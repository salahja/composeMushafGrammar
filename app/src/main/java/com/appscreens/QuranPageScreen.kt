package com.appscreens

import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.os.Environment
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.codelab.basics.ui.theme.indopak
import com.example.compose.LoadingData
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.Page
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.databinding.XoPlayerBinding
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.receiversimport.FileManager
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.textview.MaterialTextView
import com.viewmodels.QuranPagesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.io.File
import java.text.MessageFormat

var readerID = 20
lateinit var downloadLink: String
lateinit var readerName: String
var startBeforeDownload = false
var pageArrays: List<Page>? = null

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class
)
@Composable
fun QuranPageScreen(
    navController: NavHostController,
    chapid: Int,

    pageModel: QuranPagesModel,
    darkThemePreference: BooleanPreferenceSettingValueState,


    ) {

    // val model = viewModel(modelClass = VerseModel::class.java)
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    scopes = CoroutineScope(Dispatchers.Main)

    val thememode = darkThemePreference.value

    val showtranslation =
        rememberPreferenceBooleanSettingState(key = "showtranslation", defaultValue = false)
    val showwordbyword = rememberPreferenceBooleanSettingState(key = "wbw", defaultValue = false)
    val wbwchoice = rememberPreferenceIntSettingState(key = "wbwtranslation", defaultValue = 0)
    //   val chapteritems by quranModel.chapteritems.collectAsState(initial = listOf())
    val utils = Utils(QuranGrammarApplication.context)
    val corpus = CorpusUtilityorig



    showWordDetails.value = false
    //   val myViewModel: VerseModel    = viewModel(factory = newViewModelFactory(chapid))

    loading = pageModel!!.loading.value
    LoadingData(isDisplayed = loading)
    val cardss by pageModel.cards.collectAsStateWithLifecycle()
    val collectAsStateWithLifecycle = pageModel.cards.collectAsStateWithLifecycle()
    val collectAsState = pageModel.cards.collectAsState()
    loading = pageModel!!.loading.value
    LoadingData(isDisplayed = loading)
    /*
    val myViewModel: VerseModel =
        viewModel(factory = ViewModelFactory(chapid))
*/

    pageArrays = cardss[0].qurnaPages
    val surahs = cardss[0].chapterlist



    listState = rememberLazyListState()
    // corpusSurahWord = utils.getQuranCorpusWbwbysurah(chapid);
    val coroutineScope = rememberCoroutineScope()
    val scrollpos = prefrence.getInt("scroll_position", 0)
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

    LaunchedEffect(listState) {
        snapshotFlow {
            listState!!.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                prefrence.edit()
                    .putInt("scroll_position", index)
                    .apply()
            }
    }
    //  LazyColumn(state = listState!!,      modifier = Modifier.fillMaxSize(),
    Scaffold {

        //    DisplayQuran(surahs, chapid)
        fb(surahs, chapid, navController)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun fb(
    surahs: List<ChaptersAnaEntity>,
    chapid: Int,
    navController: NavHostController
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val internetStatus: Int = 0

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show bottom sheet") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ) {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState

            ) {
                Box(modifier = Modifier.wrapContentHeight()) {
                    AudioPlayer()
                }
                Column(
                    content = {

                        Spacer(modifier = Modifier.padding(16.dp))



                    },


                    )










                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {

                    Text("Hide bottom sheet")
                }
            }
        }
        BottomSheetScaffold(
            //  scaffoldState = bottomSheetScaffoldState,
            sheetShape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
            sheetContent = {
             /*   Box(modifier = Modifier.wrapContentHeight()) {
                    AudioPlayer()
                }*/
                //Ui for bottom sheet
                Column(
                    content = {

                        Spacer(modifier = Modifier.padding(16.dp))
                        Button(onClick = {

                            val Links: List<String>? = createDownloadLinks()
                            if (Links!!.isNotEmpty()) {
                                //check if the internet is opened
                                DownLoadIfNot(internetStatus, Links as ArrayList<String>)
                            } else {
                                navController.navigate(
                                    "play"
                                )
                            }


                        }
                        ) {
                            androidx.compose.material.Text(
                                text = "Play",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,
                                color = Color.White
                            )


                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)

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
            sheetPeekHeight = 40.dp,

            )
        {

            DisplayQuran(surahs, chapid)
        }
    }
}

@SuppressLint("OpaqueUnitKey")
@Composable
@Preview
fun AudioPlayer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val mediaItems = arrayListOf<MediaItem>()
    val ayaLocations: MutableList<String> = ArrayList()
    var marray: MutableList<MediaItem> = arrayListOf()
    lateinit var binding: XoPlayerBinding
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










    Column(modifier = Modifier
        .wrapContentHeight()
        .padding(horizontal = 16.dp)) {

        DisposableEffect(AndroidViewBinding(
            modifier = modifier,
            factory = XoPlayerBinding::inflate
         //           qariname = findViewById<TextView>(R.id.lqari)
        ) {
            val visible = this.playerView.isVisible

            this.playerView.apply {

                //  hide()
                //   useController = true
                //  resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                player = exoPlayer

                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )


            }
        }) {
            onDispose {

                exoPlayer.pause()
                exoPlayer.release()

            }
        }
     /*   val state = remember { mutableStateOf(0) }
        AndroidView(factory = { context->
            View.inflate(context, R.layout.xo_player,null)
        }, update = { view ->

            // Here we bind a variable with findviewbyid to access compose

            val composeView: MaterialTextView = view.findViewById(R.id.lqari)
            composeView.text="alasafy"

        })*/

    }


}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
private fun BottomSheet(
    surahs: List<ChaptersAnaEntity>,
    chapid: Int,
    navController: NavHostController
) {
    val internetStatus: Int = 0


}

