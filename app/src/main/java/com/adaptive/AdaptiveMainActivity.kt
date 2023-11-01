package com.adaptive

import CardsScreen

import NavigationActions
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.LocalTextStyle


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.adaptive.theme.BottomSheetWordDetails
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.example.bottomcompose.BottomSheetDemo
import com.example.compose.CardsViewModel
import com.example.compose.NewQuranMorphologyDetails
import com.example.compose.NounMorphologyDetails
import com.example.compose.QuranMorphologyDetails
import com.example.compose.RootModel
import com.example.compose.RootWordLoading
import com.example.compose.SurahListScreen
import com.example.compose.VerseModel
import com.example.compose.WordOccuranceLoading

import com.example.compose.activity.CardViewModelFactory
import com.example.compose.activity.RootViewModelFactory
import com.example.compose.activity.newViewModelFactory
import com.example.compose.activity.surahViewModelFactory
import com.example.compose.theme.NewQuranVerseScreen
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.RootVerbDetails

import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.tabcompose.MatTab
import com.example.utility.AnnotationUtility.Companion.AnnotatedSetWordSpanTag
import com.example.utility.QuranGrammarApplication
import com.settings.AppSettingsScreen
import com.settings.preference.SeetingScreen
import com.skyyo.expandablelist.theme.AppTheme
import com.skyyo.expandablelist.theme.AppThemeSettings

val showbootomsheet = mutableStateOf(false)
class AdaptiveMainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val darkThemePreference = rememberPreferenceBooleanSettingState(
                key = "darkThemePreference",
                defaultValue = true,
            )

            val dynamicThemePreference = rememberPreferenceBooleanSettingState(
                key = "dynamicThemePreference",
                defaultValue = true,
            )

            ComposeSettingsTheme (
              darkThemePreference = darkThemePreference.value,
               dynamicThemePreference = dynamicThemePreference.value,
            ) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val windowSizeClass = calculateWindowSizeClass(activity = this)
                    val homeViewModel = viewModel<HomeViewModel>()

                    val uiState = homeViewModel.uiState.collectAsState()

                    val updateFullState: (String?, Boolean) -> Unit = { userId, boolean ->
                        homeViewModel.updateFullState(userIdVal = userId, isOnlyScreen = boolean)
                    }

                    val updateUserId: (String?) -> Unit = {
                        homeViewModel.updateUserId(it)
                    }

                    val finishActivity = {
                        this.finish()
                    }

                    AppContent(
                        windowSizeClass,
                        selectedUserId = uiState.value.userId,
                        isOnlyDetailScreen = uiState.value.isOnlyDetailScreen,
                        updateFullState,
                        updateUserId,
                        finishActivity,    darkThemePreference,       dynamicThemePreference
                    )
                }
            }
        }
    }


    @Composable
    fun ComposeSettingsTheme(
        darkThemePreference: Boolean,
        dynamicThemePreference: Boolean,
        content: @Composable () -> Unit,
    ) {
        MaterialTheme(
            colorScheme = if (Build.VERSION.SDK_INT >= 31 && dynamicThemePreference) {
                if (darkThemePreference) {
                    dynamicDarkColorScheme(LocalContext.current)
                } else {
                    dynamicLightColorScheme(LocalContext.current)
                }
            } else {
                if (darkThemePreference) {
                    darkColorScheme()
                } else {
                    lightColorScheme()
                }
            },
            content = content,
        )
    }

}

@Composable
fun AppContent(
    windowSizeClass: WindowSizeClass,
    selectedUserId: String?,
    isOnlyDetailScreen: Boolean,
    updateFullState: (String?, Boolean) -> Unit,
    updateUserId: (String?) -> Unit,
    finishActivity: () -> Unit,
    darkThemePreference: BooleanPreferenceSettingValueState,
    dynamicThemePreference: BooleanPreferenceSettingValueState,
) {
    val navController = rememberNavController()

    val navigationType = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_NAV
        WindowWidthSizeClass.Medium -> NavigationType.NAV_RAIL
        WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAV_DRAWER
        else -> NavigationType.BOTTOM_NAV
    }

    AppNavHost(
        darkThemePreference,
        dynamicThemePreference,
        navController = navController,
        navigationType,
        selectedUserId,
        isOnlyDetailScreen,
        updateFullState,
        updateUserId,

    ) {
        //minor change after tutorial
        navController.popBackStack()
        finishActivity()
    }
}

