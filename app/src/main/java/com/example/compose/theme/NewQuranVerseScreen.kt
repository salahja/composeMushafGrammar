package com.example.compose.theme

import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.codelab.basics.ui.theme.indopak
import com.example.compose.LoadingData
import com.example.compose.TextChip
import com.example.justJava.MyTextViewZoom
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.utility.AnnotationUtility
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import com.example.utility.refWordMorphologyDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewQuranVerseScreen(
    navController: NavHostController,
    chapid: Int,
    quranModel: QuranVIewModel,


    ) {

    val model = viewModel(modelClass = QuranVIewModel::class.java)
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    scopes = CoroutineScope(Dispatchers.Main)
 //   val chapteritems by quranModel.chapteritems.collectAsState(initial = listOf())
    val utils = Utils(QuranGrammarApplication.context)
    val corpus = CorpusUtilityorig
    loading = quranModel!!.loading.value
    LoadingData(isDisplayed = loading)
    var newnewadapterlist = LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>()
    var corpusSurahWord: List<QuranCorpusWbw>? = null

    val quranbySurah = quranModel.getquranbySUrah(chapid).value
    val surahs = quranModel.getAllSurah().value
      // quranModel.getchapters().collectAsState()
    corpusSurahWord = quranModel.getQuranCorpusWbwbysurah(chapid).value
    newnewadapterlist = corpus.composeWBWCollection(quranbySurah, corpusSurahWord)
    quranModel.setspans(newnewadapterlist, chapid)
  //  val surahs = utils.getAllAnaChapters()!!
    var annotatedlist = ArrayList<AnnotatedString>()

    // quranModel.getitall(corpusSurahWord, newnewadapterlist, chapid, quranbySurah)
    //   val quranbySurah1 = utils.getQuranbySurah(chapid)

//    corpus.setMudhafs(newnewadapterlist, chapid)

    listState = rememberLazyListState()
    // corpusSurahWord = utils.getQuranCorpusWbwbysurah(chapid);


    val preferencesManager = remember { PreferencesManager(QuranGrammarApplication.context!!) }
    val data = remember { mutableStateOf(preferencesManager.getData("lastread", 1)) }


    //  val state = rememberScrollState()


    preferencesManager.saveData("lastread", chapid.toString())
    data.value = chapid.toString()
    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)
    val myItems = remember { mutableStateOf(listOf(newnewadapterlist)) }
    val item = (1..100).toList()

    LoadingData(isDisplayed = false)
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(3) }
    //  LazyColumn(state = listState!!,      modifier = Modifier.fillMaxSize(),
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

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

                                text = surahs!![chapid]!!.abjadname,

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
                        for (wbw: NewQuranCorpusWbw in wordarray!!) {


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
                                    cid = wbw.corpus!!.surah
                                    aid = wbw.corpus!!.ayah
                                    wid = wbw.corpus!!.wordno
                                    showWordDetails.value = false
                                    textChipRememberOneState.value = it
                                    Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                                    cid = wbw.corpus!!.surah
                                    aid = wbw.corpus!!.ayah
                                    wid = wbw.corpus!!.wordno
                                    showWordDetails.value = false


                                    navController.navigate(
                                        "wordalert/$cid/$aid/$wid"
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

                        text = quranbySurah!![index].translation,
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
                    /*     wordarray = newnewadapterlist[index]
                         val annotedMousuf = AnnotationUtility.AnnotedMousuf(
                             wordarray!![0].annotatedVerse.toString(),
                             wordarray!![0].corpus!!.surah, wordarray!![0].corpus!!.ayah
                         )
 */
                    wordarray = newnewadapterlist[index]

                    wordarray!![0].annotatedVerse?.let {
                        Text(

                            text = it,
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.burntamber)
                        )
                    }


                }


            }
            if (showWordDetails.value) {

                //  TooltipOnLongClickExample()
                val openDialogCustom: MutableState<Boolean> = remember {
                    mutableStateOf(true)
                }


                val TooltipPopupProperties = PopupProperties(focusable = true)
                val TooltipOffset = DpOffset(0.dp, 0.dp)
                val corpusNounWord: ArrayList<NounCorpus?> =
                    utils.getQuranNouns(
                        cid,
                        aid,
                        wid
                    ) as ArrayList<NounCorpus?>
                val verbCorpusRootWord: List<VerbCorpus?> =
                    utils.getQuranRoot(
                        cid,
                        aid,
                        wid


                    ) as List<VerbCorpus>

                val qm =
                    refWordMorphologyDetails(
                        wordarray!!.get(0).corpus!!,
                        corpusNounWord,
                        verbCorpusRootWord
                    )
                val workBreakDown = qm.workBreakDown
                Tooltips(
                    expanded = openDialogCustom,
                    modifier = Modifier,
                    2000L,
                    TooltipOffset,
                    TooltipPopupProperties
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Red)
                        ) {


                            Text(
                                text = workBreakDown.toString(),
                                fontWeight = FontWeight.Bold,


                                textAlign = TextAlign.Center,
                                fontSize = 25.sp
                            )
                        }

                        //   MyScreen(visible = expanded)

                        //     ExpandableContent( visible = expanded)
                    }
                }

                /*

                                    Tooltips(openDialogCustom, modifier = Modifier,
                                        200L, TooltipOffset, TooltipPopupProperties,comp




                                    )
                */

                //    openWordDIalog(cid, aid, wid)
                //    showWordDetails.value = true
                // showWordDetails.value = false
            }

        }
    }
}