fun createDownloadLinks(): List<String>? {

    return emptyList()

}


fun DownLoadIfNot(internetStatus: Int, Links: ArrayList<String>) {
    val app_folder_path =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            .toString() + "/audio/" + readerID
    val f = File(app_folder_path)
    val path = f.absolutePath
    val file = File(path)
    if (!file.exists()) file.mkdirs()
    startBeforeDownload = true


    /*    startDownloadingFiles(
            file=Links


        )*/
}

/*
fun startDownloadingFiles(file: java.util.ArrayList<String>,
                          success: (String) -> Unit,
                          failed: (String) -> Unit,
                          running: () -> Unit) {
    val data = Data.Builder()
    val workManager = WorkManager.getInstance(QuranGrammarApplication.context!!)
    */
/*
         id = "10",
                        name = "Pdf File 10 MB",
                        type = "PDF",
                        url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                        downloadedUri = null
     *//*

    data.apply {
        putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, "file.name")
        putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.get(0))
        putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, "mp3")
    }

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    workManager.enqueueUniqueWork(
        "oneFileDownloadWork_${System.currentTimeMillis()}",
        ExistingWorkPolicy.KEEP,
        fileDownloadWorker
    )


    workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
        .observe(QuranGrammarApplication.context!!) { info ->
            info?.let {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        success(
                            it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI)
                                ?: ""
                        )
                    }

                    WorkInfo.State.FAILED -> {
                        failed("Downloading failed!")
                    }

                    WorkInfo.State.RUNNING -> {
                        running()
                    }

                    else -> {
                        failed("Something went wrong")
                    }
                }
            }
        }
}
*/

/*private fun startDownloadingFile(
    file: DownloadAct.File,
    success: (String) -> Unit,
    failed: (String) -> Unit,
    running: () -> Unit
) {
    val data = Data.Builder()
    val workManager = WorkManager.getInstance(applicationContext)
    data.apply {
        putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, file.name)
        putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.url)
        putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, file.type)
    }

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    workManager.enqueueUniqueWork(
        "oneFileDownloadWork_${System.currentTimeMillis()}",
        ExistingWorkPolicy.KEEP,
        fileDownloadWorker
    )


    workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
        .observe(this) { info ->
            info?.let {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        success(
                            it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI)
                                ?: ""
                        )
                    }

                    WorkInfo.State.FAILED -> {
                        failed("Downloading failed!")
                    }

                    WorkInfo.State.RUNNING -> {
                        running()
                    }

                    else -> {
                        failed("Something went wrong")
                    }
                }
            }
        }
}*/


/*
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    screen: @Composable (() -> Unit)
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            //The Login Content needs to be here


            BackHandler(enabled = true) {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }

        },
        sheetPeekHeight = 400.dp,
        sheetShape = RoundedCornerShape(topEnd = 52.dp, topStart = 52.dp),
        backgroundColor = Color.Transparent
    ) {
        screen() //Adds the content which is shown on the Screen behind bottomsheet
    }
}

fun BottomSheetScaffold(
    scaffoldState: BottomSheetScaffoldState,
    sheetContent: ColumnScope.() -> Unit,
    sheetPeekHeight: Dp,
    sheetShape: RoundedCornerShape,
    backgroundColor: Color,
    content: (PaddingValues) -> Unit
) {

}
*/

@Composable
private fun DisplayQuran(
    surahs: List<ChaptersAnaEntity>,
    chapid: Int
) {
    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        state = listState!!
    ) {
        itemsIndexed(pageArrays!!.toList()) { index, item ->
            val pages = pageArrays!![index].ayahItemsquran
            val quranEntity = pageArrays!!!!.get(index)
            val pageNum = quranEntity.pageNum
            var qtext = ""

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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                        //  .background(MaterialTheme.colorScheme.background)
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
                        Text(
                            text = "Page  " + pageNum,

                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 4.dp,
                            vertical = 4.dp
                        )
                ) {

                    if (pages != null) {
                        for (quran in pages) {
                            val builder = StringBuilder()

                            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ", "", quran!!.ayah))
                            quran.ayah
                            qtext += quran!!.qurantext + builder


                        }
                    }
                    Text(
                        text = qtext,
                        fontWeight = FontWeight.Normal,
                        // color = Color.Black,

                        textAlign = TextAlign.Justify,
                        fontSize = 20.sp,

                        fontFamily = indopak


                    )


                }


            }

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun PickerExample() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val utils= Utils(QuranGrammarApplication.context)
            val chapters = utils.getAllAnaChapters()
            val cnames=mutableStateOf("")
            val surah = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.surahdetails)
            val ayaLocations: MutableList<String> = ArrayList()
            if (chapters != null) {
                for( cha in chapters){
                    val sb=StringBuilder()
                    sb.append(cha!!.chapterid).append(" ").append(cha!!.nameenglish).append("  ").append(cha!!.namearabic)
                    ayaLocations.add(sb.toString())
                }
            }
            val values = remember { (1..144).map { it.toString() } }
            val valuesPickerState = rememberPickerState()

            val units = remember { ayaLocations }
            val unitsPickerState = rememberPickerState()

            Text(text = "Example Picker", modifier = Modifier.padding(top = 16.dp))
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
                    visibleItemsCount = 3,
                    modifier = Modifier.weight(0.7f),
                    textModifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 32.sp)
                )
            }

            Text(
                text = "Interval: ${valuesPickerState.selectedItem} ${unitsPickerState.selectedItem}",

                modifier = Modifier.padding(vertical = 16.dp)
            )

        }
    }
}
