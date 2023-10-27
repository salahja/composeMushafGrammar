package com.example.compose.activity

import CardsScreen
import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.CardsViewModel
import com.example.compose.ConjugationScreen
import com.example.compose.NavigationItem
import com.example.compose.NewQuranMorphologyDetails
import com.example.compose.SurahListScreen
import com.example.compose.TextChip
import com.example.compose.VerseModel
import com.example.compose.theme.NewQuranVerseScreen
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.tabcompose.TabItem
import com.example.utility.QuranGrammarApplication
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.skyyo.expandablelist.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import com.example.tabcompose.*


lateinit var worddetails: HashMap<String, SpannableStringBuilder?>

class BottomCompose : AppCompatActivity() {
    private val viewModel by viewModels<QuranVIewModel>()
    private lateinit var allAnaChapters: List<ChaptersAnaEntity?>
    private lateinit var imgs: TypedArray
    lateinit var mainViewModel: QuranVIewModel

    //  allofQuran = mainViewModel.getquranbySUrah(chapterno).value













    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            val bundle: Bundle? = intent.extras
            //    root = bundle?.getString(Constant.QURAN_VERB_ROOT)
            //  val defArgs = bundleOf("root" to root)

            val isDarkThemeEnabled = remember { mutableStateOf(false) }
            AppTheme() {
                val quranModel by viewModels<QuranVIewModel>()

                val coroutineScope = rememberCoroutineScope()
                val scaffoldState: ScaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val navBackStackEntry
                        by navController.currentBackStackEntryAsState()

                MainScreen(viewModel)


            }


        }


    }
}


/*class ViewModelFactory(
    private val mApplication: Application,
    private val chapterid: Int

) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return VerseModel(mApplication, chapterid) as T
    }
}*/







