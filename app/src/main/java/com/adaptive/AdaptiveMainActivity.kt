
package com.adaptive
import CardsScreen
import ComposeTutorial12DifferentScreenSizesSupportTheme
import NavigationActions
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.adaptive.theme.BottomSheetWordDetails
import com.example.bottomcompose.BottomSheetDemo
import com.example.compose.CardsViewModel
import com.example.compose.SurahListScreen
import com.example.compose.VerseModel

import com.example.compose.activity.CardViewModelFactory
import com.example.compose.activity.newViewModelFactory
import com.example.compose.activity.surahViewModelFactory
import com.example.compose.theme.NewQuranVerseScreen

import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.tabcompose.MatTab
import com.skyyo.expandablelist.theme.AppTheme

class AdaptiveMainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {

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
                        finishActivity
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
) {
    val navController = rememberNavController()

    val navigationType = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_NAV
        WindowWidthSizeClass.Medium -> NavigationType.NAV_RAIL
        WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAV_DRAWER
        else -> NavigationType.BOTTOM_NAV
    }

    AppNavHost(
        navController = navController,
        navigationType,
        selectedUserId,
        isOnlyDetailScreen,
        updateFullState,
        updateUserId
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
    navController: NavHostController,
    navigationType: NavigationType,
    selectedUserId: String?,
    isOnlyDetailScreen: Boolean,
    updateFullState: (String?, Boolean) -> Unit,
    updateUserId: (String?) -> Unit,
    popFromMainBackStack: () -> Unit
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
                navigationType, selectedUserId,
                isOnlyDetailScreen,
                updateFullState,
                updateUserId,
                popFromMainBackStack = popFromMainBackStack
            )
        }

    }
}

@Composable
fun TabsNavGraph(
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
    val firstItemId = 0 //set first item id from list when u will be getting list dynamically from viewmodel

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

                        val myViewModel: QuranVIewModel = viewModel(factory = surahViewModelFactory())
                        SurahListScreen(navController,myViewModel)
                    }

                    composable(
                        route = "detail",
                        //arguments = listOf(navArgument("userId") { type = NavType.IntType })
                    ) {
                        //val userId = it.arguments?.getInt("userId")
                        UserDetailScreen(
                            userId = selectedUserId,
                            isOnlyDetailScreen = isOnlyDetailScreen
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
                        onDetailBackPressed = closeUserDetail
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


                        val myViewModel: VerseModel    = viewModel(factory = newViewModelFactory(id))
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
                        val myViewModel: CardsViewModel = viewModel(factory = CardViewModelFactory(verbroot,nounroot,true))

                        nounroot=root

                        CardsScreen(myViewModel)
                    }else{
                        val myViewModel: CardsViewModel = viewModel(factory = CardViewModelFactory(verbroot,nounroot,false))
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
                    BottomSheetWordDetails(navController, viewModel(), chapterid, verseid, wordno)
                    //  WordALert(openDialogCustom, navController, quranmodel, chapterid, verseid, wordno)
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
)

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Profile : Screen("profile", R.string.Profile)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTutorial12DifferentScreenSizesSupportTheme {

    }
}