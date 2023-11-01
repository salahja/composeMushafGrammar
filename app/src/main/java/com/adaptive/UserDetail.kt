package com.adaptive

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.Entities.qurandictionary
import com.example.mushafconsolidated.Utils
import com.example.utility.QuranGrammarApplication
import com.skyyo.expandablelist.theme.AppThemeSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: String?,
    isOnlyDetailScreen: Boolean,
    navController: NavHostController,
    onBackPressed: () -> Unit,

) {
    val util= Utils(QuranGrammarApplication.context!!)
    val searchs= "$userId%";
    val letter: List<VerbCorpus> = util.getQuranVerbsByfirstletter(searchs!!)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isOnlyDetailScreen)
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = { Text(text = "User Detail", textAlign = TextAlign.Center) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressed()
                            },
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
        },
    ) {
        LazyVerticalGrid(

            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),

            columns = GridCells.Fixed(4),

        ) {
            items(letter!!.size) { index ->
                //          indexval=index
                GridList(letter[index],navController)
            }
        }
    }

}

fun GridLists(qurandictionary: qurandictionary) {
    TODO("Not yet implemented")
}

@Composable
fun GridList(
    surahModelList: VerbCorpus,
    navController: NavHostController,

    ) {

    val darkThemeEnabled = AppThemeSettings.isDarkThemeEnabled
    val interactionSource = remember { MutableInteractionSource() }




        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            val root=        surahModelList!!.root_a
           // indexval = surahModelList!!.chapterid

            ElevatedCard(onClick = {
                navController.navigate(

                    "roots/$root")
            }) {
                Icon(Icons.Filled.ShoppingCart, "")
                Spacer(Modifier.padding(20.dp))
                Text(text = surahModelList!!.root_a.toString())
            }







    }


    @Suppress("DEPRECATION")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @ExperimentalFoundationApi
    @Composable
    fun BottomDialog() {
        //Lets create list to show in bottom sheet
        data class BottomSheetItem(val title: String, val icon: ImageVector)

        val bottomSheetItems = listOf(
            BottomSheetItem(title = "Notification", icon = Icons.Default.Notifications),
            BottomSheetItem(title = "Mail", icon = Icons.Default.MailOutline),
            BottomSheetItem(title = "Scan", icon = Icons.Default.Search),
            BottomSheetItem(title = "Edit", icon = Icons.Default.Edit),
            BottomSheetItem(title = "Favorite", icon = Icons.Default.Favorite),
            BottomSheetItem(title = "Settings", icon = Icons.Default.Settings)
        )

        //Lets define bottomSheetScaffoldState which will hold the state of Scaffold
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        val coroutineScope = rememberCoroutineScope()
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetShape = RoundedCornerShape(topEnd = 30.dp),
            sheetContent = {
                //Ui for bottom sheet
                Column(
                    content = {

                        Spacer(modifier = Modifier.padding(16.dp))
                        androidx.compose.material.Text(
                            text = "Bottom Sheet",
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,
                            color = Color.White
                        )
                        LazyVerticalGrid(
                            //cells = GridCells.Fixed(3)
                            columns = GridCells.Fixed(3), //https://developer.android.com/jetpack/compose/lists
                        ) {
                            items(bottomSheetItems.size, itemContent = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp)
                                        .clickable {


                                        },
                                ) {
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    androidx.compose.material.Icon(
                                        bottomSheetItems[it].icon,
                                        bottomSheetItems[it].title,
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    androidx.compose.material.Text(text = bottomSheetItems[it].title, color = Color.White)
                                }

                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)

                        //.background(Color(0xFF6650a4))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFDBCEE7),
                                    Color(0xFFBDB9C5)
                                )
                            ),
                            // shape = RoundedCornerShape(cornerRadius)
                        )
                        .padding(16.dp),

                    )
            },
            sheetPeekHeight = 0.dp,

            ) {


            //Add button to open bottom sheet
            Column(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }
                ) {
                    androidx.compose.material.Text(
                        text = "Click to show Bottom Sheet"
                    )
                }
            }
        }
    }
}

