package com.appscreens

import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Movie
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSettingState
import com.appscreens.Result.*
import com.codelab.basics.ui.theme.indopak
import com.corpusutility.AnnotatedQuranMorphologyDetails
import com.corpusutility.AnnotationUtility
import com.corpusutility.refWordMorphologyDetails
import com.example.compose.LoadingData
import com.example.compose.TextChipWBW
import com.example.compose.theme.Tooltips
import com.example.justJava.MyTextViewZoom
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.searchwidgetdemo.QuranSearchViewModel
import com.example.utility.QuranGrammarApplication
import com.modelfactory.newViewModelFactory
import com.previews.nonScaledSp
import com.viewmodels.ActorsScreenState
import com.viewmodels.UserAction
import com.viewmodels.VerseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import java.text.MessageFormat

var quranbySurah: List<QuranEntity>? = null
var surahs: List<ChaptersAnaEntity>? = null
var scopes: CoroutineScope? = null
var wordarray: ArrayList<NewQuranCorpusWbw>? = null

var listState: LazyListState? = null
var aid: Int = 0
var cid: Int = 0
var wid: Int = 0
val showWordDetails = mutableStateOf(false)
val prefrence: SharedPreferences by lazy {
    QuranGrammarApplication.context!!.getSharedPreferences(
        "prefs",
        MODE_PRIVATE
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NewQuranVerseScreen(
    navController: NavHostController,
    chapid: Int,

    verseModel: VerseModel,
    darkThemePreference: BooleanPreferenceSettingValueState,


    ) {

    val verseModel: VerseModel = viewModel(factory = newViewModelFactory(chapid,darkThemePreference))

    var loading = verseModel.loading.value



    val thememode = darkThemePreference.value

    val showtranslation =
        rememberPreferenceBooleanSettingState(key = "showtranslation", defaultValue = false)
    val showwordbyword = rememberPreferenceBooleanSettingState(key = "wbw", defaultValue = false)
    val wbwchoice = rememberPreferenceIntSettingState(key = "wbwtranslation", defaultValue = 0)
    var newnewadapterlist = LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>()
  //  showWordDetails.value = false
    LoadingData(isDisplayed = loading)
    val cardss by verseModel.cards.collectAsStateWithLifecycle()


    //  val searchViewModel = viewModel<VerseModel>()
    val searchText by verseModel.searchText.collectAsState()
    val isSearching by verseModel.isSearching.collectAsState()
    val quranbySurahsearch by verseModel.quransentity.collectAsState()
   // val quranbySurahsearchs by verseModel.quransentity.collectAsStateWithLifecycle()

    //val cards by verseModel.cards.collectAsStateWithLifecycle()
    if (cardss.isNotEmpty()) {
        surahs = cardss[0].chapterlist
        quranbySurah = cardss[0].quranbySurah
        newnewadapterlist = cardss[0].newnewadapterlist
    }

    val vmstate = verseModel.state

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    listState = rememberLazyListState()


    val scrollpos = prefrence.getInt("scroll_position", 0)
    val preferencesManager = remember { PreferencesManager(QuranGrammarApplication.context!!) }
    val data = remember { mutableStateOf(preferencesManager.getData("lastread", 1)) }
    preferencesManager.saveData("lastread", chapid.toString())
    data.value = chapid.toString()


    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(3) }

    LaunchedEffect(key1 = Unit) {
        listState!!.animateScrollToItem(index = scrollpos)
    }
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
    val showButton by remember {
        derivedStateOf {
            listState!!.firstVisibleItemIndex > 0
        }
    }
    LoadingData(isDisplayed = loading)


    Column {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            elevation = 10.dp
        ) {
            Row {
                //   SearchAppBar(text = searchText, onTextChange = verseModel::onSearchTextChange, onCloseClicked = { }, onSearchClicked ={} )
                mysearchbar(
                    vmstate,
                    searchText,
                    verseModel,
                    keyboardController,
                    focusManager,
                    isSearching,
                    quranbySurahsearch,
                    newnewadapterlist,
                    navController,
                    showwordbyword,
                    showtranslation,
                    loading
                )


            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(top = 10.dp)
            ) {
                SurahDetails(
                    chapid,
                    vmstate,
                    searchText,
                    verseModel,
                    keyboardController,
                    focusManager,
                    isSearching,
                    quranbySurahsearch,
                    newnewadapterlist,
                    navController,
                    showwordbyword,
                    showtranslation
                )
            }

        }

    }


}

