package com.example.compose.activity

import android.content.res.Configuration
import android.content.res.TypedArray
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.ConjugationScreen
import com.example.compose.NavigationItem
import com.example.compose.NewQuranMorphologyDetails
import com.example.compose.SurahListScreen
import com.example.compose.TextChip
import com.example.compose.VerseModel
import com.example.compose.theme.WordALert
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.mushafconsolidated.settingsimport.Constants
import com.example.tabcompose.TabItem
import com.example.utility.QuranGrammarApplication
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.skyyo.expandablelist.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import com.example.tabcompose.*
import com.example.utility.BackButtonHandler
import com.example.utility.QuranGrammarApplication.Companion.context

class TwoPane : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(color = androidx.compose.material.MaterialTheme.colors.surface) {
                    @Suppress("MagicNumber")
                    ListDetailLayout(
                        (1..10).map { index -> "Item $index" },
                        LocalConfiguration.current
                    ) {
                        List { list, onSelectionChange ->
                            MyList(list, onSelectionChange)
                        }
                        Detail { text ->
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
                            ) {
                                Text(text = text)
                            }
                        }
                    }
                }
            }
        }
    }
}

private object NavGraph {
    sealed class Route(val route: String) {
        object Detail : Route("detail/{selected}") {
            fun navigateRoute(selected: String?) = "detail/$selected"
        }
    }
}

@Composable
@Suppress("MagicNumber")
fun ListDetailLayout(
    list: List<String>,
    configuration: Configuration,
    scope: @Composable TwoPaneScope<String>.() -> Unit
) {
    val isSmallScreen = configuration.smallestScreenWidthDp < 580
    val navController = rememberNavController()
    val twoPaneScope = TwoPaneScopeImpl(list).apply { scope() }

    NavHost(navController = navController, startDestination = NavGraph.Route.Detail.route) {
        composable(NavGraph.Route.Detail.route) { navBackStackEntry ->
            val selected = navBackStackEntry.arguments?.getString("selected")
            if (isSmallScreen) {
                TwoPageLayout(twoPaneScope, selected) { selection ->
                    navController.navigate(route = NavGraph.Route.Detail.navigateRoute(selection)) {
                        popUpTo(NavGraph.Route.Detail.navigateRoute(null)) {
                            inclusive = true
                        }
                    }
                }
                BackHandler(true) {
                    navController.popBackStack()
                }
            } else {
                SplitLayout(twoPaneScope, selected) { selection ->
                    navController.navigate(route = NavGraph.Route.Detail.navigateRoute(selection)) {
                        popUpTo(NavGraph.Route.Detail.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TwoPageLayout(
    twoPaneScope: TwoPaneScopeImpl<String>,
    selected: String?,
    onSelectionChange: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (selected == null) {
            twoPaneScope.list(twoPaneScope.items, onSelectionChange)
        } else {
            twoPaneScope.detail(selected)
        }
    }
}

@Composable

private fun SplitLayout(
    twoPaneScope: TwoPaneScopeImpl<String>,
    selected: String?,
    onSelectionChange: (String) -> Unit
) {
    Row(Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(0.3f)) {
            twoPaneScope.list(twoPaneScope.items, onSelectionChange)
        }
        Box(modifier = Modifier.weight(1f)) {
            twoPaneScope.detail(selected ?: "Nothing selected")
        }
    }
}

@Composable
private fun MyList(
    list: List<String>,
    onSelectionChange: (String) -> Unit
) {
    LazyColumn {
        for (entry in list) {
            item {
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
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onSelectionChange(entry) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = entry)
                    }
                }
            }
        }
    }
}

@Immutable
interface TwoPaneScope<T> {
    val list: @Composable (List<T>, (T) -> Unit) -> Unit
    val detail: @Composable (T) -> Unit

    @Composable
    fun List(newList: @Composable (List<T>, (T) -> Unit) -> Unit)

    @Composable
    fun Detail(newDetail: @Composable (T) -> Unit)
}

private class TwoPaneScopeImpl<T>(
    val items: List<T>
) : TwoPaneScope<T> {
    override var list: @Composable (List<T>, (T) -> Unit) -> Unit = { _, _ -> }
        private set

    override var detail: @Composable (T) -> Unit = {}
        private set

    @Composable
    override fun List(newList: @Composable (List<T>, (T) -> Unit) -> Unit) {
        list = newList
    }

    @Composable
    override fun Detail(newDetail: @Composable (T) -> Unit) {
        detail = newDetail
    }
}
@Composable
@Preview
private fun SplitLayouts(

) {
    Row(Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(0.2f)) {

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
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()

                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "entry")
                }
            }
        }
        //    twoPaneScope.list(twoPaneScope.items, onSelectionChange)

        Box(modifier = Modifier.weight(1f)) {

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
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                      //  .clickable { onSelectionChange(entry) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "entry")
                }
            }
        }

    }

}