@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(viewModel: QuranVIewModel,  ) {
    val navController = rememberNavController()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val tabs = listOf(TabItem.Music, TabItem.Movies, TabItem.Books)
    val pagerState = rememberPagerState()
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        // topBar = { TopBar() },
        bottomBar = { BottomNavigationBars(navController) },

        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController, viewModel,)
            }
        },
        //backgroundColor = colorResource(id =colorPrimaryDark),
        // backgroundColor = colorResource(R.color.colorPrimaryDark) // Set background color to avoid the white flashing when you switch between screens
    )
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    quranmodel: QuranVIewModel,


) {

    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        composable("home") {

            SurahListScreen(navController, quranmodel)
        }
        composable("verses/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments!!.getInt("id")
            if (id < 0) {
                SurahListScreen(navController, quranmodel)
            } else {

                val myViewModel: VerseModel    = viewModel(factory = newViewModelFactory(id))
                NewQuranVerseScreen(navController, id, quranmodel,myViewModel)

                /*           val dataBundle = Bundle()




                           val intent = Intent(Intent.ACTION_VIEW)
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                           val i = Intent(context, QuranDisplayActs::class.java)
                           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            i.putExtra(SURAH_INDEX,id)

                           context!!.startActivity(i)
           */


            }
        }

        composable(
            "wordoccurance/{root}",
            arguments = listOf(

                navArgument("root") {
                    type = NavType.StringType
                    defaultValue = "نصر"
                },

            )


        ) { backStackEntry ->

            val root = backStackEntry.arguments?.getString("root")
            val hamzaindex = root!!.indexOf("ء")
            var nounroot: String? = ""
            val verbindex = root!!.indexOf("ا")

            var verbroot: String? = ""
            nounroot = if (hamzaindex != -1) {
                root!!.replace("ء", "ا")
            } else {
                root
            }
            verbroot = if (verbindex != -1) {
                root!!.replace("ا", "ء")
            } else {
                root
            }
            if (root == "ACC" || root == "LOC" || root == "T") {
                val myViewModel: CardsViewModel    = viewModel(factory = CardViewModelFactory(verbroot,nounroot,true))

                nounroot=root

                CardsScreen(myViewModel)
            }else{
                val myViewModel: CardsViewModel    = viewModel(factory = CardViewModelFactory(verbroot,nounroot,false))
                nounroot=root
                CardsScreen(myViewModel)

            }

        }

        composable(
            "conjugator/{conjugation}/{root}/{mood}",
            arguments = listOf(
                navArgument("conjugation") {
                    type = NavType.StringType
                    defaultValue = "5"
                },
                navArgument("root") {
                    type = NavType.StringType
                    defaultValue = "نصر"
                },
                navArgument("mood") {
                    type = NavType.StringType
                    defaultValue = "Indicative"
                }
            )


        ) { backStackEntry ->
            val conjugation = backStackEntry.arguments?.getString("conjugation")
            val root = backStackEntry.arguments?.getString("root")
            val mood = backStackEntry.arguments?.getString("mood")

            MatTab(navController, conjugation.toString(), root.toString(), mood)
        }









        composable("books/{chapterid}/{verseid}/{wordno}",
            arguments = listOf(
                navArgument("chapterid") {
                    type = NavType.IntType
                    defaultValue = 2
                },
                navArgument("verseid") {
                    type = NavType.IntType
                    defaultValue = 3
                },
                navArgument("wordno") {
                    type = NavType.IntType
                    defaultValue = 4
                }
            )


        ) { backStackEntry ->
            val chapterid = backStackEntry.arguments?.getInt("chapterid")
            val verseid = backStackEntry.arguments?.getInt("verseid")
            val wordno = backStackEntry.arguments?.getInt("wordno")

            BottomSheetDemo(navController, viewModel(), chapterid, verseid, wordno)
        }


        composable("wordalert/{chapterid}/{verseid}/{wordno}",
            arguments = listOf(
                navArgument("chapterid") {
                    type = NavType.IntType
                    defaultValue = 2
                },
                navArgument("verseid") {
                    type = NavType.IntType
                    defaultValue = 3
                },
                navArgument("wordno") {
                    type = NavType.IntType
                    defaultValue = 4
                }
            )


        ) { backStackEntry ->
            val chapterid = backStackEntry.arguments?.getInt("chapterid")
            val verseid = backStackEntry.arguments?.getInt("verseid")
            val wordno = backStackEntry.arguments?.getInt("wordno")
            val openDialogCustom: MutableState<Boolean> = remember {
                mutableStateOf(true)
            }
            //     CustomDialog(openDialogCustom,navController, viewModel, chapterid, verseid, wordno)
            BottomSheetDemo(navController, viewModel(), chapterid, verseid, wordno)
          //  WordALert(openDialogCustom, navController, quranmodel, chapterid, verseid, wordno)
        }
















        composable("conjugation/{conjugation}/{root}/{mood}",
            arguments = listOf(
                navArgument("conjugation") {
                    type = NavType.StringType
                    defaultValue = "5"
                },
                navArgument("root") {
                    type = NavType.StringType
                    defaultValue = "نصر"
                },
                navArgument("mood") {
                    type = NavType.StringType
                    defaultValue = "Indicative"
                }
            )


        ) { backStackEntry ->
            val conjugation = backStackEntry.arguments?.getString("conjugation")
            val root = backStackEntry.arguments?.getString("root")
            val mood = backStackEntry.arguments?.getString("mood")

            ConjugationScreen(navController, conjugation.toString(), root.toString(), mood)
        }


    }
}