enum class NavigationType {
    BOTTOM_NAV, NAV_RAIL, PERMANENT_NAV_DRAWER
}

@Composable
fun AppNavHost(
    darkThemePreference: BooleanPreferenceSettingValueState,
    dynamicThemePreference: BooleanPreferenceSettingValueState,
    navController: NavHostController,
    navigationType: NavigationType,
    selectedUserId: String?,
    isOnlyDetailScreen: Boolean,
    updateFullState: (String?, Boolean) -> Unit,
    updateUserId: (String?) -> Unit,
    popFromMainBackStack: () -> Unit,

) {

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            navController.navigate("tabs") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }

        composable("tabs") {
            TabsNavGraph(
                darkThemePreference,
                dynamicThemePreference,
                navigationType, selectedUserId,
                isOnlyDetailScreen,
                updateFullState,
                updateUserId,
                popFromMainBackStack = popFromMainBackStack,
            )
        }

    }
}

@Composable
fun TabsNavGraph(
    darkThemePreference: BooleanPreferenceSettingValueState,
    dynamicThemePreference: BooleanPreferenceSettingValueState,
    navigationType: NavigationType,
    selectedUserId: String?,
    isOnlyDetailScreen: Boolean,
    updateFullState: (String?, Boolean) -> Unit,
    updateUserId: (String?) -> Unit,
    popFromMainBackStack: () -> Unit
) {
    // Each NavHost must be associated with separate NavHostController, so use new NavController here for bottom tabs
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    //common tabs, rail or drawer navigation logic added after tutorial
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }

    // for desktop device
    val firstItemId =
        0 //set first item id from list when u will be getting list dynamically from viewmodel

    val goToUserDetail: (String?) -> Unit = { userId ->
        if (navigationType == NavigationType.BOTTOM_NAV) {
            navController.navigate("detail")
            updateUserId(userId)
        } else if (navigationType == NavigationType.NAV_RAIL) {
            updateFullState(userId, true)
        } else {
            updateFullState(userId, false)
        }
    }

    val closeUserDetail: () -> Unit = {
        if (navigationType == NavigationType.BOTTOM_NAV) {
            navController.popBackStack()
            popFromMainBackStack()
        } else if (navigationType == NavigationType.NAV_RAIL) {
            //minor change after tutorial
            if (isOnlyDetailScreen) {
                updateFullState(null, false)
            } else {
                popFromMainBackStack() //clear from main backstack
            }
        } else {
            //minor change after tutorial
            updateFullState(firstItemId.toString(), false)
            popFromMainBackStack() //clear from main backstack
        }
    }
    if (navigationType == NavigationType.PERMANENT_NAV_DRAWER) {

        LaunchedEffect(Unit) {
            updateFullState(firstItemId.toString(), false) //set first time state
        }
        PermanentNavDrawer(
            currentDestination = currentDestination,
            navController = navController,
            navigateToTopLevelDestination = navigationActions::navigateTo
        ) {
            MainContent(
                darkThemePreference,
                dynamicThemePreference,

                navController = navController,
                currentDestination = currentDestination,
                navigationType = navigationType,
                selectedUserId,
                isOnlyDetailScreen,
                updateFullState,
                updateUserId,
                goToUserDetail = goToUserDetail,
                closeUserDetail = closeUserDetail,
                navigationActions = navigationActions
            )
        }

    } else {
        MainContent(
            darkThemePreference,
            dynamicThemePreference,

            navController = navController,
            currentDestination = currentDestination,
            navigationType = navigationType,
            selectedUserId,
            isOnlyDetailScreen,
            updateFullState,
            updateUserId,
            goToUserDetail = goToUserDetail,
            closeUserDetail = closeUserDetail,
            navigationActions = navigationActions
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    darkThemePreference: BooleanPreferenceSettingValueState,
    dynamicThemePreference: BooleanPreferenceSettingValueState,
    navController: NavHostController,
    currentDestination: NavDestination?,
    navigationType: NavigationType,
    selectedUserId: String?,
    isOnlyDetailScreen: Boolean,
    updateFullState: (String?, Boolean) -> Unit,
    updateUserId: (String?) -> Unit,
    goToUserDetail: (String?) -> Unit,
    closeUserDetail: () -> Unit,
    navigationActions: NavigationActions
) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAV) {
                BottomNavBar(
                    currentDestination,
                    navController,
                    navigateToTopLevelDestination = navigationActions::navigateTo
                )
            }
        }
    ) { innerPadding ->

        Row(modifier = Modifier.fillMaxSize()) {

            AnimatedVisibility(visible = navigationType == NavigationType.NAV_RAIL) {
                NavRail(
                    currentDestination = currentDestination,
                    navController = navController,
                    navigateToTopLevelDestination = navigationActions::navigateTo
                )
            }

            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                Modifier.padding(innerPadding)
            ) {
                navigation(startDestination = "home_main", route = Screen.Home.route) {
                    composable("home_main") {

                        val myViewModel: QuranVIewModel =
                            viewModel(factory = surahViewModelFactory())
                        SurahListScreen(navController, myViewModel)
                    }

                    composable(
                        route = "detail",
                        //arguments = listOf(navArgument("userId") { type = NavType.IntType })
                    ) {
                        //val userId = it.arguments?.getInt("userId")
                        UserDetailScreen(
                            userId = selectedUserId,
                            isOnlyDetailScreen = isOnlyDetailScreen,
                            navController
                        ) {
                            navController.popBackStack()
                        }
                    }
                }
                composable(Screen.Profile.route) {

                   HomeScreen(
                        userId = selectedUserId,
                        isOnlyDetailScreen = isOnlyDetailScreen,
                        navigationType = navigationType,
                        goToUserDetail = goToUserDetail,
                        onDetailBackPressed = closeUserDetail,
                        navController)












                }

                composable(Screen.Settings.route) {

                    /*       val intent = Intent(Intent.ACTION_VIEW)
                          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                          val i = Intent(context, SettingAct::class.java)
                          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          context!!.startActivity(i)
*/                    SeetingScreen(navController)

                }

                composable(Screen.TopSettings.route) {


                    /*       val intent = Intent(Intent.ACTION_VIEW)
                          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                          val i = Intent(context, SettingAct::class.java)
                          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          context!!.startActivity(i)
*/
                    AppSettingsScreen(
                        navController = navController,
                        darkThemePreference = darkThemePreference,
                        dynamicThemePreference = dynamicThemePreference,
                    )





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


                    val myViewModel: VerseModel = viewModel(factory = newViewModelFactory(id))
                    NewQuranVerseScreen(navController, id, myViewModel)


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
                        val myViewModel: CardsViewModel =
                            viewModel(factory = CardViewModelFactory(verbroot, nounroot, true))

                        nounroot = root

                        CardsScreen(myViewModel)
                    } else {
                        val myViewModel: CardsViewModel =
                            viewModel(factory = CardViewModelFactory(verbroot, nounroot, false))
                        nounroot = root
                        CardsScreen(myViewModel)

                    }

                }



                composable(

                    "roots/{root}",
                    arguments = listOf(

                        navArgument("root") {
                            type = NavType.StringType
                            defaultValue = "نصر"
                        },

                        )


                ) { backStackEntry ->

                    var root = backStackEntry.arguments?.getString("root")


                    val indexOf = root!!.indexOf("ء")
                    if (indexOf != -1) {
                        root = root.replace("ء", "ا")
                    }
                    val rootmodel: RootModel = viewModel(factory = RootViewModelFactory(root))




                    RootScreens(
                        rootmodel, isOnlyDetailScreen = isOnlyDetailScreen,
                        navigationType = navigationType,
                        goToUserDetail = goToUserDetail,
                        onDetailBackPressed = closeUserDetail,
                        navController
                    )


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
                    BottomSheetWordDetails(navController, viewModel(), chapterid, verseid, wordno)
                    //  WordALert(openDialogCustom, navController, quranmodel, chapterid, verseid, wordno)
                }


            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootScreens(
    rootmodel: RootModel,
    isOnlyDetailScreen: Boolean,
    navigationType: NavigationType,
    goToUserDetail: (String?) -> Unit,
    onDetailBackPressed: () -> Unit,
    navController: NavHostController
) {
    val util = Utils(QuranGrammarApplication.context!!)
    var loading = rootmodel.loading.value

    val roots by rootmodel.verbroot.collectAsStateWithLifecycle()
   // val collectAsStateWithLifecycle = rootmodel.verbroot.collectAsStateWithLifecycle()
   // val collectAsState = rootmodel.verbroot.collectAsState()

    val verbroots = roots[0].verbrootlist
    val nounroots = roots[0].corpusSurahWordlist
    val chapters = roots[0].chapterlist
    val nouns = roots[0].nounlist
        // loading = rootmodel.loading.value
    rootmodel.open.value = true
    /*   val nm=NounMorphologyDetails(nounroots ,nouns)
       var wordbdetail = HashMap<String, AnnotatedString>()
       wordbdetail=nm.wordDetails

       if(verbroots.isEmpty()){
           wordbdetail=nm.wordDetails

       }*/

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
                                onDetailBackPressed()
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
        //  if (verbroots.isNotEmpty())
        loading = rootmodel.loading.value
        RootWordLoading(isDisplayed = loading)
        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),


            ) {

            if (verbroots.isNotEmpty()) {
                items(verbroots.size) { index ->
                    //          indexval=index
                    RootGrid(verbroots[index], navController)
                }
            } else {
                items(nounroots.size) { index ->
                    //          indexval=index
                    NounGrid(nounroots[index], navController, chapters, nouns[index])
                }

            }
        }
    }
}