@Composable
private fun SurahDetails(
    chapid: Int,
    vmstate: ActorsScreenState,
    searchText: String,
    verseModel: VerseModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    isSearching: Boolean,
    quranbySurahsearch: List<QuranEntity>?,
    newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
    navController: NavHostController,
    showwordbyword: BooleanPreferenceSettingValueState,
    showtranslation: BooleanPreferenceSettingValueState
) {
    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 10.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {


            Text(

                text = surahs!![chapid - 1].abjadname,

                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.align(Alignment.TopCenter),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            if (surahs!![chapid - 1].ismakki == 1) {
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
                text = "No.Of Aya's" + surahs!![chapid - 1].versescount.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,

                modifier = Modifier.align(Alignment.TopStart),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Text(
                text = "No.Of Ruku's" + surahs!![chapid - 1].rukucount.toString(),

                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.BottomStart),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }


    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun mysearchbar(
    vmstate: ActorsScreenState,
    searchText: String,
    verseModel: VerseModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    isSearching: Boolean,
    quranbySurahsearch: List<QuranEntity>?,
    newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
    navController: NavHostController,
    showwordbyword: BooleanPreferenceSettingValueState,
    showtranslation: BooleanPreferenceSettingValueState,
    loading: Boolean,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            //    .background(BackgroundColor)
            .padding(20.dp)
    ) {
        Crossfade(
            targetState = vmstate.isSearchBarVisible,
            animationSpec = tween(durationMillis = 500)
        ) {
            if (it) {
                SearchAppBar(
                    searchText,
                    verseModel,
                    onCloseIconClicked = {
                        verseModel.onAction(UserAction.CloseIconClicked)
                    },

                    onInputValueChange = { newText ->
                        verseModel.onAction(
                            UserAction.TextFieldInput(newText)
                        )
                    },
                    text = vmstate.searchText,
                    onSearchClicked = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            } else {
                TopSearchAppBar(
                    onSearchIconClicked = {
                        verseModel.onAction(UserAction.SearchIconClicked)
                    }


                )
            }
        }

        Divider(
            //   color = CardBackgroundColor,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        thememode = rememberPreferenceBooleanSettingState(key = "Dark", defaultValue = false)
        LoadingData(isDisplayed = false)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),

            state = listState!!
        ) {


            /*     AsyncImage(
                                 modifier = Modifier.align(Alignment.TopStart),
                                 model = img,
                                 contentDescription = "",
                                 colorFilter = ColorFilter.tint(Color.Red),
                             )*/




            itemsIndexed(quranbySurahsearch!!.toList()) { index, item ->


                Card(
                    colors = CardDefaults.cardColors(
                        //      containerColor = colorResource(id = R.color.bg_surface_dark_blue),
                    ), elevation = CardDefaults.cardElevation(
                        defaultElevation = 16.dp
                    ), modifier = Modifier
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
                            val builder = StringBuilder()
                            wordarray = newnewadapterlist[index]
                            //    builder.append("{").append(wordarray!![0].corpus!!.ayah  ).append("} ")
                            builder.append(
                                MessageFormat.format(
                                    "{0} ﴿ {1} ﴾ ",
                                    "",
                                    wordarray!![0].corpus!!.ayah
                                )
                            )
                            Text(
                                text = wordarray!![0].corpus!!.ayah.toString(),

                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                //   modifier = Modifier.align(Alignment.BottomCenter),
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )
                            if (showwordbyword.value) {
                                // Text(text = wbw.wbw!!.en)


                                wordarray = newnewadapterlist[index]
                                val totalItemsCount = listState!!.layoutInfo.totalItemsCount


                                var list = LinkedHashMap<AnnotatedString, String>()
                                val lhm = LinkedHashMap<AnnotatedString, String>()

                                for (wbw in wordarray!!) {


                                    list = AnnotationUtility.AnnotatedStrings(
                                        wbw.corpus!!.tagone, wbw.corpus!!.tagtwo,
                                        wbw.corpus!!.tagthree, wbw.corpus!!.tagfour,
                                        wbw.corpus!!.tagfive,

                                        wbw.corpus!!.araone!!, wbw.corpus!!.aratwo!!,
                                        wbw.corpus!!.arathree!!, wbw.corpus!!.arafour!!,
                                        wbw.corpus!!.arafive!!,
                                        wbw.wbw!!.en, thememode!!.value
                                    )

                                    val toList = list.toList()
                                    val annotatedStringStringPair = toList[0]
                                    val aword = annotatedStringStringPair.first
                                    val ln = "\n"
                                    val tra = annotatedStringStringPair.second

                                    val fword = aword + AnnotatedString(ln) + AnnotatedString(tra)


                                    //    Text(text = "First index: ${listState!!.firstVisibleItemIndex}")
                                    val textChipRememberOneState = remember {
                                        mutableStateOf(false)
                                    }
                                    showWordDetails.value=true

                                    TextChipWBW(
                                        showWordDetails.value,
                                        navController,
                                        thememode!!.value,
                                        isSelected = textChipRememberOneState.value,
                                        text = fword ,


                                        onChecked = {
                                            cid = wbw.corpus!!.surah
                                            aid = wbw.corpus!!.ayah
                                            wid = wbw.corpus!!.wordno

                                            textChipRememberOneState.value = it
                                            Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                                            cid = wbw.corpus!!.surah
                                            aid = wbw.corpus!!.ayah
                                            wid = wbw.corpus!!.wordno

                                            showWordDetails.value = true

                                        /*   navController.navigate(
                                                "wordalert/$cid/$aid/$wid"
                                            )
*/
                                        },


                                        selectedColor = Color.DarkGray,

                                        )


                                }

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
                            wordarray = newnewadapterlist[index]

                            ClickableText(
                                text = wordarray!![0].annotatedVerse!!,

                                onClick = {
                                    val cid = quranbySurah!![index].surah
                                    val aid = quranbySurah!![index].ayah
                                    navController.navigate(
                                        "versealert/${cid}/${aid}"
                                    )

                                }, style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize.nonScaledSp,
                                   // fontSize = 20.sp,
                                    fontFamily = indopak
                                )
                            )
                        }


                    }


                    //    startDetailActivity(cid,aid, wid!!)
                    // navController.popBackStack("verses/{id}", true)
                    //   showWordDetails.value = false
                    //  BottomSheetWordDetails(navController, viewModel(), cid, aid, wid)



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

                                text = AnnotatedString(quranbySurahsearch!![index].translation)
                                /*    fontSize = 20.sp,
                                fontFamily = indopak,
                                color = colorResource(id = R.color.kashmirigreen)*/
                            )
                        }
                    }
                     val tafsirKathir = quranbySurahsearch!![0].tafsir_kathir
                    tafsirKathir.replace("<b>","").replace("</b>","")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(
                                horizontal = 10.dp,
                                vertical = 8.dp
                            )
                    ) {
                        ExpandableText(
                            text = AnnotatedString("Ibne Kathir :" + quranbySurahsearch!![0].tafsir_kathir.replace("<b>","").replace("</b>",""))
                        )
                    }
              /*      if (showWordDetails.value) {
                        //    BottomSheetWordDetails(navController, viewModel(), cid, aid, wid)
                        WordAlertDialog(viewModel(),cid,aid,wid,navController)

                    }*/

                }

            }
        }
        /*     LazyColumn {
                 this.items(vmstate.list) { actor ->
                     SingleItemCard(
                         actorName = actor
                     )
                     Spacer(modifier = Modifier.height(15.dp))
                 }
             } */
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordAlertDialog(
    mainViewModel: QuranVIewModel,
    chapterid: Int?,
    verseid: Int?,
    wordno: Int?,
    navController: NavHostController
) {

    val corpusSurahWord = mainViewModel.getQuranCorpusWbw(chapterid!!, verseid!!, wordno!!).value

    var vbdetail = HashMap<String, String?>()
    val quran = mainViewModel.getsurahayahVerseslist(chapterid!!, verseid!!).value
    val corpusNounWord = mainViewModel.getNouncorpus(chapterid!!, verseid!!, wordno!!).value

    val verbCorpusRootWord =
        mainViewModel.getVerbRootBySurahAyahWord(chapterid!!, verseid!!, wordno!!).value

    val mafoolbihi = mainViewModel.getMafoolbihiword(chapterid, verseid, wordno).value
    val haliaSentence = mainViewModel.gethalsurahayah(chapterid, verseid).value
    val tameezWord = mainViewModel.getTameezword(chapterid, verseid, wordno).value
    val liajlihiEntArrayList = mainViewModel.getAjlihiword(chapterid, verseid, wordno).value
    val mutlaqword = mainViewModel.getMutlaqWOrd(chapterid, verseid, wordno).value
    val am = AnnotatedQuranMorphologyDetails(
        corpusSurahWord!!,
        corpusNounWord as ArrayList<NounCorpus>?,
        verbCorpusRootWord as ArrayList<VerbCorpus>?,
        QuranGrammarApplication.context
    )


    worddetails = am.wordDetails
    // wordbdetail = am.wordDetails
    if (verbCorpusRootWord != null) {
        if (verbCorpusRootWord.isNotEmpty() && verbCorpusRootWord[0].tag.equals("V")) {
            vbdetail = am.verbDetails
            //  isVerb = true
        }
    }

    if (tameezWord != null) {
        if (tameezWord.isNotEmpty()) {
            val tameezwordspan = StringBuilder()
            tameezwordspan.append("(").append("تمييز").append(")")
            tameezwordspan.append(tameezWord!![0].word)
            worddetails["tameez"] = AnnotatedString(tameezwordspan.toString())
        }
    }
    if (liajlihiEntArrayList != null) {
        if (liajlihiEntArrayList.isNotEmpty()) {
            val ajlihiwordspan = StringBuilder()
            ajlihiwordspan.append("(").append("مفعول لأجله").append(")")
            ajlihiwordspan.append(liajlihiEntArrayList[0].word)
            worddetails["liajlihi"] = AnnotatedString(ajlihiwordspan.toString())

        }
    }

    if (mutlaqword != null) {
        if (mutlaqword.isNotEmpty()) {
            val ajlihiwordspan = StringBuilder()
            ajlihiwordspan.append("(").append("مفعول المطلق").append(")")
            ajlihiwordspan.append(mutlaqword[0].word)
            worddetails["mutlaqword"] = AnnotatedString(ajlihiwordspan.toString())
        }
    }

    val openDialog = remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }

    if (openDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    content = {

                        //      Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = worddetails["surahid"].toString() + ":" + worddetails["ayahid"].toString() + ":" + worddetails["wordno"].toString(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,

                            )



                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(

                                    horizontal = 4.dp
                                )

                                .clip(shape = CircleShape)

                                .padding(4.dp)

                        ) {
                            if (worddetails["word"] != null) {
                                val annotatedString = worddetails["word"]
                                if (annotatedString != null) {
                                    AssistChip(
                                        elevation = AssistChipDefaults.assistChipElevation(
                                            elevation = 16.dp
                                        ),
                                        modifier = Modifier.padding(1.dp),
                                        onClick = {

                                        },
                                        label = {
                                            Text(annotatedString)

                                        },

                                        leadingIcon = {
                                            Icon(
                                                Icons.Filled.Settings,
                                                contentDescription = "Localized description",
                                                Modifier.size(AssistChipDefaults.IconSize)
                                            )
                                        }
                                    )

                                }
                            }
                        }

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
                            if (worddetails["liajlihi"] != null) {
                                val annotatedString = worddetails["liajlihi"]
                                if (annotatedString != null) {
                                    AssistChip(
                                        elevation = AssistChipDefaults.assistChipElevation(
                                            elevation = 16.dp
                                        ),
                                        modifier = Modifier.padding(1.dp),
                                        onClick = {

                                        },
                                        label = {
                                            Text(annotatedString)

                                        },

                                        leadingIcon = {
                                            Icon(
                                                Icons.Filled.Settings,
                                                contentDescription = "Localized description",
                                                Modifier.size(AssistChipDefaults.IconSize)
                                            )
                                        }
                                    )

                                }
                            }
                        }
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
                            if (worddetails["tameez"] != null) {
                                val annotatedString = worddetails["tameez"]
                                if (annotatedString != null) {
                                    AssistChip(
                                        elevation = AssistChipDefaults.assistChipElevation(
                                            elevation = 16.dp
                                        ),
                                        modifier = Modifier.padding(1.dp),
                                        onClick = {

                                        },
                                        label = {
                                            Text(annotatedString)

                                        },

                                        leadingIcon = {
                                            Icon(
                                                Icons.Filled.Settings,
                                                contentDescription = "Localized description",
                                                Modifier.size(AssistChipDefaults.IconSize)
                                            )
                                        }
                                    )

                                }
                            }
                        }



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
                            if (worddetails["mutlaqword"] != null) {
                                val annotatedString = worddetails["mutlaqword"]
                                if (annotatedString != null) {

                                    AssistChip(
                                        elevation = AssistChipDefaults.assistChipElevation(
                                            elevation = 16.dp
                                        ),
                                        modifier = Modifier.padding(1.dp),
                                        onClick = {

                                        },
                                        label = {
                                            Text(annotatedString)

                                        },

                                        leadingIcon = {
                                            Icon(
                                                Icons.Filled.Settings,
                                                contentDescription = "Localized description",
                                                Modifier.size(AssistChipDefaults.IconSize)
                                            )
                                        }
                                    )

                                }
                            }
                        }


                        Row(modifier = Modifier.padding(1.dp)) {

                            if (worddetails["PRON"] != null) {
                                Text(
                                    text = worddetails["PRON"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )
                            }
                        }
                        Row(modifier = Modifier.padding(1.dp)) {
                            val annotatedString = worddetails["worddetails"]
                            if (worddetails["worddetails"] != null) {
                                Text(
                                    text = annotatedString!!,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 18.sp,

                                    )
                            }
                        }

                        Row(modifier = Modifier.padding(1.dp)) {
                            if (worddetails["noun"] != null) {
                                val annotatedString = worddetails["noun"];
                                if (annotatedString != null) {
                                    Text(
                                        text = annotatedString,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 21.sp,

                                        )
                                }
                            }
                        }
                        Row(modifier = Modifier.padding(1.dp)) {
                            //  if (worddetails["lemma"] != null || worddetails["lemma"]!!.isNotEmpty()) {
                            Text(
                                text = corpusSurahWord[0].wbw.en,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )

                            //    }
                        }

                        Row(modifier = Modifier.padding(1.dp)) {
                            if (worddetails["lemma"] != null || worddetails["lemma"]!!.isNotEmpty()) {
                                Text(
                                    text = "Lemma:  " + worddetails["lemma"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,
                                )

                            }
                        }

                        Row(modifier = Modifier.padding(1.dp)) {
                            if (worddetails["translation"] != null) {
                                Text(
                                    text = "Translation" + worddetails["translation"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,
                                )
                            }
                        }



                        Row(modifier = Modifier.padding(1.dp)) {
                            if (worddetails["root"] != null) {
                                Text(
                                    text = "Root:" + worddetails["root"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )

                            }
                        }
                        Row(modifier = Modifier.padding(1.dp)) {
                            if (vbdetail["formnumber"] != null) {
                                Text(
                                    text = vbdetail["formnumber"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )

                            }
                        }

                        //
                        Row(modifier = Modifier.padding(1.dp)) {
                            if (vbdetail["mazeed"] != null) {
                                Text(
                                    text = vbdetail["mazeed"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )

                            }
                        }
                        Row(modifier = Modifier.padding(1.dp)) {
                            if (vbdetail["verbmood"] != null) {
                                Text(
                                    text = vbdetail["verbmood"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )

                            }
                        }

                        val vb: StringBuilder = StringBuilder()
                        vb.append("V-")
                        if (vbdetail["thulathi"] != null) {
                            vb.append(vbdetail["thulathi"])
                        }
                        if (vbdetail["png"] != null) {
                            vb.append(vbdetail["png"])
                        }
                        if (vbdetail["tense"] != null) {
                            vb.append(vbdetail["tense"])
                        }
                        if (vbdetail["voice"] != null) {
                            vb.append(vbdetail["voice"])
                        }
                        if (vbdetail["mood"] != null) {
                            vb.append(vbdetail["mood"])
                        }
                        if (vb.length > 2) {

                            Row(modifier = Modifier.padding(1.dp)) {

                                Text(
                                    text = vb.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )

                            }

                            //  holder.verbdetails.setTextSize(arabicFontsize);
                        }
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
                            if (vbdetail["thulathi"] != null) {
                                val conjugation = vbdetail["wazan"].toString()
                                val root = vbdetail["root"].toString()
                                val mood = vbdetail["verbmood"].toString()

                                AssistChip(
                                    elevation = AssistChipDefaults.assistChipElevation(
                                        elevation = 16.dp
                                    ),

                                    onClick = {

                                        navController.navigate(
                                            "conjugator/${conjugation}/${root}/${mood}"
                                        )
                                    },
                                    label = { Text("Conjugate" + vbdetail["thulathi"].toString()) },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.Settings,
                                            contentDescription = "Localized description",
                                            Modifier.size(AssistChipDefaults.IconSize)
                                        )
                                    }
                                )

                            }

                        }
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
                            if (vbdetail["formnumber"] != null) {
                                val conjugation = vbdetail["form"].toString()
                                val root = vbdetail["root"].toString()
                                val mood = vbdetail["verbmood"].toString()
                                AssistChip(
                                    elevation = AssistChipDefaults.assistChipElevation(
                                        elevation = 16.dp
                                    ),

                                    onClick = {

                                        navController.navigate(
                                            "conjugator/${conjugation}/${root}/${mood}"
                                        )
                                    },
                                    label = { Text("Conjugate" + vbdetail["formnumber"].toString()) },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.Settings,
                                            contentDescription = "Localized description",
                                            Modifier.size(AssistChipDefaults.IconSize)
                                        )
                                    }
                                )
                            }
                            if (worddetails["form"] != null && worddetails["form"]!!.text != "I") {
                                val conjugation = worddetails["form"].toString()
                                val root = worddetails["root"].toString()
                                val mood = "Indicative"
                                AssistChip(
                                    elevation = AssistChipDefaults.assistChipElevation(
                                        elevation = 16.dp
                                    ),

                                    onClick = {

                                        navController.navigate(
                                            "conjugator/${conjugation}/${root}/${mood}"
                                        )
                                    },
                                    label = { Text("Conjugate" + vbdetail["formnumber"].toString()) },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.Settings,
                                            contentDescription = "Localized description",
                                            Modifier.size(AssistChipDefaults.IconSize)
                                        )
                                    }
                                )

                            }

                        }



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
                            if (worddetails["arabicword"] != null) {
                                val root = worddetails["root"]
                                AssistChip(
                                    elevation = AssistChipDefaults.assistChipElevation(
                                        elevation = 16.dp
                                    ),

                                    onClick = {

                                        navController.navigate(
                                            "wordoccurance/${root}"
                                        )

                                    },
                                    label = { Text("Word Occurance" + worddetails["root"].toString()) },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.Settings,
                                            contentDescription = "Localized description",
                                            Modifier.size(AssistChipDefaults.IconSize)
                                        )
                                    }
                                )

                                val textChipRememberOneState = remember {
                                    mutableStateOf(false)
                                }
                                /*          Button(
                                              modifier = Modifier
                                                  .padding(20.dp),
                                              onClick = {

                                                  navController.navigate(
                                                      "wordoccurance/${root}"
                                                  )

                                    }
                                ) {
                                    Text(
                                        text = "Word Occurance" + worddetails["root"].toString()
                                    )
                                }
                  */

                            }

                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()

                        //     .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp),

                    )
            }
        }
    }

}