@Composable
fun ElevatedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    CircleShape: RoundedCornerShape = RoundedCornerShape(20),
    shape: Shape = CircleShape,
    content: @Composable RowScope.() -> Unit
) {

    Row(
        modifier = modifier
            .defaultMinSize(minWidth = 76.dp, minHeight = 48.dp)

            .clickable(
                enabled = enabled,
                indication = ScaleIndication,
                interactionSource = interactionSource,
                onClick = onClick
            )
            .border(width = 2.dp, color = Color.Blue, shape = shape)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

/*class NeonIndication(private val shape: Shape, private val borderWidth: Dp) : Indication {

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        // key the remember against interactionSource, so if it changes we create a new instance
        val instance = remember(interactionSource) {
            NeonIndicationInstance(
                shape,
                // Double the border size for a stronger press effect
                borderWidth * 2
            )
        }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> instance.animateToPressed(interaction.pressPosition, this)
                    is PressInteraction.Release -> instance.animateToResting(this)
                    is PressInteraction.Cancel -> instance.animateToResting(this)
                }
            }
        }

        return instance
    }

    private class NeonIndicationInstance(
        private val shape: Shape,
        private val borderWidth: Dp
    ) : IndicationInstance {
        var currentPressPosition: Offset = Offset.Zero
        val animatedProgress = androidx.compose.animation.core.Animatable(0f)
        val animatedPressAlpha = androidx.compose.animation.core.Animatable(1f)

        var pressedAnimation: Job? = null
        var restingAnimation: Job? = null

        fun animateToPressed(pressPosition: Offset, scope: CoroutineScope) {
            val currentPressedAnimation = pressedAnimation
            pressedAnimation = scope.launch {
                // Finish any existing animations, in case of a new press while we are still showing
                // an animation for a previous one
                restingAnimation?.cancelAndJoin()
                currentPressedAnimation?.cancelAndJoin()
                currentPressPosition = pressPosition
                animatedPressAlpha.snapTo(1f)
                animatedProgress.snapTo(0f)
                animatedProgress.animateTo(1f, tween(450))
            }
        }

        fun animateToResting(scope: CoroutineScope) {
            restingAnimation = scope.launch {
                // Wait for the existing press animation to finish if it is still ongoing
                pressedAnimation?.join()
                animatedPressAlpha.animateTo(0f, tween(250))
                animatedProgress.snapTo(0f)
            }
        }


        override fun ContentDrawScope.drawIndication() {
            val (startPosition, endPosition) = calculateGradientStartAndEndFromPressPosition(
                currentPressPosition, size
            )
            val brush = animateBrush(
                startPosition = startPosition,
                endPosition = endPosition,
                progress = animatedProgress.value
            )
            val alpha = animatedPressAlpha.value

            drawContent()

            val outline = shape.createOutline(size, layoutDirection, this)
            // Draw overlay on top of content
            drawOutline(
                outline = outline,
                brush = brush,
                alpha = alpha * 0.1f
            )
            // Draw border on top of overlay
            drawOutline(
                outline = outline,
                brush = brush,
                alpha = alpha,
                style = Stroke(width = borderWidth.toPx())
            )
        }

        private fun calculateGradientStartAndEndFromPressPosition(
            pressPosition: Offset,
            size: androidx.compose.ui.geometry.Size
        ): Pair<Offset, Offset> { 0.1f }

        private fun animateBrush(
            startPosition: Offset,
            endPosition: Offset,
            progress: Float
        ): Brush { … }
    }
}*/
object ScaleIndication : Indication {
    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        // key the remember against interactionSource, so if it changes we create a new instance
        val instance = remember(interactionSource) { ScaleIndicationInstance() }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> instance.animateToPressed(interaction.pressPosition)
                    is PressInteraction.Release -> instance.animateToResting()
                    is PressInteraction.Cancel -> instance.animateToResting()
                }
            }
        }

        return instance
    }

    private class ScaleIndicationInstance : IndicationInstance {
        var currentPressPosition: Offset = Offset.Zero
        val animatedScalePercent = androidx.compose.animation.core.Animatable(1f)

        suspend fun animateToPressed(pressPosition: Offset) {
            currentPressPosition = pressPosition
            animatedScalePercent.animateTo(0.9f, spring())
        }

        suspend fun animateToResting() {
            animatedScalePercent.animateTo(1f, spring())
        }


        override fun ContentDrawScope.drawIndication() {
            scale(
                scale = animatedScalePercent.value,
                pivot = currentPressPosition
            ) {
                this@drawIndication.drawContent()
            }
        }
    }
}
@Composable
fun HoverButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    Button(onClick, modifier, enabled, interactionSource = interactionSource) {
        AnimatedVisibility(visible = isHovered) {
            icon()
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
        text()
    }
}