@Composable
fun NounGrid(
    quranCorpusWbw: QuranCorpusWbw,
    navController: NavHostController,
    chapters: List<ChaptersAnaEntity?>,
    nounCorpus: NounCorpus?,

    ) {
    val sb = StringBuilder()
    val spannableString = AnnotatedSetWordSpanTag(
        quranCorpusWbw.corpus.tagone!!,
        quranCorpusWbw.corpus.tagtwo!!,
        quranCorpusWbw.corpus.tagthree!!,
        quranCorpusWbw.corpus.tagfour!!,
        quranCorpusWbw.corpus.tagfive!!,
        quranCorpusWbw.corpus.araone!!,
        quranCorpusWbw.corpus.aratwo!!,
        quranCorpusWbw.corpus.arathree!!,
        quranCorpusWbw.corpus.arafour!!,
        quranCorpusWbw.corpus.arafive!!
    )
    val nm = NounMorphologyDetails(quranCorpusWbw, nounCorpus)
    var wordbdetail = HashMap<String, AnnotatedString>()
    wordbdetail = nm.wordDetails
    val arabicword: AnnotatedString = wordbdetail["arabicword"]!!
    //  sb.append(lughat.getSurah()).append("   ").append(lughat.getNamearabic()).append(lughat.getAyah()).append(" ").append(lughat.getArabic());
    sb.append(quranCorpusWbw.corpus.ayah).append("  ")
        .append(chapters.get(quranCorpusWbw.corpus.surah)!!.namearabic).append("   ")
        .append(quranCorpusWbw.corpus.surah).append(" ").append(quranCorpusWbw.wbw.en)

    Card(

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),


        modifier = Modifier
            .fillMaxWidth()

            .padding(
                horizontal = 5.dp,
                vertical = 5.dp
            )

    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            ClickableText(
                text = arabicword,

                onClick = {


                })
            Text(
                text = wordbdetail["noun"].toString(),
            )


        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            ClickableText(
                text = AnnotatedString(chapters.get(quranCorpusWbw.corpus.surah)!!.namearabic.toString()),

                onClick = {


                })
            Text(
                text = "Ayah  " + quranCorpusWbw.corpus.ayah.toString(),
            )

            // indexval = surahModelList!!.chapterid
            ClickableText(
                text = spannableString,

                onClick = {


                })
            ClickableText(
                text = AnnotatedString(quranCorpusWbw.wbw.en.toString()),

                onClick = {


                })


        }


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RootGrid(rootdetails: RootVerbDetails, navController: NavHostController) {
    val darkThemeEnabled = AppThemeSettings.isDarkThemeEnabled
    var wazan = ""
    if (rootdetails.form == "I") {
        if (rootdetails.thulathibab!!.length > 1) {
            val s = rootdetails.thulathibab!!.substring(0, 1)
            wazan = QuranMorphologyDetails.getThulathiName(s).toString()
        } else {
            wazan = QuranMorphologyDetails.getThulathiName(rootdetails.thulathibab).toString()
        }


        //   QuranMorphologyDetails.getThulathiName(rootdetails.getThulathibab());
    } else {
        wazan = QuranMorphologyDetails.getFormName(rootdetails.form)
    }

    var surahinfo = StringBuilder()
    surahinfo.append(rootdetails.surah).append(":").append(rootdetails.ayah).append(":")
        .append(rootdetails.wordno)

    val gender =
        QuranMorphologyDetails.getGenderNumberdetails(rootdetails.gendernumber)
    val tense = StringBuilder()
    tense.append(rootdetails.tense).append(":").append(rootdetails.voice).append(":")
        .append(rootdetails.mood_kananumbers)

    val start = rootdetails.qurantext!!.indexOf(rootdetails.arabic!!)
    val builder = AnnotatedString.Builder()
    builder.append(rootdetails.qurantext)
    val tagonestyle = SpanStyle(

        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
    )
    if (start != -1) {
        builder.addStyle(tagonestyle, start, start + rootdetails.arabic!!.length)
    }

    val form1 = rootdetails.form!!
    var sform = ""
    if (rootdetails.form!!.isNotEmpty() && !form1.equals("I")) {
        sform = convertFormss(form1)
    } else {
        var thulathi = rootdetails.thulathibab
        extracted(rootdetails, thulathi)
        sform = thulathi.toString()
    }



    Card(

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),


        modifier = Modifier
            .fillMaxWidth()

            .padding(
                horizontal = 5.dp,
                vertical = 5.dp
            )

    )
    {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            var sa = StringBuilder()
            sa.append(rootdetails.surah).append(":").append(rootdetails.ayah).append(":")
                .append(rootdetails.wordno)


            // indexval = surahModelList!!.chapterid
            ClickableText(
                text = AnnotatedString(rootdetails.abjadname!!),

                onClick = {


                })
            ClickableText(
                text = AnnotatedString(sa.toString()),

                onClick = {


                })
            ClickableText(
                text = AnnotatedString(rootdetails.arabic.toString()),

                onClick = {


                })
            ClickableText(
                text = AnnotatedString(wazan.toString()),

                onClick = {


                })


        }







        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            // indexval = surahModelList!!.chapterid
            ClickableText(
                text = AnnotatedString(gender.toString()),

                onClick = {


                })
            ClickableText(
                text = AnnotatedString(tense.toString()),

                onClick = {


                })
            ClickableText(
                text = AnnotatedString(rootdetails.en.toString()),

                onClick = {


                })


        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            ExpandableText(
                text = builder.toAnnotatedString(),



            )
            Spacer(modifier = Modifier.width(12.dp))


        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            ExpandableText(
                text = AnnotatedString( "Translation :"+         rootdetails.translation!!,)




            )
            Spacer(modifier = Modifier.width(20.dp))


        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            ExpandableText(
                text = AnnotatedString("Tafsir Ibne Kathir:"   +   rootdetails.tafsir_kathir!!,)




            )
            Spacer(modifier = Modifier.width(20.dp))


        }



        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val root = rootdetails.rootarabic
            val form = rootdetails.form

            val conjugation = rootdetails.thulathibab

            if (sform.length > 1) {
                sform = sform.substring(0, 1)
            }
            val mood = "Indicative"
            IconButton(

                onClick = {
                    showbootomsheet.value=false

                   navController.navigate(
                        "conjugator/${sform}/${root}/${mood}"
                    )
                }


            ) {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_construction_24),
                        contentDescription = "Expandable Arrow",
                      //  modifier = Modifier.rotate(20.0f),

                        )
                    Text("Conjugate")
                }
            }

            IconButton(
                {
                    val root = rootdetails.rootarabic
                    val form = rootdetails.form

                    val conjugation = rootdetails.thulathibab

                    if (sform.length > 1) {
                        sform = sform.substring(0, 1)
                    }
                    val mood = "Indicative"
                    navController.navigate(
                        "conjugator/${sform}/${root}/${mood}"
                    )
                },

                )
            {
                Icon(
                    painter = painterResource(id = R.drawable.tafsir),
                    contentDescription = "Expandable Arrow",
                    modifier = Modifier.rotate(20.0f),

                    )

            }

            if(showbootomsheet.value){
                //BottomDialog()
            }


        }


    }
}


