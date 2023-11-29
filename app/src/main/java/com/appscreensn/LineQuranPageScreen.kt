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

lateinit var binding: XoPlayerBinding

var msecond: Long = 0L

var isTrackchanged: MutableState<Boolean>? = null
var fullyVisibleItemsInfo: MutableList<LazyListItemInfo>? = null
var isplaying = mutableStateOf(false)
var newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>? = null
var ldownloadlist: ArrayList<FIleDownloadParam>? = null
var lreaderID = 20
lateinit var ldownloadLink: String
lateinit var lreaderName: String
var lstartBeforeDownload = false
var lpageArrays: List<Page>? = null
var lshowAudio = mutableStateOf(false)
var lquranbySurah: List<QuranEntity>? = null
var lsurahs: List<ChaptersAnaEntity>? = null
var lscopes: CoroutineScope? = null
var lwordarray: ArrayList<NewQuranCorpusWbw>? = null
private var currenttrack = 0


var exoplayer: ExoPlayer? = null

var llistState: LazyListState? = null
var annotatedStringStringPair: Pair<AnnotatedString, Int>? = null
var laid: Int = 0
var lcid: Int = 0
var lwid: Int = 0
val showWordDetails = mutableStateOf(false)
val lprefrence by lazy {
    QuranGrammarApplication.context!!.getSharedPreferences(
        "prefs",
        Context.MODE_PRIVATE
    )
}


@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class
)
@Composable
fun LineQuranPageScreen(
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
    lfb(lsurahs!!, chapid, navController, downloadModel)


}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun lfb(
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
            //   ExploreList(exploreList, onItemClicked, listState = listState)

            // Show the button if the first visible item is past
            // the first item. We use a remembered derived state to
            // minimize unnecessary compositions


        BottomSheetScaffold(
            //  scaffoldState = bottomSheetScaffoldState,

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
                        .height(350.dp)

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
            sheetPeekHeight = 240.dp,

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



@SuppressLint("OpaqueUnitKey")
@Composable

fun lAudioPlayer(downloadModel: DownloaderViewModel, modifier: Modifier = Modifier) {
    lateinit var playerView: PlayerControlView

    lateinit var trackSelectionParameters: TrackSelectionParameters
    lateinit var lastSeenTracks: Tracks
    var startAutoPlay = false
    var startItemIndex = 0
    var startPosition: Long = 0
    lateinit var playiv: ImageView
    var pausePlayFlag = false
    var surahselected = 0
    var verselected = 0
    var versescount = 0
    lateinit var surahNameEnglish: String
    lateinit var surahNameArabic: String
    lateinit var isNightmode: String
    val context = LocalContext.current
    val mediaItems = arrayListOf<MediaItem>()
    val ayaLocations: MutableList<String> = ArrayList()
    var marray: MutableList<MediaItem> = arrayListOf()

    downloadlist = arrayListOf<FIleDownloadParam>()
    val coroutineScope = rememberCoroutineScope()
    val scope: CoroutineScope
    scope = CoroutineScope(Dispatchers.Main)
    val repository = Utils(QuranGrammarApplication.context)
    val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(
        111
    )


    val media by downloadModel.marray.collectAsStateWithLifecycle()

    exoplayer = ExoPlayer.Builder(context).build()

    exoplayer!!.addListener(PlayerEventListener())
    // exoPlayer!!.addAnalyticsListener(EventLogger())
    exoplayer!!.setAudioAttributes(AudioAttributes.DEFAULT,  /* handleAudioFocus= */true)
    exoplayer!!.playWhenReady = startAutoPlay
    exoplayer!!.repeatMode = Player.REPEAT_MODE_ALL
    exoplayer!!.setMediaItems(media)
    exoplayer!!.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    exoplayer!!.playWhenReady = true
    exoplayer!!.prepare()
    exoplayer!!.play()



    lateinit var binding: XoPlayerBinding

    /* AndroidView(
         factory = { context ->
             val view = LayoutInflater.from(context).inflate(R.layout.custom_xo_player, null, false)
             val textView = view.findViewById<TextView>(R.id.lqari)

             // do whatever you want...
             view // return the view
         },
         update = { view ->

         }
     )*/
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {

        DisposableEffect(key1 = AndroidViewBinding(
            XoPlayerBinding::inflate,
            modifier = modifier,


            ) {
            val visible = this.playerView.isVisible

            this.playerView.apply {

                //  hide()
                //   useController = true
                //  resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                player = exoplayer

                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )

                val currenttrack = player!!.currentMediaItemIndex
                val nexttrack = player!!.currentMediaItemIndex + 1

                player!!.addListener(PlayerEventListener())
                isplaying.value = true
                val playbackProperties = exoplayer!!.currentMediaItem!!.playbackProperties
                playbackProperties.toString()

                val uri = Uri.parse(playbackProperties!!.uri.toString())
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(context, uri)
                val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)


            }
        }) {
            onDispose {

                exoplayer!!.pause()
                exoplayer!!.release()

            }
        }

    }


}


class PlayerEventListener : Player.Listener {
    override fun onTracksChanged(tracks: Tracks) {

        val playbackProperties = exoplayer!!.currentMediaItem!!.playbackProperties
        playbackProperties.toString()

        val uri = Uri.parse(playbackProperties!!.uri.toString())
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, uri)
        val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        msecond = durationStr!!.toLong()
        currenttrack = exoplayer!!.currentMediaItemIndex
        currenttrack++
        isTrackchanged!!.value = true



