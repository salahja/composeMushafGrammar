package com.activities

import AudioPlayer
import CardsScreen

import NavigationActions
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home

import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.appscreens.HomeScreen
import com.viewmodels.HomeViewModel
import com.appscreens.RootDetailScreen

import com.adaptive.theme.ComposeSettingsTheme
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSettingState
import com.android.audiomushaf.ShowMushafActivity
import com.appscreens.BottomSheetWordDetails
import com.appscreens.MatTab

import com.viewmodels.CardsViewModel
import com.viewmodels.RootModel
import com.appscreens.SurahListScreen
import com.viewmodels.VerseModel




import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.appscreens.NewQuranVerseScreen
import com.appscreens.NounRootScreens
import com.appscreens.RootScreens
import com.appscreens.newVerseAnalysisCardsScreen
import com.appscreensn.LineQuranPageScreen
import com.appscreensn.LineQuranPageScreens
import com.downloadmanager.DownloaderViewModel
import com.example.myapplication.MainLayout
import com.example.utility.QuranGrammarApplication.Companion.context
import com.modelfactory.AudioDownloadFactory
import com.modelfactory.CardViewModelFactory
import com.modelfactory.QuranVMFactory
import com.modelfactory.RootViewModelFactory
import com.modelfactory.VerseAnalysisFctory
import com.modelfactory.newViewModelFactory
import com.modelfactory.surahViewModelFactory
import com.settings.AppSettingsScreen
import com.settings.preference.SeetingScreen
import com.viewmodels.ExpandableVerseViewModel
import com.viewmodels.QuranPagesModel


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
                 //   color = MaterialTheme.colorScheme.background
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

@OptIn(ExperimentalAnimationApi::class)
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
@ExperimentalAnimationApi
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  MainContent(
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
                        RootDetailScreen(
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

      /*              val intent = Intent(Intent.ACTION_VIEW)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val i = Intent(context, DownloadActThree::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context!!.startActivity(i)
*/
             SeetingScreen(navController,       darkThemePreference  ,                 dynamicThemePreference  )



                }

                composable(Screen.TopSettings.route) {
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

                    val thememode = rememberPreferenceBooleanSettingState(key = "Dark", defaultValue = false)
                    val myViewModel: VerseModel = viewModel(factory = newViewModelFactory(id,darkThemePreference))

                  NewQuranVerseScreen(navController, id, myViewModel,darkThemePreference)
               //     val myViewModel: QuranPagesModel = viewModel(factory = QuranVMFactory(id,darkThemePreference))
               //     QuranPageScreen(navController, id, myViewModel,darkThemePreference)
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
                    val selectTranslation = rememberPreferenceIntSettingState(key = "selecttranslation")
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
                            viewModel(factory = CardViewModelFactory(verbroot, nounroot, true,selectTranslation))

                        nounroot = root

                        CardsScreen(myViewModel)
                    } else {
                        val myViewModel: CardsViewModel =
                            viewModel(factory = CardViewModelFactory(verbroot, nounroot, false,selectTranslation))
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

                    "nounroot/{root}",
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
                        root = root!!.replace("ء", "ا")
                    }
                    val rootmodel: RootModel = viewModel(factory = RootViewModelFactory(root!!))
                    NounRootScreens(
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
                   // MainLayout()
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

                }

                composable(Screen.Audio.route) {

                                  val intent = Intent(Intent.ACTION_VIEW)
                                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                  val i = Intent(context, ShowMushafActivity::class.java)
                                  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                  context!!.startActivity(i)





                }



                composable("versealert/{chapterid}/{verseid}",
                    arguments = listOf(
                        navArgument("chapterid") {
                            type = NavType.IntType
                            defaultValue = 2
                        },
                        navArgument("verseid") {
                            type = NavType.IntType
                            defaultValue = 3
                        },

                    )


                ) { backStackEntry ->
                    val chapterid = backStackEntry.arguments?.getInt("chapterid")
                    val verseid = backStackEntry.arguments?.getInt("verseid")

                    val openDialogCustom: MutableState<Boolean> = remember {
                        mutableStateOf(true)
                    }
                    val thememode = darkThemePreference.value
                    val wbwchoice=                      rememberPreferenceIntSettingState(key = "wbwtranslation", defaultValue = 0)
                    //     CustomDialog(openDialogCustom,navController, viewModel, chapterid, verseid, wordno)
                    val versemodel: ExpandableVerseViewModel = viewModel(factory = VerseAnalysisFctory(chapterid!!,verseid!!,thememode,wbwchoice.value))
                 //   VerseAnalysisScreen(versemodel,navController,  chapterid, verseid,)
                    newVerseAnalysisCardsScreen(versemodel,navController,  chapterid, verseid,)

                }

            }
        }
    }

}

@Composable
fun NavRail(
    currentDestination: NavDestination?,
    navController: NavHostController,
    navigateToTopLevelDestination: (Screen) -> Unit
) {
    NavigationRail(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
 //   NavigationRail(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
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
         //   drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
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
                        if (screen.route == Screen.Home.route) Icons.Filled.Home else Icons.Filled.Favorite,contentDescription = null

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
    Screen.Settings,
    Screen.Audio
)

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Profile : Screen("Verb Root", R.string.Verb)
    object Roots : Screen("roots", R.string.Verb)
    object Settings : Screen("setting", R.string.Setting)
    object TopSettings : Screen("topsetting", R.string.Topsetting)
    object Mushaf : Screen("mushaf", R.string.Mushaf)
    object Audio : Screen("audio", R.string.audio)

}
