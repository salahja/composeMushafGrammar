package com.example.compose.activity


import CardsScreen
import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.compose.ui.text.AnnotatedString
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
import com.adaptive.AdaptiveMainActivity
import com.example.bottomcompose.BottomSheetDemo
import com.example.compose.CardsViewModel
import com.example.compose.ConjugationScreen
import com.example.compose.NavigationItem
import com.example.compose.NewQuranMorphologyDetails
import com.example.compose.RootModel
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
import com.example.mushafconsolidated.settingsimport.Constants.Companion.SURAH_INDEX
import com.example.tabcompose.TabItem
import com.example.utility.QuranGrammarApplication
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.skyyo.expandablelist.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import com.example.tabcompose.*
import com.example.utility.QuranGrammarApplication.Companion.context



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
fun MainScreen(viewModel: QuranVIewModel) {
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

                val myViewModel: VerseModel = viewModel(factory = newViewModelFactory(id))
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

class newViewModelFactory(private val dbname: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        VerseModel(dbname) as T
}

class CardViewModelFactory(
    private val dbname: String,
    private val nounroot: String,
    private val isharf: Boolean
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        CardsViewModel(dbname, nounroot, isharf) as T
}


class RootViewModelFactory(
    private val dbname: String,


) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        RootModel(dbname) as T
}




class surahViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        QuranVIewModel() as T
}