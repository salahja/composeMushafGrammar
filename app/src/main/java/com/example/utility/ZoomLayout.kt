package com.example.utility

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.widget.FrameLayout
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class ZoomLayout : FrameLayout, OnScaleGestureListener {
    private var mode:  ZoomLayout.Mode =
      ZoomLayout.Mode.NONE
    private var scale = 1.0f
    private var lastScaleFactor = 0f

    // Where the finger first  touches the screen
    private var startX = 0f
    private var startY = 0f

    // How much to translate the canvas
    private var dx = 0f
    private var dy = 0f
    private var prevDx = 0f
    private var prevDy = 0f

    // Custom vars to handle double tap
    private var firstTouch = false
    private var time = System.currentTimeMillis()
    private var restore = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(context: Context) {
        val scaleDetector = ScaleGestureDetector(context, this)
        setOnTouchListener { view, motionEvent ->
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> if (firstTouch && System.currentTimeMillis() - time <= 300) {
                    //do stuff here for double tap
                    if (restore) {
                        scale = 1.0f
                        restore = false
                    } else {
                        scale *= 2.0f
                        restore = true
                    }
                    mode =ZoomLayout.Mode.ZOOM
                    firstTouch = false
                } else {
                    if (scale >ZoomLayout.Companion.MIN_ZOOM) {
                        mode =ZoomLayout.Mode.DRAG
                        startX = motionEvent.x - prevDx
                        startY = motionEvent.y - prevDy
                    }
                    firstTouch = true
                    time = System.currentTimeMillis()
                }

                MotionEvent.ACTION_MOVE -> if (mode ==ZoomLayout.Mode.DRAG) {
                    dx = motionEvent.x - startX
                    dy = motionEvent.y - startY
                }

                MotionEvent.ACTION_POINTER_DOWN -> mode =
                    ZoomLayout.Mode.ZOOM

                MotionEvent.ACTION_POINTER_UP -> mode =
                    ZoomLayout.Mode.NONE

                MotionEvent.ACTION_UP -> {
                    Log.i(ZoomLayout.Companion.TAG, "UP")
                    mode =ZoomLayout.Mode.NONE
                    prevDx = dx
                    prevDy = dy
                }
            }
            scaleDetector.onTouchEvent(motionEvent)
            if (mode ==ZoomLayout.Mode.DRAG && scale >=ZoomLayout.Companion.MIN_ZOOM || mode ==ZoomLayout.Mode.ZOOM) {
                parent.requestDisallowInterceptTouchEvent(true)
                val maxDx = child().width - child().width / scale / 2 * scale
                val maxDy = child().height - child().height / scale / 2 * scale
                dx = min(max(dx, -maxDx), maxDx)
                dy = min(max(dy, -maxDy), maxDy)
                Log.i(
                    ZoomLayout.Companion.TAG,
                    "Width: " + child().width + ", scale " + scale + ", dx " + dx
                            + ", max " + maxDx
                )
                applyScaleAndTranslation()
            }
            true
        }
    }

    override fun onScaleBegin(scaleDetector: ScaleGestureDetector): Boolean {
        Log.i(ZoomLayout.Companion.TAG, "onScaleBegin")
        return true
    }

    // ScaleGestureDetector
    override fun onScale(scaleDetector: ScaleGestureDetector): Boolean {
        val scaleFactor = scaleDetector.scaleFactor
        Log.i(
            ZoomLayout.Companion.TAG,
            "onScale$scaleFactor"
        )
        if (lastScaleFactor == 0f || sign(scaleFactor) == sign(lastScaleFactor)) {
            scale *= scaleFactor
            scale = max(
                MIN_ZOOM,
                min(scale,MAX_ZOOM)
            )
            lastScaleFactor = scaleFactor
        } else {
            lastScaleFactor = 0f
        }
        return true
    }

    override fun onScaleEnd(scaleDetector: ScaleGestureDetector) {
        Log.i(ZoomLayout.Companion.TAG, "onScaleEnd")
    }

    private fun applyScaleAndTranslation() {
        child().scaleX = scale
        child().scaleY = scale
        child().translationX = dx
        child().translationY = dy
    }

    private fun child(): View {
        return getChildAt(0)
    }

    private enum class Mode {
        NONE, DRAG, ZOOM
    }

    companion object {
        private const val TAG = "ZoomLayout"
        private const val MIN_ZOOM = 1.0f
        private const val MAX_ZOOM = 4.0f
    }
}