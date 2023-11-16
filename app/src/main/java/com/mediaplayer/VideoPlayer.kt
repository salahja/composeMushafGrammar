import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
//import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.media3.ui.AspectRatioFrameLayout


import com.appscreens.readerID
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils

import com.example.mushafconsolidated.databinding.XoPlayerBinding
import com.example.mushafconsolidated.receiversimport.FileManager
import com.example.utility.QuranGrammarApplication
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView




@SuppressLint("OpaqueUnitKey")
@Composable
@Preview
fun AudioPlayer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val mediaItems = arrayListOf<MediaItem>()
    val ayaLocations: MutableList<String> = ArrayList()
      var marray: MutableList<MediaItem> = arrayListOf()

    val repository = Utils(QuranGrammarApplication.context)
    val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(
        70
    )
    for (ayaItem in quranbySurah!!) {
        ayaLocations.add(
            FileManager.createAyaAudioLinkLocation(
                QuranGrammarApplication.context,
                readerID,
                ayaItem!!.ayah,
                ayaItem.surah
            )
        )
        val location = FileManager.createAyaAudioLinkLocation(
            QuranGrammarApplication.context,
            readerID,
            ayaItem.ayah,
            ayaItem.surah
        )
        marray.add(MediaItem.fromUri(location))
    }
/*
    // "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val exoPlayers = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri(
                    "https://server11.mp3quran.net/a_ahmed/058.mp3"
                )
            )
            prepare()
            playWhenReady = true
        }
    }
*/

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItems(marray

            )
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = true
            prepare()
            play()

        }
    }


    Box(modifier = modifier) {
        DisposableEffect(key1 = Unit) { onDispose { exoPlayer.release() } }

        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    layoutParams =
                        FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                }

            }

        )


    }







/*
    Box(modifier = modifier) {

        DisposableEffect(AndroidViewBinding(
            modifier = modifier,
            factory = XoPlayerBinding::inflate
        ) {
            this.playerView.apply {
              //  hide()
             //   useController = true
              //  resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }) {
            onDispose {

                exoPlayer.pause()
                exoPlayer.release()

            }
        }



    }

*/



}











@androidx.media3.common.util.UnstableApi
@ExperimentalAnimationApi
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,

    ) {
    val mediaItems = arrayListOf<MediaItem>()
    val ayaLocations: MutableList<String> = ArrayList()

    val repository = Utils(QuranGrammarApplication.context)
    val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(
        100
    )
    for (ayaItem in quranbySurah!!) {
        ayaLocations.add(
            FileManager.createAyaAudioLinkLocation(
                QuranGrammarApplication.context,
                readerID,
                ayaItem!!.ayah,
                ayaItem.surah
            )
        )
        val location = FileManager.createAyaAudioLinkLocation(
            QuranGrammarApplication.context,
            readerID,
            ayaItem.ayah,
            ayaItem.surah
        )
        mediaItems.add(MediaItem.fromUri(location))
    }
    val context = LocalContext.current
    /*    var players: SimpleExoPlayer? = null
    player = SimpleExoPlayer.Builder(QuranGrammarApplication.context!!).build()
    player.setMediaItems(mediaItems)

    player!!.prepare()
    player.play()*/
    //   val videoTitle = remember {      mutableStateOf(gameVideos[0].name)  }

    val visibleState = remember { mutableStateOf(true) }


    var exoPlayer = remember { SimpleExoPlayer.Builder(QuranGrammarApplication.context!!).build() }
    exoPlayer.setMediaItems(mediaItems)

    exoPlayer!!.prepare()
    exoPlayer.play()
    exoPlayer.playWhenReady = true


    /*

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
           this. setMediaItem(
                mediaItems
            )
            prepare()
            playWhenReady = true
        }
    }
*/

    Box(modifier = modifier) {
        DisposableEffect(key1 = Unit) { onDispose { exoPlayer.release() } }

        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    layoutParams =
                        FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                }
            }
        )
    }
}
 /*   val playerView = remember {
        StyledPlayerView(context, null, 0).apply {
            player = exoPlayer
        }
    }
    Surface(color = Color.Black) {
        AndroidView(
            modifier = Modifier.wrapContentWidth(),
            factory = {
                playerView
            }
        )
    }
    */
/*    exoPlayer.playWhenReady = true
    exoPlayer.play()


    player!!.prepare()
    player!!.play()
    ConstraintLayout(modifier = modifier) {
        val (title, videoPlayer) = createRefs()

        AnimatedVisibility(
            visible = visibleState.value,
            modifier =
            Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = "videoTitle.value",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }

        // player view same as before
    }*/

