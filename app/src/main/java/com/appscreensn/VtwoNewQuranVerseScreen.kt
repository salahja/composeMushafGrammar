package com.appscreensn

import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.samples.crane.ui.BottomSheetShape
import androidx.compose.samples.crane.ui.crane_caption
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSettingState
import com.appscreens.ExpandableText
import com.appscreens.Picker
import com.appscreens.listState
import com.appscreens.rememberPickerState
import com.codelab.basics.ui.theme.indopak
import com.downloadmanager.DownloaderViewModel
import com.example.compose.LoadingData
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.Page
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.databinding.NewFragmentReadingBinding
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.receiversimport.FileManager
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import com.example.utility.QuranGrammarApplication.Companion.context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters
import com.google.android.exoplayer2.ui.PlayerControlView
import com.viewmodels.FIleDownloadParam
import com.viewmodels.VerseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import com.example.mushafconsolidated.databinding.XoPlayerBinding as XoPlayerBinding

private var currenttrack = 0

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(
  ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
  ExperimentalFoundationApi::class, ExperimentalMaterialApi::class
)
@Composable
fun LineQuranPageScreens(
  navController: NavHostController,
  chapid: Int,

  pageModel: VerseModel,
  darkThemePreference: BooleanPreferenceSettingValueState,
  downloadModel: DownloaderViewModel,

  ) {

  ///     val downloaderViewModel by viewModels<DownloaderViewModel>()/ val model = viewModel(modelClass = VerseModel::class.java)
  var loading by remember { mutableStateOf(false) }
  val scope = rememberCoroutineScope()
  //scopes = CoroutineScope(Dispatchers.Main)
  var isplaying by remember { mutableStateOf(false) }
  var hasTrackedChanged by remember { mutableStateOf(false) }
  var currenttrack: Int by remember { mutableStateOf(0) }
  val thememode = darkThemePreference.value

  val showtranslation =
    rememberPreferenceBooleanSettingState(key = "showtranslation", defaultValue = false)
  val showwordbyword = rememberPreferenceBooleanSettingState(key = "wbw", defaultValue = false)
  val wbwchoice = rememberPreferenceIntSettingState(key = "wbwtranslation", defaultValue = 0)
  //   val chapteritems by quranModel.chapteritems.collectAsState(initial = listOf())
  val utils = Utils(QuranGrammarApplication.context)
  val corpus = CorpusUtilityorig

  //  showWordDetails.value = false
  //   val myViewModel: VerseModel    = viewModel(factory = newViewModelFactory(chapid))

  loading = pageModel!!.loading.value
  LoadingData(isDisplayed = loading)
  val cardss by pageModel.cards.collectAsStateWithLifecycle()


  loading = pageModel!!.loading.value
  LoadingData(isDisplayed = loading)
  /*
  val myViewModel: VerseModel =
      viewModel(factory = ViewModelFactory(chapid))
*/

  if (cardss.isNotEmpty()) {
    lsurahs = cardss[0].chapterlist
    lquranbySurah = cardss[0].quranbySurah
    newnewadapterlist = cardss[0].newnewadapterlist
  }





  llistState = rememberLazyListState()
  // corpusSurahWord = utils.getQuranCorpusWbwbysurah(chapid);
  val coroutineScope = rememberCoroutineScope()
  val scrollpos = lprefrence.getInt("scroll_position", 0)
  val preferencesManager = remember { PreferencesManager(QuranGrammarApplication.context!!) }
  val data = remember { mutableStateOf(preferencesManager.getData("lastread", 1)) }
  preferencesManager.saveData("lastread", chapid.toString())
  data.value = chapid.toString()
  val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)

  val item = (1..100).toList()
  var wbw: NewQuranCorpusWbw? = null
  LoadingData(isDisplayed = false)
  val state = rememberScrollState()
  LaunchedEffect(Unit) { state.animateScrollTo(1) }

  LaunchedEffect(llistState) {
    snapshotFlow {
      llistState!!.firstVisibleItemIndex
    }
      .debounce(500L)
      .collectLatest { index ->
        lprefrence.edit()
          .putInt("scroll_position", index)
          .apply()
      }
  }
  //  LazyColumn(state = listState!!,      modifier = Modifier.fillMaxSize(),

  //    DisplayQuran(surahs, chapid)
  lfbs(lsurahs!!, chapid, navController, downloadModel)

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun lfbs(
  surahs: List<ChaptersAnaEntity>,
  chapid: Int,
  navController: NavHostController,
  downloadModel: DownloaderViewModel

) {
  val showtranslation =
    rememberPreferenceBooleanSettingState(key = "showtranslation", defaultValue = false)
  //val downloaderViewModel by viewModels<DownloaderViewModel>()
  val sheetState = rememberModalBottomSheetState()
  val scope = rememberCoroutineScope()
  var showBottomSheet by remember { mutableStateOf(false) }
  val internetStatus: Int = 0
  val showButton by remember {
    derivedStateOf {
      listState!!.firstVisibleItemIndex > 0
    }
  }
  Scaffold(

  ) {
    val listState = rememberLazyListState()

    BottomSheetScaffold(
      sheetShape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
      sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
      sheetContent = {

        Column(
          content = {
            Spacer(modifier = Modifier.padding(1.dp))

            Row(
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(
                      vertical = 2.dp,
                      horizontal = 4.dp
                  )

                  .clip(shape = CircleShape)

                  .padding(4.dp)

            ) {

              Button(onClick = {

                showAudio.value = true;
              }) {
                androidx.compose.material.Text(
                  text = "Play",
                  modifier = Modifier
                    .wrapContentHeight(),
                  textAlign = TextAlign.Center,
                  fontWeight = FontWeight.Light,
                  fontSize = 21.sp
                  //      color = Color.White
                )
              }

            }

            Row(
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                  .wrapContentWidth()
                  .padding(
                      2.dp
                  )

                  .clip(shape = CircleShape)

                  .padding(4.dp)

            ) {
              if (showAudio.value) {
                //        lAudioPlayer(downloadModel)
              }
            }

          },
          modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)

              //.background(Color(0xFF6650a4))
              .background(
                  brush = Brush.linearGradient(
                      colors = listOf(
                          Color(0xFFCEE7E4),
                          Color(0xFFBDB9C5)
                      )
                  ),
                  // shape = RoundedCornerShape(cornerRadius)
              )
              .padding(16.dp),

          )

      },
      sheetPeekHeight = 20.dp,

      )
    {
      androidx.compose.material.Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
        shape = BottomSheetShape
      ) {
        Column(modifier = Modifier.padding(start = 24.dp, top = 20.dp, end = 24.dp)) {
          androidx.compose.material.Text(
            text = "title",
            style = androidx.compose.material.MaterialTheme.typography.caption.copy(
              color = crane_caption
            )
          )
          Spacer(Modifier.height(8.dp))
          // TODO Codelab: derivedStateOf step
          // TODO: Show "Scroll to top" button when the first item of the list is not visible
          val listState = rememberLazyListState()
          LDisplayQuran(surahs, chapid, showtranslation, navController)
        }
      }

    }
  }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