/*
@Composable
private fun extracted(
    vmstate: ActorsScreenState,
    verseModel: VerseModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            //    .background(BackgroundColor)
            .padding(20.dp)
    ) {
        Crossfade(
            targetState = vmstate.isSearchBarVisible,
            animationSpec = tween(durationMillis = 500)
        ) {
            if (it) {
                SearchAppBar(
                    onCloseIconClicked = {
                        verseModel.onAction(UserAction.CloseIconClicked)
                    },
                    onInputValueChange = { newText ->
                        verseModel.onAction(
                            UserAction.TextFieldInput(newText)
                        )
                    },
                    text = vmstate.searchText,
                    onSearchClicked = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            } else {
                TopSearchAppBar(
                    onSearchIconClicked = {
                        verseModel.onAction(UserAction.SearchIconClicked)
                    }


                )
            }
        }

        Divider(
            //   color = CardBackgroundColor,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        LazyColumn {
            this.items(vmstate.list) { actor ->
                SingleItemCard(
                    actorName = actor
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}
*/


@Composable
fun SingleItemCard(
    actorName: String
) {
    Card(


        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
    )


    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material.Icon(
                painter = painterResource(R.drawable.arrow_down),
                contentDescription = "People Icon"
            )
            Spacer(modifier = Modifier.width(10.dp))
            androidx.compose.material.Text(
                text = actorName,
                fontSize = 20.sp,
                //   fontFamily = cairoFont
            )
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplySearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    searchViewModel: QuranSearchViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp)
            .background(MaterialTheme.colorScheme.background, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {

    }
}

