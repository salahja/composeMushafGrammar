package com.appscreens

import Utility.PreferencesManager
import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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


var pref: PreferencesManager?=null

var indexval = 0

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SurahListScreen(navController: NavHostController, quranModel: QuranVIewModel?) {
    pref = remember { PreferencesManager(QuranGrammarApplication.context!!) }
    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)

    val thememode = rememberPreferenceBooleanSettingState(key = "Dark", defaultValue = false)
   val allAnaChapters = quranModel!!.getAllSurah().value
    //   val listState = rememberLazyListState()
    val listState =                 rememberLazyGridState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction == 1f
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { LibraryTopBar(navController,scrollBehavior, isCollapsed) }
    ) {
        LazyVerticalGrid(

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
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryTopBar(navController: NavHostController,scrollBehavior: TopAppBarScrollBehavior, isCollapsed: Boolean) =
    MediumTopAppBar(

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



            ElevatedButton(onClick = {  }) {
                    Text("Surah Kahaf")
                }








            IconButton(onClick = { /* Define your action here */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        },

      colors = TopAppBarDefaults.mediumTopAppBarColors(
           containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp),
           // scrolledContainerColor = MaterialTheme.colorScheme.background,
            titleContentColor = if (isCollapsed) {
                MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.onPrimary
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


                },style = TextStyle(
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

                },style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                ))

            ClickableText(
                text = AnnotatedString(surahModelList!!.nameenglish.toString()),

                onClick = {
                    Log.d(MyTextViewZoom.TAG, "mode=ZOOM")
                    // navController.navigate(NavigationItem.Books.route)
                    navController.navigate("verses/" + surahModelList!!.chapterid)

                },style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                ))

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