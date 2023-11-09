package com.activities


import CardsScreen
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alorma.compose.settings.storage.preferences.rememberPreferenceIntSettingState
import com.appscreens.MatTab

import com.viewmodels.CardsViewModel
import com.appscreens.ConjugationScreen
import com.example.compose.NavigationItem
import com.appscreens.SurahListScreen
import com.viewmodels.VerseModel
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.mushafconsolidated.settingsimport.Constants.Companion.SURAH_INDEX
import com.google.accompanist.pager.ExperimentalPagerApi
import com.skyyo.expandablelist.theme.AppTheme

import com.example.utility.QuranGrammarApplication.Companion.context
import com.modelfactory.CardViewModelFactory
import com.modelfactory.newViewModelFactory


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







@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(viewModel: QuranVIewModel) {
    val navController = rememberNavController()
  //  val scaffoldState: ScaffoldState = rememberScaffoldState()
 //   val scope: CoroutineScope = rememberCoroutineScope()
 //   val tabs = listOf(TabItem.Music, TabItem.Movies, TabItem.Books)
  //  val pagerState = rememberPagerState()
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        // topBar = { TopBar() },
        bottomBar = { BottomNavigationBars(navController) },

        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController, viewModel)
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

        composable("Wordoccurance") {


            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val i = Intent(context, AdaptiveMainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra(SURAH_INDEX, id)

            context!!.startActivity(i)
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

         //       val myViewModel: VerseModel = viewModel(factory = newViewModelFactory(id, true))
                //   NewQuranVerseScreen(navController, id, quranmodel,myViewModel)

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
            val selectTranslation = rememberPreferenceIntSettingState(key = "selecttranslation")
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
                    viewModel(factory = CardViewModelFactory(
                        verbroot,
                        nounroot,
                        true,
                        selectTranslation
                    ))

                nounroot = root

                CardsScreen(myViewModel)
            } else {
                val myViewModel: CardsViewModel =
                    viewModel(factory = CardViewModelFactory(
                        verbroot,
                        nounroot,
                        false,
                        selectTranslation
                    ))
                nounroot = root
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


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Composable

fun BottomNavigationBars(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Wordoccurance,
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