@Composable
fun ReplyProfileImage(
    drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        painter = painterResource(id = drawableResource),
        contentDescription = description,
    )
}


@Composable
fun RightToLeftLayout(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: List<Movie>,
    onSearchQueryChange: (String) -> Unit
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        placeholder = {
            Text(text = "Search movies")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {},
        content = {},
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}

@Composable
fun TopSearchAppBar(
    onSearchIconClicked: () -> Unit,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Search",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            //  color = Color.White,
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onSearchIconClicked) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon",
                //   tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Composable
fun SearchAppBar(
    searchText: String,
    verseModel: VerseModel,
    onCloseIconClicked: () -> Unit,
    onInputValueChange: (String) -> Unit,
    text: String,
    onSearchClicked: () -> Unit
) {
    Row {
        //   SearchAppBar(text = searchText, onTextChange = verseModel::onSearchTextChange, onCloseClicked = { }, onSearchClicked ={} )
        val lightBlue = Color(0xffd8e6ff)
        val blue = Color(0xff76a9ff)
        TextField(
            value = searchText, onValueChange = verseModel::onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                //    .background(MaterialTheme.colorScheme.inversePrimary)
                .padding(15.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),

            shape = RoundedCornerShape(48.dp),
            placeholder = {
                Text(
                    text = "Search...",
                    // fontFamily = cairoFont,
                    //  color = Color.White.copy(alpha = ContentAlpha.medium)
                )
            },

            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            trailingIcon = {
                androidx.compose.material.IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onInputValueChange("")
                        } else {
                            onCloseIconClicked()
                        }
                    }
                ) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Blue
                    )
                }
            },

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightBlue,
                cursorColor = Color.Black,
                disabledLabelColor = lightBlue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

            /*   colors = TextFieldDefaults.outlinedTextFieldColors(
                   unfocusedBorderColor = Color.Red.copy(
                       alpha = ContentAlpha.medium
                   ),
                   focusedBorderColor = Color.Red,
                   cursorColor = Color.White,
               ),
   */
            /*
                          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                  keyboardActions = KeyboardActions(
                      onSearch = { onSearchClicked() }
                  )*/

        )


        Spacer(modifier = Modifier.height(5.dp))

    }


}