private fun LDisplayQuran(
  surahs: List<ChaptersAnaEntity>,
  chapid: Int,
  showtranslation: BooleanPreferenceSettingValueState,
  navController: NavHostController
) {

  Scaffold(

  )

  {

    isTrackchanged = remember {
      mutableStateOf(true)
    }


    Column(

      modifier = Modifier
          .fillMaxSize()
          .padding(5.dp)
    ) {

      Spacer(modifier = Modifier.height(5.dp))
      val scope = rememberCoroutineScope()
      val state = rememberLazyListState()
      val showButton by remember {
        derivedStateOf {
          state!!.firstVisibleItemIndex > 0
        }
      }

      val fullyVisibleIndices: List<Int> by remember {
        derivedStateOf {
          val layoutInfo = state.layoutInfo
          val visibleItemsInfo = layoutInfo.visibleItemsInfo
          if (visibleItemsInfo.isEmpty()) {
            emptyList()
          } else {
            fullyVisibleItemsInfo = visibleItemsInfo.toMutableList()

            val lastItem = fullyVisibleItemsInfo!!.last()

            val viewportHeight =
              layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

            if (lastItem.offset + lastItem.size > viewportHeight) {
              fullyVisibleItemsInfo!!.removeLast()
            }

            val firstItemIfLeft = fullyVisibleItemsInfo!!.firstOrNull()
            if (firstItemIfLeft != null && firstItemIfLeft.offset < layoutInfo.viewportStartOffset) {
              fullyVisibleItemsInfo!!.removeFirst()
            }

            fullyVisibleItemsInfo!!.map { it.index }

          }
        }
      }

      val corroutineScope = rememberCoroutineScope()

      val itemHeight = with(LocalDensity.current) { 80.dp.toPx() } // Your item height
      val scrollPos =
        state.firstVisibleItemIndex * itemHeight + state.firstVisibleItemScrollOffset
      LaunchedEffect(state) {
        snapshotFlow {
          state!!.firstVisibleItemIndex
        }
        state.scrollToItem(currenttrack!!)
      }

      //  scrollToCurrentTrack(state)
      // Text(text = "scroll: $scrollPos")
      Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colorScheme.primary)
      ) {

        Text(

          text = surahs!![chapid - 1]!!.abjadname,

          fontWeight = FontWeight.Bold,
          color = Color.Red,
          modifier = Modifier.align(Alignment.TopCenter),
          textAlign = TextAlign.Center,
          fontFamily = indopak,
          fontSize = 20.sp
        )
        if (surahs!![chapid - 1]!!.ismakki == 1) {
          Image(
            painter = painterResource(id = R.drawable.ic_makkah_48),

            modifier = Modifier.align(Alignment.TopEnd),
            //   .background(Color( QuranGrammarApplication.Companion.context!!!!.getColor(R.color.OrangeRed))),
            contentDescription = "contentDescription",
            contentScale = ContentScale.Crop
          )
        } else {
          Image(
            painter = painterResource(id = R.drawable.ic_makkah_48),

            modifier = Modifier.align(Alignment.TopEnd),
            //         .background(Color( QuranGrammarApplication.Companion.context!!!!.getColor(R.color.OrangeRed))),
            contentDescription = "contentDescription",
            contentScale = ContentScale.Crop,
          )

        }

        Text(
          text = "No.Of Aya's :" + surahs!![chapid - 1]!!.versescount.toString(),
          color = Color.Black,
          fontWeight = FontWeight.Bold,

          modifier = Modifier.align(Alignment.TopStart),
          textAlign = TextAlign.Center,
          fontSize = 15.sp
        )
        Text(
          text = "No.Of Ruku's :" + surahs!![chapid - 1]!!.rukucount.toString(),

          fontWeight = FontWeight.Bold,
          color = Color.Black,
          modifier = Modifier.align(Alignment.BottomStart),
          textAlign = TextAlign.Center,
          fontSize = 15.sp
        )

      }



      LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .padding(10.dp)
            .wrapContentWidth(),
        state = state!!
      ) {

        itemsIndexed(lquranbySurah!!.toList()) { index, item ->
          scope.launch {
            state.scrollToItem(currenttrack!!)
          }

          //   val img = imgs.getDrawable(surahs!!.chapid - 2)

          Card(
            colors = CardDefaults.cardColors(
              //      containerColor = colorResource(id = R.color.bg_surface_dark_blue),
            ), elevation = CardDefaults.cardElevation(
              defaultElevation = 16.dp
            )
          )

          {
            Row(
              verticalAlignment = Alignment.Top,
              horizontalArrangement = Arrangement.SpaceEvenly,

              modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight()

                  .padding(
                      horizontal = 10.dp,
                      vertical = 8.dp
                  )

            )

            {

            }
            val isVisible by remember(index) {
              derivedStateOf {
                fullyVisibleIndices.contains(index)
              }
            }
            val infiniteTransition = rememberInfiniteTransition()

            /*       Text(
                       index.toString(),
                       modifier = Modifier
                           .background(if (isVisible) Color.Green else Color.Transparent)
                           .padding(30.dp)
                   )*/
            Row(
              modifier = Modifier
                  .fillMaxWidth()

                  .padding(
                      horizontal = 10.dp,
                      vertical = 8.dp
                  )
            ) {
              lwordarray = newnewadapterlist?.get(index)

              ClickableText(
                text = lwordarray!![0].annotatedVerse!!,

                onClick = {
                  val cid = lquranbySurah!![index].surah
                  val aid = lquranbySurah!![index].ayah
                  navController.navigate(
                    "versealert/${cid}/${aid}"
                  )

                }, style = TextStyle(
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 18.sp,
                  fontFamily = FontFamily.Cursive
                ),
                modifier = Modifier

                    .background(if (isVisible && isplaying.value && currenttrack == index) Color.LightGray else Color.Transparent)
                    .padding(3.dp)

              )
              //  Text(text = "current: $currenttrack")
              //    Text(text = "index: $index")
            }
            Row(
              horizontalArrangement = Arrangement.End,
              modifier = Modifier
                  .fillMaxWidth()

                  .padding(
                      horizontal = 10.dp,
                      vertical = 8.dp
                  )

            ) {

              if (showtranslation.value) {
                ExpandableText(

                  text = AnnotatedString(lquranbySurah!![index].translation)
                  /*    fontSize = 20.sp,
              fontFamily = indopak,
              color = colorResource(id = R.color.kashmirigreen)*/
                )
              }
            }
            Row(
              modifier = Modifier
                  .fillMaxWidth()

                  .padding(
                      horizontal = 10.dp,
                      vertical = 8.dp
                  )
            ) {
              /*     wordarray = newnewadapterlist[index]
                   val annotedMousuf = AnnotationUtility.AnnotedMousuf(
                       wordarray!![0].annotatedVerse.toString(),
                       wordarray!![0].corpus!!.surah, wordarray!![0].corpus!!.ayah
                   )
*/

              ExpandableText(
                text = AnnotatedString("Ibne Kathir :" + lquranbySurah!![0].tafsir_kathir)

              )

            }

          }

        }
      }
      /*     if(isplaying.value) {
               LaunchedEffect(Unit) {
                   scrollToCurrentTrack(state)
                   //    autoScroll(state)

               }
           }*/
    }
  }

}

private tailrec suspend fun scrollToCurrentTrack(state: LazyListState) {

  var key = currenttrack > 1

  if (key && isplaying.value) {
    state.animateScrollToItem(currenttrack)
  }

  scrollToCurrentTrack(state)
}

private const val DELAY_BETWEEN_SCROLL_MS = 390L

private const val SCROLL_DX = 4f
private tailrec suspend fun autoScroll(
  lazyListState: LazyListState,

  ) {
  // lazyListState.animateScrollToItem(currenttrack+1)
  lazyListState.scroll(MutatePriority.PreventUserInput) {

    scrollBy(SCROLL_DX)
  }
  val l = msecond / 1000
  if (msecond == 0.toLong()) {
    delay(DELAY_BETWEEN_SCROLL_MS)
  } else {
    delay(DELAY_BETWEEN_SCROLL_MS)

  }
  if (isTrackchanged!!.value == true) {
    autoScroll(lazyListState)
    isTrackchanged!!.value = false
  }

  /*    if(isTrackchanged!!.value==false)
          autoScroll(lazyListState)
      if(isTrackchanged!!.value)
       autoScroll(lazyListState)*/

}