const val DEFAULT_MINIMUM_TEXT_LINE = 1

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: AnnotatedString,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign
        )
    }

}









@Composable
private fun extracted(
    rootdetails: RootVerbDetails,
    thulathi: String?
) {
    var thulathi1 = thulathi
    if (rootdetails.thulathibab!!.isNotEmpty()) {
        when (thulathi1!!.length) {
            0 -> thulathi1 =
                null

            1 -> {
                thulathi1 = rootdetails.thulathibab
                val sb = NewQuranMorphologyDetails.getThulathiName(thulathi1)

            }

            else -> {
                thulathi1.length
                val s = thulathi1.substring(0, 1)
                val sb = NewQuranMorphologyDetails.getThulathiName(thulathi1)
                thulathi1 = s
            }
        }
    }
}

fun convertFormss(form1: String): String {
    var form = form1
    when (form) {
        "IV" -> form = 1.toString()
        "II" -> form = 2.toString()
        "III" -> form = 3.toString()
        "VII" -> form = 4.toString()
        "VIII" -> form = 5.toString()
        "VI" -> form = 7.toString()
        "V" -> form = 8.toString()
        "X" -> form = 9.toString()
        else -> {

        }
    }


    return form
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootScreen(
    rootmodel: RootModel,
    isOnlyDetailScreen: Boolean,
    navController: NavHostController,
    onBackPressed: () -> Unit,

    ) {


}

@Composable
fun NavRail(
    currentDestination: NavDestination?,
    navController: NavHostController,
    navigateToTopLevelDestination: (Screen) -> Unit
) {
    NavigationRail(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
        items.forEach { screen ->
            NavigationRailItem(
                modifier = Modifier.padding(top = 50.dp),
                icon = {
                    Icon(
                        if (screen.route == Screen.Home.route) Icons.Filled.Home else Icons.Filled.Favorite,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.resourceId)) },
                // it will use hierarchy to check if we have nested navigation,
                // then for multiple routes it will check for all nested routes, suppose we are on nested route inside current route,
                // thats why it will check for all hierarchy to show selected for all nested routes as well
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navigateToTopLevelDestination(screen)
                }
            )
        }
    }
}


