package com.example.compose.theme


import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.codelab.basics.ui.theme.dejavu
import com.codelab.basics.ui.theme.indopak
import com.example.compose.LoadingData
import com.example.compose.TextChip
import com.example.justJava.MyTextViewZoom
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.utility.AnnotationUtility
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication.Companion.context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

var scopes: CoroutineScope? = null
var wordarray: ArrayList<NewQuranCorpusWbw>? = null
var listState: LazyListState? = null
var annotatedStringStringPair: Pair<AnnotatedString, Int>? = null
var aid: Int = 0
var cid: Int = 0
var wid: Int? = null
private val showWordDetails = mutableStateOf(false)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuranVerseScreen(navController: NavHostController, chapid: Int, quranModel: QuranVIewModel?) {


    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    scopes = CoroutineScope(Dispatchers.Main)
    var newnewadapterlist = LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>()
    var corpusSurahWord: List<QuranCorpusWbw>? = null
    val utils = Utils(context)
    val corpus = CorpusUtilityorig
    loading = quranModel!!.loading.value
    LoadingData(isDisplayed = loading)
    val quranbySurah = quranModel.getquranbySUrah(chapid).value
    //  val surahs = quranModel!!.getAllSurah().value
    val surahs = utils.getAllAnaChapters()!!


    corpusSurahWord = quranModel.getQuranCorpusWbwbysurah(chapid).value
    newnewadapterlist = corpus.composeWBWCollection(quranbySurah, corpusSurahWord)

    listState = rememberLazyListState()
    // corpusSurahWord = utils.getQuranCorpusWbwbysurah(chapid);


    val preferencesManager = remember { PreferencesManager(context!!) }
    val data = remember { mutableStateOf(preferencesManager.getData("lastread", 1)) }


    //  val state = rememberScrollState()


    preferencesManager.saveData("lastread", chapid.toString())
    data.value = chapid.toString()
    val imgs = context!!.resources.obtainTypedArray(R.array.sura_imgs)
    val myItems = remember { mutableStateOf(listOf(newnewadapterlist)) }
    val item = (1..100).toList()

    LoadingData(isDisplayed = false)
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(3) }
    LazyColumn(state = listState!!,
        modifier = Modifier.fillMaxSize(),

        content = {

            //     itemsIndexed(quranbySurah) { index, item ->
            itemsIndexed(quranbySurah!!.toList()) { index, item ->
                //   val img = imgs.getDrawable(surahs!!.chapid - 2)


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
                    RightToLeftLayout {


                        FlowRow(


                            verticalArrangement = Arrangement.Top,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            maxItemsInEachRow = 6,
                            modifier = Modifier
                                .fillMaxWidth()

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
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                Text(
                                    text = surahs[chapid]!!.abjadname,

                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red,
                                    modifier = Modifier.align(Alignment.TopCenter),
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                                if (surahs[chapid]!!.ismakki == 1) {
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
                                    text = "No.Of Aya's" + surahs[chapid]!!.versescount.toString(),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,

                                    modifier = Modifier.align(Alignment.TopStart),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "No.Of Ruku's" + surahs[chapid]!!.rukucount.toString(),

                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.align(Alignment.BottomCenter),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp
                                )
                                /*     AsyncImage(
                                         modifier = Modifier.align(Alignment.TopStart),
                                         model = img,
                                         contentDescription = "",
                                         colorFilter = ColorFilter.tint(Color.Red),
                                     )*/


                            }
                            wordarray = newnewadapterlist[index]
                            val totalItemsCount = listState!!.layoutInfo.totalItemsCount
                            println(totalItemsCount)
                            var counter = 0
                            //  for (counter in wbw.size - 1 downTo 0) {
                            //   wbw.forEach { indexval
                            var list = LinkedHashMap<AnnotatedString, Int>()
                            val lhm = LinkedHashMap<AnnotatedString, String>()
                            for (wbw in wordarray!!) {


                                list = AnnotationUtility.AnnotatedStrings(
                                    wbw.corpus!!.tagone, wbw.corpus!!.tagtwo,
                                    wbw.corpus!!.tagthree, wbw.corpus!!.tagfour,
                                    wbw.corpus!!.tagfive,

                                    wbw.corpus!!.araone!!, wbw.corpus!!.aratwo!!,
                                    wbw.corpus!!.arathree!!, wbw.corpus!!.arafour!!,
                                    wbw.corpus!!.arafive!!,
                                    wbw.corpus!!.wordno
                                )

                                val toList = list.toList()
                                annotatedStringStringPair = toList[0]

                                //    Text(text = "First index: ${listState!!.firstVisibleItemIndex}")
                                val textChipRememberOneState = remember {
                                    mutableStateOf(false)
                                }
                                /*
                                       modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                                 */
                                TextChip(

                                    isSelected = textChipRememberOneState.value,
                                    text = annotatedStringStringPair!!.first,

                                    selectedColor = Color.DarkGray,


                                    onChecked = {
                                        textChipRememberOneState.value = it
                                        Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                                        cid = wbw.corpus!!.surah
                                        aid = wbw.corpus!!.ayah
                                        wid = wbw.corpus!!.wordno
                                        showWordDetails.value = false

                                        /*
                                                                                  navController.navigate(
                                                                                       "books/${cid}/${aid}/${wid}"
                                                                                   )*/

                                        navController.navigate(
                                            "wordalert/${cid}/${aid}/${wid}"
                                        )

                                    }

                                )

                                /*            ClickableText(
                                                text = annotatedStringStringPair!!.first,

                                                onClick = { position: Int ->
                                                    Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                                                    cid = wbw.corpus!!.surah
                                                    aid = wbw.corpus!!.ayah
                                                    wid = wbw.corpus!!.wordno
                                                    showWordDetails.value = false

         *//*
                                           navController.navigate(
                                                "books/${cid}/${aid}/${wid}"
                                            )*//*

                                            navController.navigate(
                                                "wordalert/${cid}/${aid}/${wid}"
                                            )



                                        },
                                        style = TextStyle(

                                            fontSize = 26.sp,
                                            fontFamily = dejavu
                                        )
                                    )*/


                            }


                            //    startDetailActivity(cid,aid, wid!!)
                            // navController.popBackStack("verses/{id}", true)
                            //   showWordDetails.value = false
                            //  BottomSheetWordDetails(navController, viewModel(), cid, aid, wid)


                        }


                    }

                    if (showWordDetails.value) {

                        openWordDIalog(cid, aid, wid)
                        showWordDetails.value = true

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
                        Text(

                            text = quranbySurah[index].qurantext,
                            fontSize = 20.sp,
                            fontFamily = indopak,
                            color = colorResource(id = R.color.kashmirigreen)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(
                                horizontal = 10.dp,
                                vertical = 8.dp
                            )
                    ) {

                        Text(

                            text = quranbySurah[index].translation,
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.burntamber)
                        )

                    }


                }


            }
        }
    )
}