        //  super.onTracksChanged(tracks)

    }


}

private val sendUpdatesToUI: Runnable = object : Runnable {
    override fun run() {

        println("current track" + currenttrack)

    }
}
/*        //  rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((ayah)));
///musincadapter
        // RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(currenttrack);
        val holder = recyclerView.findViewHolderForAdapterPosition(currenttrack)
        val ab = StringBuilder()
        ab.append("Aya").append(":").append(currenttrack).append(" ").append("of").append(
            versescount
        )
        ayaprogress.text = ab.toString()
        if (null != holder) {
            try {
                if (holder.itemView.findViewById<View?>(R.id.quran_textView) != null) {
                    if (isNightmode == "light") {
                        holder.itemView.findViewById<View>(R.id.quran_textView)
                            .setBackgroundColor(
                                android.graphics.Color.LTGRAY
                            )
                        val textViews =
                            holder.itemView.findViewById<TextView>(R.id.quran_textView)
                        val str = textViews.text.toString()
                        val span = SpannableStringBuilder(str)
                        span.setSpan(
                            ForegroundColorSpan(android.graphics.Color.CYAN),
                            0,
                            str.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    } else if (isNightmode == "brown") {
                        holder.itemView.findViewById<View>(R.id.quran_textView)
                            .setBackgroundColor(
                                android.graphics.Color.CYAN
                            )
                        val textViews =
                            holder.itemView.findViewById<TextView>(R.id.quran_textView)
                        val str = textViews.text.toString()
                        val span = SpannableStringBuilder(str)
                        span.setSpan(
                            ForegroundColorSpan(android.graphics.Color.CYAN),
                            0,
                            str.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    } else {
                        val textView =
                            holder.itemView.findViewById<TextView>(R.id.quran_textView)
                        textView.text
                        val strs = textView.text.toString()
                        val spans = SpannableStringBuilder(strs)
                        spans.setSpan(
                            BackgroundColorSpan(android.graphics.Color.BLUE),
                            0,
                            strs.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        textView.text = spans
                        //  holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.BLUE);
                        //for vtwoadapter
                    }
                }
            } catch (exception: NullPointerException) {
                Toast.makeText(
                    this@ShowMushafActivity,
                    "null pointer udapte",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val holderp = recyclerView.findViewHolderForAdapterPosition(currenttrack - 1)
        if (currenttrack > 1) {
            if (null != holderp) {
                try {
                    val arrayList = ArrayList<String>()
                    val fl: FlowLayout = FlowLayout(this@ShowMushafActivity, arrayList)
                    val arrayList1 = fl.arrayList
                    fl.getChildAt(ayah)
                    val drawingCacheBackgroundColor =
                        holderp.itemView.findViewById<View>(R.id.quran_textView).drawingCacheBackgroundColor
                    if (holderp.itemView.findViewById<View?>(R.id.quran_textView) != null) {
                        //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                        holderp.itemView.findViewById<View>(R.id.quran_textView)
                            .setBackgroundColor(drawingCacheBackgroundColor)
                    }
                } catch (exception: NullPointerException) {
                    Toast.makeText(
                        this@ShowMushafActivity,
                        "UPDATE HIGHLIGHTED",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        recyclerView.post { recyclerView.scrollToPosition(currenttrack) }

        //  handler.postDelayed(this, 1000);
    }*/


private fun extracted(
    quranbySurah: List<QuranEntity?>?,
    ayaLocations: MutableList<String>,
    marray: MutableList<MediaItem>
) {
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
}

@Composable
private fun exoPlayer(
    context: Context,
    marray: List<MediaItem>
): ExoPlayer {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItems(
                marray

            )
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = true
            prepare()
            play()

        }
    }
    return exoPlayer
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
            lPickerExample()
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

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun lPickerExample() {
    Surface(
        modifier = Modifier.height(40.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Card(


            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    vertical = 8.dp
                )
        )
        {
            val utils = Utils(QuranGrammarApplication.context)
            val chapters = utils.getAllAnaChapters()
            val cnames = mutableStateOf("")
            val surah =
                QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.surahdetails)
            val ayaLocations: MutableList<String> = ArrayList()
            if (chapters != null) {
                for (cha in chapters) {
                    val sb = StringBuilder()
                    sb.append(cha!!.chapterid).append(" ").append(cha!!.nameenglish).append("  ")
                        .append(cha!!.namearabic)
                    ayaLocations.add(sb.toString())
                }
            }
            val values = remember { (1..144).map { it.toString() } }
            val valuesPickerState = rememberPickerState()

            val units = remember { ayaLocations }
            val unitsPickerState = rememberPickerState()

            //  Text(text = "Example Picker", modifier = Modifier.padding(top = 5.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                /*   Picker(
                       state = valuesPickerState,
                       items = values,
                       visibleItemsCount = 3,
                       modifier = Modifier.weight(0.3f),
                       textModifier = Modifier.padding(8.dp),
                       textStyle = TextStyle(fontSize = 32.sp)
                   )*/
                Picker(
                    state = valuesPickerState,
                    items = units,
                    visibleItemsCount = 1,
                    modifier = Modifier.weight(0.7f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 32.sp)
                )
            }
            /*
                        Text(
                            text = "Interval: ${valuesPickerState.selectedItem} ${unitsPickerState.selectedItem}",

                            modifier = Modifier.padding(vertical = 16.dp)
                        )*/

        }
    }
}