@Composable
fun PermanentNavDrawer(
    currentDestination: NavDestination?,
    navController: NavHostController,
    navigateToTopLevelDestination: (Screen) -> Unit,
    content: @Composable () -> Unit
) {
    PermanentNavigationDrawer(drawerContent = {
        PermanentDrawerSheet(
            modifier = Modifier.sizeIn(minWidth = 150.dp, maxWidth = 230.dp),
            drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
        ) {
            items.forEach { screen ->
                NavigationDrawerItem(
                    modifier = Modifier.padding(top = 30.dp),

                    icon = {
                        Icon(
                            if (screen.route == Screen.Home.route) Icons.Filled.Home else Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(screen.resourceId)) },
                    // it will use hierarchy to check if we have nested navigation,
                    // then for multiple routes it will check for all nested routes, suppose we are on nested route inside current route,
                    // thats why it will check for all hierarchy to show selected for all nested routes as well
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navigateToTopLevelDestination(screen)
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent
                    )
                )
            }
        }
    }
    ) {
        content()
    }
}


@Composable
fun BottomNavBar(
    currentDestination: NavDestination?,
    navController: NavHostController,
    navigateToTopLevelDestination: (Screen) -> Unit
) {
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (screen.route == Screen.Home.route) Icons.Filled.Home else Icons.Filled.Favorite,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.resourceId)) },
                // it will use hierarchy to check if we have nested navigation,
                // then for multiple routes it will check for all nested routes, suppose we are on nested route inside current route,
                // thats why it will check for all hierarchy to show selected for all nested routes as well
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navigateToTopLevelDestination(screen)
                }
            )
        }
    }
}


val items = listOf(
    Screen.Home,
    Screen.Profile,
    Screen.Settings
)

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Profile : Screen("Verb Root", R.string.Verb)
    object Roots : Screen("roots", R.string.Verb)
    object Settings : Screen("setting", R.string.Setting)
    object TopSettings : Screen("topsetting", R.string.Topsetting)

}