@Composable
fun Tooltips(utils: Utils) {
    //  TooltipOnLongClickExample()
    val openDialogCustom: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }


    val tooltipPopupProperties = PopupProperties(focusable = true)
    val tooltipOffset = DpOffset(0.dp, 0.dp)
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
            wordarray!![0].corpus!!,
            corpusNounWord,
            verbCorpusRootWord
        )
    val workBreakDown = qm.workBreakDown
    Tooltips(
        expanded = openDialogCustom,
        modifier = Modifier,
        2000L,
        tooltipOffset,
        tooltipPopupProperties
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


        }
    }

}


/*
@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
private fun Display(
    chapid: Int,
    newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
    thememode: Boolean,
    navController: NavHostController,
    showwordbyword: BooleanPreferenceSettingValueState,
    showtranslation: BooleanPreferenceSettingValueState,
    quranbySurahsearch: List<QuranEntity>?,
    searchText: String,
    verseModel: VerseModel,
    isSearching: Boolean
) {
    val showButton by remember {
        derivedStateOf {
            listState!!.firstVisibleItemIndex > 0
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        extracted(
            chapid,
            newnewadapterlist,
            thememode,
            navController,
            showwordbyword,
            showtranslation,
            quranbySurahsearch,
            searchText,
            verseModel,
            isSearching
        )
        if (showButton) {
            val coroutineScope = rememberCoroutineScope()
            SmallFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 1.dp),
                onClick = {
                    coroutineScope.launch {
                        listState!!.scrollToItem(0)
                    }
                }
            )


            {
                Text("Up!")
            }
        }

    }
}
*/
/*
@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
private fun extracted(
    chapid: Int,
    newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
    thememode: Boolean,
    navController: NavHostController,
    showwordbyword: BooleanPreferenceSettingValueState,
    showtranslation: BooleanPreferenceSettingValueState,
    quranbySurahsearch: List<QuranEntity>?,
    searchText: String,
    verseModel: VerseModel,
    isSearching: Boolean
) {
    /*
        shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
     */



    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 10.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {


            Text(

                text = surahs!![chapid - 1].abjadname,

                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.align(Alignment.TopCenter),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            if (surahs!![chapid - 1].ismakki == 1) {
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
                text = "No.Of Aya's" + surahs!![chapid - 1].versescount.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,

                modifier = Modifier.align(Alignment.TopStart),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Text(
                text = "No.Of Ruku's" + surahs!![chapid - 1].rukucount.toString(),

                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.BottomStart),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }
        Column {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                elevation = 10.dp
            ) {
                Row {
                    //   SearchAppBar(text = searchText, onTextChange = verseModel::onSearchTextChange, onCloseClicked = { }, onSearchClicked ={} )

                    TextField(value = searchText, onValueChange = verseModel::onSearchTextChange,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        },
                        placeholder = { Text(text = "Search: Surah#/Surah English Name") }


                    )


                    Spacer(modifier = Modifier.height(5.dp))

                }

            }

        }
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),

            state = listState!!
        ) {


            /*     AsyncImage(
                                 modifier = Modifier.align(Alignment.TopStart),
                                 model = img,
                                 contentDescription = "",
                                 colorFilter = ColorFilter.tint(Color.Red),
                             )*/




            itemsIndexed(quranbySurahsearch!!.toList()) { index, item ->


                Card(
                    colors = CardDefaults.cardColors(
                        //      containerColor = colorResource(id = R.color.bg_surface_dark_blue),
                    ), elevation = CardDefaults.cardElevation(
                        defaultElevation = 16.dp
                    ), modifier = Modifier
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
                            val builder = StringBuilder()
                            wordarray = newnewadapterlist[index]
                            //    builder.append("{").append(wordarray!![0].corpus!!.ayah  ).append("} ")
                            builder.append(
                                MessageFormat.format(
                                    "{0} ﴿ {1} ﴾ ",
                                    "",
                                    wordarray!![0].corpus!!.ayah
                                )
                            )
                            Text(
                                text = wordarray!![0].corpus!!.ayah.toString(),

                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                //   modifier = Modifier.align(Alignment.BottomCenter),
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )
                            if (showwordbyword.value) {
                                // Text(text = wbw.wbw!!.en)


                                wordarray = newnewadapterlist[index]
                                val totalItemsCount = listState!!.layoutInfo.totalItemsCount


                                var list = LinkedHashMap<AnnotatedString, String>()
                                val lhm = LinkedHashMap<AnnotatedString, String>()

                                for (wbw in wordarray!!) {


                                    list = AnnotationUtility.AnnotatedStrings(
                                        wbw.corpus!!.tagone, wbw.corpus!!.tagtwo,
                                        wbw.corpus!!.tagthree, wbw.corpus!!.tagfour,
                                        wbw.corpus!!.tagfive,

                                        wbw.corpus!!.araone!!, wbw.corpus!!.aratwo!!,
                                        wbw.corpus!!.arathree!!, wbw.corpus!!.arafour!!,
                                        wbw.corpus!!.arafive!!,
                                        wbw.wbw!!.en, thememode
                                    )

                                    val toList = list.toList()
                                    val annotatedStringStringPair = toList[0]
                                    val aword = annotatedStringStringPair.first
                                    val ln = "\n"
                                    val tra = annotatedStringStringPair.second

                                    val fword = aword + AnnotatedString(ln) + AnnotatedString(tra)


                                    //    Text(text = "First index: ${listState!!.firstVisibleItemIndex}")
                                    val textChipRememberOneState = remember {
                                        mutableStateOf(false)
                                    }

                                    TextChipWBW(
                                        showWordDetails.value,
                                        navController,
                                        thememode,

                                        isSelected = textChipRememberOneState.value,

                                        text = fword,
                                        onChecked = {
                                            cid = wbw.corpus!!.surah
                                            aid = wbw.corpus!!.ayah
                                            wid = wbw.corpus!!.wordno

                                            textChipRememberOneState.value = it
                                            Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                                            cid = wbw.corpus!!.surah
                                            aid = wbw.corpus!!.ayah
                                            wid = wbw.corpus!!.wordno

                                            showWordDetails.value = true

                                       /*     navController.navigate(
                                                "wordalert/$cid/$aid/$wid"
                                            )*/





                                        },
                                        selectedColor = Color.DarkGray,

                                        )


                                }


                            }
                        }


                    }


                    //    startDetailActivity(cid,aid, wid!!)
                    // navController.popBackStack("verses/{id}", true)
                    //   showWordDetails.value = false
                    //  BottomSheetWordDetails(navController, viewModel(), cid, aid, wid)


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(
                                horizontal = 10.dp,
                                vertical = 8.dp
                            )
                    ) {
                        wordarray = newnewadapterlist[index]

                        ClickableText(
                            text = wordarray!![0].annotatedVerse!!,

                            onClick = {
                                val cid = quranbySurah!![index].surah
                                val aid = quranbySurah!![index].ayah
                                navController.navigate(
                                    "versealert/${cid}/${aid}"
                                )

                            }, style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Cursive
                            )
                        )
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

                                text = AnnotatedString(quranbySurahsearch!![index].translation)
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
                        ExpandableText(
                            text = AnnotatedString("Ibne Kathir :" + quranbySurahsearch!![0].tafsir_kathir)
                        )
                    }
                    /*      Row(
                              modifier = Modifier
                                  .fillMaxWidth()

                                  .padding(
                                      horizontal = 10.dp,
                                      vertical = 8.dp
                                  )
                          ) {
                              ExpandableText(
                                  text = AnnotatedString("Trans literation :" + quranbySurahsearch!![0].en_transliteration)
                              )
                          }
      */

                }
              /*  if (showWordDetails.value) {
                    BottomSheetWordDetails(navController, viewModel(), cid, aid, wid)
                    //
                    //  Bottoms()
                    //    extractedtooltips(utils)
                    *//*

                                        var openBottomSheet by remember { mutableStateOf(false) }
                                        //   BottomSheetWordDetails(navController, viewModel(), cid, aid, wid)
                                        ModalBottomSheet(onDismissRequest = { openBottomSheet = false }) {

                                        }
                        *//*

                }*/
            }
        }
    }
}

 */