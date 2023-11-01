package com.example.bottomcompose




import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adaptive.theme.BottomSheetWordDetails

import com.example.compose.NewQuranMorphologyDetails
import com.example.compose.ProfileScreen
import com.example.compose.SurahListScreen
import com.example.compose.TextChip
import com.example.compose.theme.NewQuranVerseScreen


import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import com.skyyo.expandablelist.theme.AppTheme

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@Composable
fun JetRedditApp(viewModel: QuranVIewModel) {
    AppTheme {
        AppContent(viewModel)
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun AppContent(viewModel: QuranVIewModel) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Crossfade(targetState = navBackStackEntry?.destination?.route) { route: String? ->

        Scaffold(
            topBar = getTopBar(Screen.fromRoute(route), scaffoldState, coroutineScope),
            drawerContent = {
                AppDrawer(
                    onScreenSelected = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        coroutineScope.launch { scaffoldState.drawerState.close() }
                    }
                )
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationComponent(navController = navController)
            },
            content = {
                MainScreenContainer(
                    navController = navController,
                    modifier = Modifier.padding(bottom = 56.dp),
                    viewModel = viewModel
                )
            }
        )
    }
}

fun getTopBar(
    screenState: Screen,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
): @Composable (() -> Unit) {
    if (screenState == Screen.Profile || screenState == Screen.Words) {
        return {}
    } else {
        return {
            TopAppBar(
                screen = screenState,
                scaffoldState = scaffoldState,
                coroutineScope = coroutineScope
            )
        }
    }
}

/**
 * Represents top app bar on the screen
 */
@Composable
fun TopAppBar(
    screen: Screen,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {

    val colors = MaterialTheme.colors

    TopAppBar(
        title = {
            Text(
                text = stringResource(screen.titleResId),
                color = colors.primaryVariant
            )
        },
        backgroundColor = colors.surface,
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(
                    Icons.Filled.AccountCircle,
                    tint = Color.LightGray,
                    contentDescription = stringResource(id = R.string.account)
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreenContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: QuranVIewModel
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                SurahListScreen(navController,viewModel)
            }

            composable( "verses/{chapterid}?{verseid}?{wordno}",
                arguments = listOf(
                    navArgument(name = "chapterid") {
                        type = NavType.StringType
                        defaultValue = "1"
                    },
                     navArgument("verseid") {
                        type = androidx.navigation.NavType.StringType

                        nullable = true                  },
                    navArgument("wordno") { type = NavType.StringType

                        nullable = true}
                )
            ) { backStackEntry ->
              //  val id = backStackEntry.arguments!!.getString("chapterid")
                val id = backStackEntry.arguments!!.getInt("id")
                var newnewadapterlist = LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>()
                var corpusSurahWord: List<QuranCorpusWbw>? = null

                val utils = Utils(QuranGrammarApplication.context)
                val corpus = CorpusUtilityorig

                val quranbySurah = viewModel.getquranbySUrah(id).value
                val surahs = viewModel.getAllChapters().value
                corpusSurahWord = viewModel.getQuranCorpusWbwbysurah(id).value
                newnewadapterlist = corpus.composeWBWCollection(quranbySurah, corpusSurahWord)
                viewModel.setspans(newnewadapterlist, id)


            //    NewQuranVerseScreen(navController, id, viewModel,quranbySurah,surahs,corpusSurahWord,newnewadapterlist)

            }

            composable( "books/{chapterid}/{verseid}/{wordno}",
                arguments = listOf(
                    navArgument("chapterid") {
                        type = NavType.StringType
                        defaultValue = "2"
                     },
                    navArgument("verseid") {
                        type = NavType.StringType
                        defaultValue ="3"
                           },
                    navArgument("wordno") { type = NavType.StringType
                        defaultValue ="4"
                      }
                )




            ) { backStackEntry ->
                val chapterid = backStackEntry.arguments?.getString("chapterid")
                val verseid = backStackEntry.arguments?.getString("verseid")
                val wordno = backStackEntry.arguments?.getString("wordno")

         //       BottomSheetWordDetails(navController,viewModel(),chapterid!!.toInt(),verseid!!.toInt(),wordno!!.toInt() )
               BottomSheetWordDetails(
                    navController,
                    viewModel(),
                    chapterid!!.toInt(),
                    verseid!!.toInt(),
                    wordno!!.toInt()
                )

            }

            composable(Screen.Profile.route) {
                ProfileScreen()
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
    var worddetails = am.wordDetails
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
                            val mood = vbdetail["mood"].toString()
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
                            val mood = vbdetail["mood"].toString()
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


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)


                 //   .background(androidx.compose.material3.MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp),

                )
        },
        sheetPeekHeight = 0.dp,

        ) {


        //Add button to open bottom sheet

    }
}

@Composable
private fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf(
        NavigationItem(0, R.drawable.ic_baseline_home_24, R.string.home_icon, Screen.Home),
        NavigationItem(
            1,
            R.drawable.ic_baseline_format_list_bulleted_24,
            R.string.subscriptions_icon,
            Screen.Profile
        ),
        NavigationItem(2, R.drawable.ic_baseline_add_24, R.string.post_icon, Screen.Words),
    )
    BottomNavigation(modifier = modifier) {
        items.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = it.vectorResourceId),
                        contentDescription = stringResource(id = it.contentDescriptionResourceId)
                    )
                },
                selected = selectedItem == it.index,
                onClick = {
                    selectedItem = it.index
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private data class NavigationItem(
    val index: Int,
    val vectorResourceId: Int,
    val contentDescriptionResourceId: Int,
    val screen: Screen
)