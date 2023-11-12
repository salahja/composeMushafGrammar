package com.appscreens


import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.example.justJava.MyTextViewZoom
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.searchwidgetdemo.SearchViewModel
import com.example.utility.QuranGrammarApplication
import java.util.ArrayList
import java.util.Locale


var thememodes: BooleanPreferenceSettingValueState? = null
var pref: PreferencesManager? = null

var indexvals = 0
val imgss = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SurahListScreen(navController: NavHostController, quranModel: QuranVIewModel?) {
    pref = remember { PreferencesManager(QuranGrammarApplication.context!!) }

    thememodes = rememberPreferenceBooleanSettingState(key = "Dark", defaultValue = false)
    //   val chapters = quranModel!!.getAllSurah().value
    //   val listState = rememberLazyListState()
    val listState = rememberLazyGridState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction == 1f
        }
    }


    val viewModel = viewModel<SearchViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val chapters by viewModel.chapters.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchWidgetState by viewModel.searchWidgetState
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { LibraryTopBars(navController, scrollBehavior, isCollapsed) }








    ) {


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            TextField(value = searchText, onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search: Surah#/Surah English Name") }
            )
            Spacer(modifier = Modifier.height(5.dp))


            LazyVerticalGrid(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),

                columns = GridCells.Fixed(2),
                state = listState
            ) {
                items(chapters!!.size) { index ->
                    //          indexval=index


                    VerbRootGridList(chapters!![index], imgs, navController, thememodes!!)
                }
            }


            /* LazyColumn(
             modifier = Modifier
                 .fillMaxWidth()
                 .weight(1f)
         ) {



             items(chapters!!.size) { index ->
                 Text(
                     text = chapters!![index]!!.nameenglish,
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(vertical = 16.dp)
                 )
                 Text(
                     text = "${chapters!![index]!!.nameenglish} ${chapters!![index]!!.abjadname}",
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(vertical = 16.dp)
                 )
             }

         }*/
        }

    }







}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryTopBars(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    isCollapsed: Boolean
) =
    SmallTopAppBar(

        title = { androidx.compose.material3.Text(text = "SurahList") },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {

            FilledTonalButton(onClick = {
                val data = pref!!.getData("lastread", 1)
                navController.navigate("verses/" + data)
            }) {
                Text("Last Read")
            }



            FilledTonalButton(onClick = { }) {
                Text("Surah Kahaf")
            }








            IconButton(onClick = {

            }) {
                Icon(Icons.Filled.Search, contentDescription = null)
            }
        },

        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp),
            // scrolledContainerColor = MaterialTheme.colorScheme.background,
            titleContentColor = if (isCollapsed) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            },
        ),

        scrollBehavior = scrollBehavior,
    )

@OptIn(ExperimentalMaterial3Api::class)
private
@Composable

fun VerbRootGridList(
    surahModelList: ChaptersAnaEntity?,
    imgs: TypedArray,
    navController: NavHostController,
    thememode: BooleanPreferenceSettingValueState
) {

    val stated = rememberScrollState()
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) { stated.animateScrollTo(3) }

    val img = imgs.getDrawable(surahModelList!!.chapterid.toInt() - 1)


    val themestate = rememberPreferenceBooleanSettingState(key = "Dark", defaultValue = false)
    Card(
        /*
                 colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),*/
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            indexval = surahModelList!!.chapterid
            ClickableText(
                text = AnnotatedString(surahModelList!!.chapterid.toString()),

                onClick = {
                    Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                    //    navController.navigate(NavigationItem.Books.route)

                    navController.navigate("verses/" + surahModelList!!.chapterid)


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )


            )
            ClickableText(
                text = AnnotatedString(surahModelList!!.namearabic.toString()),

                onClick = {
                    Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                    //   navController.navigate(NavigationItem.Books.route)
                    navController.navigate("verses/" + surahModelList!!.chapterid.toString())

                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )

            ClickableText(
                text = AnnotatedString(surahModelList!!.nameenglish.toString()),

                onClick = {
                    Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                    // navController.navigate(NavigationItem.Books.route)
                    navController.navigate("verses/" + surahModelList!!.chapterid)

                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )

            /*       Text(
                       modifier = Modifier.clickable { println("Clicked") },
                       text = surahModelList!!.namearabic,
                       fontSize = 20.sp
                   )
                   Text(
                       modifier = Modifier.clickable { println("Clicked") },
                       text = surahModelList.nameenglish,
                       fontSize = 10.sp
                   )
       */

            if (themestate.value) {
                AsyncImage(

                    model = img,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Cyan),
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp),

                    )
            } else {
                AsyncImage(

                    model = img,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Red),
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp),

                    )
            }


        }
    }


}