@Composable
fun CustomDialogs(
    openDialogCustom: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: Any,
    chapterid: Int?,
    verseid: Int?,
    wordno: Int?
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyUI(mainViewModel: QuranVIewModel, chapterid: Int?, verseid: Int?, wordno: Int?) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val utils = Utils(QuranGrammarApplication.context)

    val corpusSurahWord = mainViewModel.getQuranCorpusWbw(chapterid!!, verseid!!, wordno!!).value

    var vbdetail = HashMap<String, String?>()
    val quran = mainViewModel.getsurahayahVerseslist(chapterid!!, verseid!!).value
    val corpusNounWord = mainViewModel.getNouncorpus(chapterid!!, verseid!!, wordno!!).value

    val verbCorpusRootWord =
        mainViewModel.getVerbRootBySurahAyahWord(chapterid!!, verseid!!, wordno!!).value


    val am = NewQuranMorphologyDetails(
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
    var openBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val contextForToast = LocalContext.current.applicationContext
//openBottomSheet=true
    // app content

    // sheet content
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState
        ) {

            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = worddetails["surahid"].toString() + ":" + worddetails["ayahid"].toString() + ":" + worddetails["wordno"].toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,

                )
            Row {

                if (worddetails["arabicword"] != null) {
                    /*          CustomChip(
                                  selected = true,
                                  text = "ArabicWord" + worddetails["arabicword"].toString(),
                                  modifier = Modifier.padding(horizontal = 8.dp),


                                  )*/

                    val textChipRememberOneState = remember {
                        mutableStateOf(false)
                    }
                    TextChip(
                        isSelected = textChipRememberOneState.value,
                        text = "ArabicWord" + worddetails["arabicword"].toString(),

                        selectedColor = Color.DarkGray,
                        onChecked = {
                            textChipRememberOneState.value = it
                        }
                    )


                }
                /*
                         Chip(onClick = { *//*TODO*//* }) {



                            if (worddetails["arabicword"] != null) {
                                Text(
                                    text = "ArabicWord" + worddetails["arabicword"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                )
                            }
                        }*/

            }

            Row {

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
            Row {

                if (worddetails["worddetails"] != null) {
                    Text(
                        text = worddetails["worddetails"].toString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,

                        )
                }
            }




            Row {
                if (worddetails["noun"] != null) {
                    Text(
                        text = "Noun" + worddetails["noun"].toString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,

                        )
                }
            }

            Row {
                if (worddetails["lemma"] != null || worddetails["lemma"]!!.isNotEmpty()) {
                    Text(
                        text = "Lemma" + worddetails["lemma"].toString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,

                        )

                }
            }
            Row {
                if (worddetails["arabicword"] != null) {
                    Text(
                        text = "ArabicWord" + worddetails["arabicword"].toString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,

                        )
                }
            }
            Row {
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



            Row {
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
            Row {
                if (worddetails["formnumber"] != null) {
                    Text(
                        text = worddetails["form"].toString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,

                        )


                }
            }

            //
            Row {
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
            Row {
                if (vbdetail["form"] != null) {
                    Text(
                        text = vbdetail["form"].toString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,

                        )


                }
            }


            Row {
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


                Row {

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

        }
    }
}


@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun BottomSheetDemo(

    navController: NavHostController,
    mainViewModel: QuranVIewModel,
    chapterid: Int?,
    verseid: Int?,
    wordno: Int?
) {
    val utils = Utils(QuranGrammarApplication.context)

    val corpusSurahWord = mainViewModel.getQuranCorpusWbw(chapterid!!, verseid!!, wordno!!).value

    var vbdetail = HashMap<String, String?>()
    val quran = mainViewModel.getsurahayahVerseslist(chapterid!!, verseid!!).value
    val corpusNounWord = mainViewModel.getNouncorpus(chapterid!!, verseid!!, wordno!!).value

    val verbCorpusRootWord =
        mainViewModel.getVerbRootBySurahAyahWord(chapterid!!, verseid!!, wordno!!).value


    val am = NewQuranMorphologyDetails(
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


    //Lets define bottomSheetScaffoldState which will hold the state of Scaffold

    //   val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topEnd = 30.dp),
        sheetContent = {
            //Ui for bottom sheet
            Column(
                content = {

                    Spacer(modifier = Modifier.padding(16.dp))
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
                            Button(
                                modifier = Modifier
                                    .padding(20.dp),
                                onClick = {

                                    navController.navigate(
                                        "conjugator/${conjugation}/${root}/${mood}"
                                    )
                                    /*     val intent = Intent(Intent.ACTION_VIEW)
                                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                         val i = Intent(context, SurahComposeAct::class.java)
                                         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                         context!!.startActivity(i)

     */
                                }
                            ) {
                                Text(
                                    text = "Conjugate" + vbdetail["thulathi"].toString()
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
                        if (vbdetail["formnumber"] != null) {
                            val conjugation = vbdetail["form"].toString()
                            val root = vbdetail["root"].toString()
                            val mood = vbdetail["verbmood"].toString()
                            Button(
                                modifier = Modifier
                                    .padding(20.dp),
                                onClick = {
                                    navController.navigate(
                                        "conjugator/${conjugation}/${root}/${mood}"
                                    )
                                }
                            ) {
                                Text(
                                    text = "Conjugate" + vbdetail["formnumber"].toString()
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
                        if (worddetails["arabicword"] != null) {
                            /*          CustomChip(
                                          selected = true,
                                          text = "ArabicWord" + worddetails["arabicword"].toString(),
                                          modifier = Modifier.padding(horizontal = 8.dp),


                                          )*/
                                 val root=     worddetails["root"]

                         /*   val textChipRememberOneState = remember {
                                mutableStateOf(false)
                            }*/
                            Button(
                                modifier = Modifier
                                    .padding(20.dp),
                                onClick = {

                                    navController.navigate(
                                        "wordoccurance/${root}"
                                    )
                                    /*     val intent = Intent(Intent.ACTION_VIEW)
                                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                         val i = Intent(context, SurahComposeAct::class.java)
                                         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                         context!!.startActivity(i)

     */
                                }
                            ) {
                                Text(
                                    text = "Word Occurance" + worddetails["root"].toString()
                                )
                            }


                        }
                        /*
                                 Chip(onClick = { *//*TODO*//* }) {



                            if (worddetails["arabicword"] != null) {
                                Text(
                                    text = "ArabicWord" + worddetails["arabicword"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                )
                            }
                        }*/

                    }

                    Row {

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
                    Row {

                        if (worddetails["worddetails"] != null) {
                            Text(
                                text = worddetails["worddetails"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )
                        }
                    }




                    Row {
                        if (worddetails["noun"] != null) {
                            Text(
                                text = "Noun" + worddetails["noun"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )
                        }
                    }

                    Row {
                        if (worddetails["lemma"] != null || worddetails["lemma"]!!.isNotEmpty()) {
                            Text(
                                text = "Lemma" + worddetails["lemma"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )

                        }
                    }
                    Row {
                        if (worddetails["arabicword"] != null) {
                            Text(
                                text = "ArabicWord" + worddetails["arabicword"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )
                        }
                    }
                    Row {
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



                    Row {
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
                    Row {
                        if (worddetails["formnumber"] != null) {
                            Text(
                                text = worddetails["form"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )


                        }
                    }

                    //
                    Row {
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
                    Row {
                        if (vbdetail["form"] != null) {
                            Text(
                                text = vbdetail["form"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )


                        }
                    }


                    Row {
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


                        Row {

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


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)


                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp),

                )
        },
        sheetPeekHeight = 60.dp,

        ) {


        //Add button to open bottom sheet

    }
}

@Composable
fun CustomChip(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {
    // define properties to the chip
    // such as color, shape, width
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.onSurface
            else -> Color.Transparent
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.onPrimary
            else -> Color.LightGray
        },
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> MaterialTheme.colorScheme.primary
                else -> Color.LightGray
            }
        ),
        modifier = modifier
    ) {
        // Add text to show the data that we passed
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )

    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 18.sp
            )
        },
        //  backgroundColor = colorResource(id = colorPrimary),


        backgroundColor = MaterialTheme.colorScheme.inversePrimary,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CollapsedTopBar(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean
) {
    val color: Color by animateColorAsState(
        if (isCollapsed) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.primaryContainer
        }
    )
    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(visible = isCollapsed) {
            Text(text = "Library")
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CollapsableTopBar() {
    Scaffold(

        // Creating a Top Bar
        topBar = {
            TopAppBar(
                title = { Text("GFG | Collapsing Toolbar", color = Color.White) },
                backgroundColor = Color(0xff0f9d58)
            )
        },

        // Creating Content
        content = {

            // Creating a Column Layout
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Creating a Scrollable list of 100 items
                val items = (1..100).map { "Item $it" }
                val lazyListState = rememberLazyListState()
                var scrolledY = 0f
                var previousOffset = 0
                LazyColumn(
                    Modifier.fillMaxSize(),
                    lazyListState,
                ) {
                    // Setting the Image as the first
                    // item and making it collapsible
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.ic_quran_nav_top_54),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .graphicsLayer {
                                    scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                    translationY = scrolledY * 0.5f
                                    previousOffset = lazyListState.firstVisibleItemScrollOffset
                                }
                                .height(240.dp)
                                .fillMaxWidth()
                        )
                    }

                    // Displaying the remaining 100 items
                    /*    items(items) {
                            Text(
                                text = it,
                                Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }*/
                }
            }
        }
    )


}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Composable

fun BottomNavigationBars(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        //  NavigationItem.Verses,
        //  NavigationItem.Conjugator,
        //   NavigationItem.Books,
        //  NavigationItem.Conjugation
    )
    BottomNavigation(
        //  backgroundColor = colorResource(id = R.color.colorPrimary),

        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },

                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                                inclusive = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    // BottomNavigationBar()
}

class newViewModelFactory(private val dbname: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = VerseModel(dbname) as T
}
class CardViewModelFactory(private val dbname: String, private val nounroot: String, private val isharf: Boolean) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = CardsViewModel(dbname,nounroot,isharf) as T
}