@Composable
fun openWordDIalog(cid: Int, aid: Int, wid: Int?) {
    val openDialogCustom: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }


    // CustomDialog(openDialogCustom)
    //  MyUI()

}

@Composable
fun startDetailActivity(cid: Int, aid: Int, wid: Int) {
    val context = LocalContext.current
    //  WordDialogFragment().show(context.parentFragmentManager, "TestDialogFragment")
    /*
        onDismissRequest: () -> Unit,
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
     */
    var openDialog = remember { mutableStateOf(true) }

    AlertExample()

    Column {
        AlertDialogS(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back button.
                // If you want to disable that functionality, simply leave this block empty.
                //     openDialog = false
            },
            buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // confirm button
                    Button(
                        onClick = {
                            //         dialogOpen = false
                        }
                    ) {
                        Text(text = "Confirm")
                    }

                    // dismiss button
                    Button(
                        onClick = {
                            //       openDialog = false
                        }
                    ) {
                        Text(text = "Dismiss")
                    }
                }
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Text(text = "Description")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White
        )

    }


    /*   val intent = Intent(context, MainActivitys::class.java)
     context.startActivity(intent)*/
}

@Composable
@Preview
fun AlertExample() {
    var dialogVisibility by remember { mutableStateOf(true) }
    if (dialogVisibility) {
        AlertDialog(
            onDismissRequest = {
                dialogVisibility = false
            },
            title = {
                Text(text = " Alert Title")
            },
            text = {
                Text("This is a Jetpack Compose tutorial on AlertDialog.")
            },
            confirmButton = {

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { dialogVisibility = false }
                ) {
                    Text("Okay")
                }
            }
        )
    }
}

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun VerseList(
    quran: QuranEntity,

    navController: NavHostController,
    wbw: ArrayList<NewQuranCorpusWbw>?,
    surahs: ChaptersAnaEntity?,
    imgs: TypedArray,

    ) {
    val img = imgs.getDrawable(surahs!!.chapterid - 2)


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
        RightToLeftLayout {


            FlowRow(


                verticalArrangement = Arrangement.Top,
                horizontalArrangement = Arrangement.SpaceEvenly,
                maxItemsInEachRow = 6,
                modifier = Modifier
                    .fillMaxWidth()

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
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = surahs.abjadname,

                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.TopCenter),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    if (surahs.ismakki == 1) {
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
                        text = "No.Of Aya's" + surahs.versescount.toString(),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.align(Alignment.TopStart),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "No.Of Ruku's" + surahs.rukucount.toString(),

                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                    AsyncImage(
                        modifier = Modifier.align(Alignment.TopStart),
                        model = img,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Red),
                    )


                }

                if (wbw != null) {
                    var counter = 0
                    //  for (counter in wbw.size - 1 downTo 0) {
                    //   wbw.forEach { indexval
                    var list = LinkedHashMap<AnnotatedString, Int>()
                    val lhm = LinkedHashMap<AnnotatedString, String>()
                    for (indexval in 0 until wbw.size) {


                        list = AnnotationUtility.AnnotatedStrings(
                            wbw[indexval].corpus!!.tagone, wbw[indexval].corpus!!.tagtwo,
                            wbw[indexval].corpus!!.tagthree, wbw[indexval].corpus!!.tagfour,
                            wbw[indexval].corpus!!.tagfive,

                            wbw[indexval].corpus!!.araone!!, wbw[indexval].corpus!!.aratwo!!,
                            wbw[indexval].corpus!!.arathree!!, wbw[indexval].corpus!!.arafour!!,
                            wbw[indexval].corpus!!.arafive!!,
                            wbw[indexval].corpus!!.wordno
                        )

                        val toList = list.toList()
                        annotatedStringStringPair = toList[0]

                        Text(text = "First index: ${listState!!.firstVisibleItemIndex}")



                        ClickableText(
                            text = annotatedStringStringPair!!.first,

                            onClick = { position: Int ->
                                Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                                cid = wbw[0].corpus!!.surah
                                aid = wbw[0].corpus!!.ayah
                                wid = annotatedStringStringPair!!.second

                                showWordDetails.value = true


                                navController.navigate(
                                    "books/${cid}/${aid}/${wid}"
                                )


                            },
                            style = TextStyle(

                                fontSize = 26.sp,
                                fontFamily = dejavu
                            )
                        )


                    }
                    /*     if(showWordDetails.value) {
                            // navController.popBackStack("verses/{id}", true)
                             showWordDetails.value = false
                             BottomSheetWordDetails(navController, viewModel(), cid, aid, wid)


                         }
      */

                }
            }


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
            Text(

                text = quran.qurantext,
                fontSize = 20.sp,
                fontFamily = indopak,
                color = colorResource(id = R.color.kashmirigreen)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    vertical = 8.dp
                )
        ) {

            Text(

                text = quran.translation,
                fontSize = 20.sp,
                color = colorResource(id = R.color.burntamber)
            )

        }


    }


}


@Composable
fun RightToLeftLayout(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        content()
    }
}

@Composable
fun CustomRow(
    name: String,
    cost: String,
) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .size(72.dp)
        )
        Column {
            Text(
                text = name,

                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = cost,

                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun AndroidCafe() {

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                Text(
                    text = "Android Cafe",
                    modifier = Modifier.padding(12.dp),

                    )
            }
        ) {
            Column {
                CustomRow(
                    name = "Pizza", "$5",

                    )
                CustomRow(
                    name = "Coffee", "$1",

                    )
                CustomRow(
                    name = "Salad", "$2",

                    )
                CustomRow(
                    name = "Ice Cream", "$.63",

                    )
            }
        }
    }
}