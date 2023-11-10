package com.appscreens

import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.alorma.compose.settings.storage.preferences.rememberPreferenceBooleanSettingState
import com.example.justJava.MyTextViewZoom
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.utility.QuranGrammarApplication
import java.util.ArrayList
import java.util.Locale


var thememode: BooleanPreferenceSettingValueState? = null
var pref: PreferencesManager? = null

var indexval = 0
val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SurahListScreen(navController: NavHostController, quranModel: QuranVIewModel?) {
    pref = remember { PreferencesManager(QuranGrammarApplication.context!!) }


    thememode = rememberPreferenceBooleanSettingState(key = "Dark", defaultValue = false)
    val chapters = quranModel!!.getAllSurah().value
    //   val listState = rememberLazyListState()
    val listState = rememberLazyGridState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction == 1f
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { LibraryTopBar(navController, scrollBehavior, isCollapsed) }
    ) {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)
        SurahList(navController = navController, state = textState, chapters)
        /*    LazyVerticalGrid(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),

                columns = GridCells.Fixed(2),
                state = listState
            ) {
                items(allAnaChapters!!.size) { index ->
                    //          indexval=index


                    VerbRootGridList(allAnaChapters[index], imgs, navController,thememode)
                }
            }*/
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahList(
    navController: NavHostController,
    state: MutableState<TextFieldValue>,
    chapters: List<ChaptersAnaEntity>?
) {
    if (chapters != null) {
        chapters.forEach {
            it.nameenglish
        }
    }
    var filteredCountries: List<ChaptersAnaEntity> = listOf<ChaptersAnaEntity>()
    val stated = rememberScrollState()
    val scrollState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) { stated.animateScrollTo(3) }

    LazyVerticalGrid(
        /*        modifier = Modifier

                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 10.dp)
                    .verticalScroll(rememberScrollState()),*/
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),

        columns = GridCells.Fixed(2),
        state = scrollState
    )
    /*    LazyColumn(        modifier = Modifier
            .fillMaxWidth()

            .padding(

                vertical = 48.dp
            )

            )*/

    {
        val searchedText = state.value.text


        if (searchedText.isEmpty()) {
            filteredCountries = chapters as ArrayList<ChaptersAnaEntity>
        } else {
            val resultList = ArrayList<String>()
            if (chapters != null) {
                for (country in chapters) {
                    if (country.nameenglish.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(country.nameenglish)
                    }
                }
            }
            resultList
        }
        items(filteredCountries) { filteredCountry ->
            CountryListItem(
                surahModelList = filteredCountry,navController,
            ) { selectedCountry ->
                navController.navigate("details/$selectedCountry") {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo("main") {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        }
    }

}


@Composable
fun CountryListItem(
    surahModelList: ChaptersAnaEntity,
    navController: NavHostController,
    onItemClick: (String) -> Unit
) {

    val img = imgs.getDrawable(surahModelList!!.chapterid.toInt() - 1)
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

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,

            modifier = Modifier
                .clickable(onClick = { onItemClick(surahModelList.nameenglish) })
                .background(colorResource(id = R.color.colorPrimaryDark))
                .height(57.dp)
                .fillMaxWidth()
                .fillMaxSize()
                .padding(PaddingValues(8.dp, 16.dp))

        ) {
            // Text(text = surahModelList.nameenglish, fontSize = 18.sp, color = Color.White)


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

            if (thememode!!.value) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryTopBar(
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
    val img = imgs.getDrawable(surahModelList!!.chapterid.toInt() - 1)

    var filteredCountries: ArrayList<String>
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