package com.example.tabcompose

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.codelab.basics.ui.theme.indopak
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Entities.VerbWazan
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication

import kotlinx.coroutines.launch
import org.sj.conjugator.utilities.GatherAll
import org.sj.verbConjugation.AmrNahiAmr
import org.sj.verbConjugation.FaelMafool
import org.sj.verbConjugation.IsmAlaMifaalun
import org.sj.verbConjugation.IsmAlaMifalatun
import org.sj.verbConjugation.IsmAlaMifalun
import org.sj.verbConjugation.IsmZarfMafalatun
import org.sj.verbConjugation.IsmZarfMafalun
import org.sj.verbConjugation.IsmZarfMafilun
import org.sj.verbConjugation.MadhiMudharay
import org.sj.verbConjugation.SarfSagheer

lateinit var tabItems: List<TabItems>
var isMujarrad: Boolean = false
lateinit var sarfSagheer: SarfSagheer
lateinit var mujarrad: ArrayList<ArrayList<*>>
var mazeed: ArrayList<ArrayList<*>>? = null
var amrandnahi: ArrayList<*>? = null
var faelmafool: ArrayList<*>? = null
var madhimudhary: ArrayList<*>? = null
lateinit var mifalun: IsmAlaMifalun
lateinit var mifalatun: IsmAlaMifalatun
lateinit var mifaalun: IsmAlaMifaalun
lateinit var zmafilun: IsmZarfMafilun
lateinit var zmafalatun: IsmZarfMafalatun
lateinit var zmafalun: IsmZarfMafalun

@OptIn(ExperimentalFoundationApi::class)
@Composable

fun MatTab(navController: NavHostController, conjugation: String, root: String, mood: String?) {
    var allofQuran: List<QuranEntity>? = null
    val vb = VerbWazan()
    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)

    val filter = conjugation.toString().filter(Char::isDigit)
    isMujarrad = isNumeric(conjugation)

    val verbindex = root!!.indexOf("ا")

 val   verbroot = if (verbindex != -1) {
        root!!.replace("ا", "ء")
    } else {
        root
    }
    /*
    5 = {ArrayList@27947}  size = 1
     0 = {IsmAlaMifalun@27992} org.sj.verbConjugation.IsmAlaMifalun@14684e4
    6 = {ArrayList@27948}  size = 1
     0 = {IsmAlaMifalatun@27995} org.sj.verbConjugation.IsmAlaMifalatun@3dc3d4d
    7 = {ArrayList@27949}  size = 1
     0 = {IsmAlaMifaalun@27998} org.sj.verbConjugation.IsmAlaMifaalun@601c002
    8 = {ArrayList@27950}  size = 1
     0 = {IsmZarfMafilun@28048} org.sj.verbConjugation.IsmZarfMafilun@6cfda4e
    9 = {ArrayList@27951}  size = 1
     0 = {IsmZarfMafalatun@28045} org.sj.verbConjugation.IsmZarfMafalatun@3c6149
    10 = {ArrayList@27952}  size = 1
     0 = {IsmZarfMafalun@28017} org.sj.verbConjugation.IsmZarfMafalun@d590b50

     lateinit var mifalun: IsmAlaMifaalun
    lateinit var mialatun : IsmAlaMifalatun
    lateinit var mifaalun: IsmAlaMifaalun
    lateinit var zmafilun: IsmZarfMafilun
    lateinit var zmafalatun : IsmZarfMafalatun
    lateinit var zmafalun: IsmZarfMafalun
     */
    vb.wazan = conjugation
    if (isMujarrad) {
        mazeed = GatherAll.instance.getMazeedListing(mood, verbroot, conjugation)
        madhimudhary = mazeed!![0]
        faelmafool = mazeed!![1]
        amrandnahi = mazeed!![2]
        val arrayList = mazeed!![4]
        sarfSagheer = arrayList[0] as SarfSagheer
    } else {
        mujarrad = GatherAll.instance.getMujarradListing(mood, verbroot, vb.wazan!!)
        val arrayList = mujarrad[11]
        sarfSagheer = arrayList[0] as SarfSagheer
        madhimudhary = mujarrad!![0]
        faelmafool = mujarrad!![1]
        amrandnahi = mujarrad!![2]
        mifalun = mujarrad!![5][0] as IsmAlaMifalun
        mifalatun = mujarrad!![6][0] as IsmAlaMifalatun
        mifaalun = mujarrad!![7][0] as IsmAlaMifaalun
        zmafilun = mujarrad!![8][0] as IsmZarfMafilun
        zmafalatun = mujarrad!![9][0] as IsmZarfMafalatun
        zmafalun = mujarrad!![10][0] as IsmZarfMafalun
    }
    vb.wazan = conjugation
    val utils = Utils(QuranGrammarApplication.context)


    val corpus = CorpusUtilityorig
    if (!isMujarrad) {
        // tab items
        tabItems = listOf(
            TabItems(

                title = "Verb Conjugation Summary",
                unselectedIcon = Icons.Outlined.Home,
                selectedIcon = Icons.Filled.Home
            ),
            TabItems(
                title = "Verb Conjugation",
                unselectedIcon = Icons.Outlined.Email,
                selectedIcon = Icons.Filled.Email
            ),
            TabItems(
                title = "Active/Passive Participles",
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                selectedIcon = Icons.Filled.Favorite
            ),
            TabItems(
                title = "Noun of Instruments",
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                selectedIcon = Icons.Filled.Favorite
            ),
            TabItems(
                title = "Adverb of Place/time",
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                selectedIcon = Icons.Filled.Favorite
            ),
        )
    } else {

         tabItems = listOf(
            TabItems(

                title = "Verb Conjugation Summary",
                unselectedIcon = Icons.Outlined.Home,
                selectedIcon = Icons.Filled.Home
            ),
            TabItems(
                title = "Verb Conjugation",
                unselectedIcon = Icons.Outlined.Email,
                selectedIcon = Icons.Filled.Email
            ),
            TabItems(
                title = "Active/Passive Participles",
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                selectedIcon = Icons.Filled.Favorite
            ),

            )

    }

    // remember the selected tab
    var selectedTabIndex by remember {
        mutableIntStateOf(0) // or use mutableStateOf(0)
    }

    // pager state
    var pagerState = rememberPagerState {
        tabItems.size
    }

    // coroutine scope
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // tab row
        val backgroundColor = MaterialTheme.colorScheme.background
        TabRow(
            selectedTabIndex = selectedTabIndex,

            contentColor = Color.Black,

            containerColor = MaterialTheme.colorScheme.inversePrimary


        ) {
            // tab items
            tabItems.forEachIndexed { index, item ->

                Tab(
                    selected = (index == selectedTabIndex),


                    onClick = {
                        selectedTabIndex = index

                        // change the page when the tab is changed
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
                    },
                    text = {
                        Text(text = item.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) item.selectedIcon else item.unselectedIcon,
                            contentDescription = null
                        )
                    }
                )
            }
        }

        // pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            // app content
            if (!isMujarrad) {
                if (index == 0) {

                    sagheerscreen()
                }
                if (index == 1) {
                    cscreen(madhimudhary!!, amrandnahi!!)
                } else if (index == 2) {
                    participlescreen(faelmafool!!)
                } else if (index == 3) {
                    ismalascreen(mifalun, mifalatun, mifaalun)
                } else if (index == 4) {
                    ismzarfscreen(zmafilun, zmafalatun, zmafalun)
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tabItems[index].title,
                        fontSize = 18.sp
                    )
                }
            } else if (isMujarrad) {
                if (index == 0) {

                    sagheerscreen()
                }
                if (index == 1) {
                    cscreen(madhimudhary!!, amrandnahi!!)
                } else if (index == 2) {
                    participlescreen(faelmafool!!)
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tabItems[index].title,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

// change the tab item when current page is changed
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun sagheerscreen() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


        modifier = Modifier

            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.primary)
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedCard(
            onClick = { Log.d("Click", "CardExample: Card Click") },
        /*    colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
            ),*/
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = sarfSagheer.verbroot!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.verbtype!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.chaptername!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = sarfSagheer.faelsin!!.replace("[", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.mudharayhua!!.replace("[", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.madhihua!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = sarfSagheer.mafoolsin!!.replace("[", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.mudharaymajhoolhua!!.replace("[", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.madhimajhoolhua!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.nahiamr!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.amr!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
          /*  colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
            ),*/
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Noun of Instrument",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = sarfSagheer.ismalaone!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.ismalatwo!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.ismalathre!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }






        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Adverb of Place& Time",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )

            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = sarfSagheer.ismzarfone!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.ismzarftwo!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = sarfSagheer.ismzarfthree!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun sarfsagheers() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


        modifier = Modifier

            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",

                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        OutlinedCard(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    vertical = 10.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Noun of Instrument",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }






        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Adverb of  Place & Time",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,

                    fontFamily = indopak
                )

            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Check",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


    }
}

fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cscreen(madhimudhary: java.util.ArrayList<*>, amrandnahi: java.util.ArrayList<*>) {
    /*
        var allofQuran: List<QuranEntity>? = null

        val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)
        val utils = Utils(QuranGrammarApplication.context)
        val mujarrad: ArrayList<ArrayList<*>> =
            GatherAll.instance.getMujarradListing("Indicative", root, conjugation)
        val madhimudhary = mujarrad[0]
        val faelmafool = mujarrad[1]
        val amrandnahi = mujarrad[2]

        val madhi = madhimudhary[0]
        val madhimajhool = madhimudhary[1]
        val mudharaymaroof = madhimudhary[2]
        val mudharaymajhool = madhimudhary[3]
        val amr = amrandnahi[0]
        val amrnahi = amrandnahi[1]

        val corpus = CorpusUtilityorig
    */
    val madhi = madhimudhary[0]
    val madhimajhool = madhimudhary[1]
    val mudharaymaroof = madhimudhary[2]
    val mudharaymajhool = madhimudhary[3]
    val amr = amrandnahi[0]
    val amrnahi = amrandnahi[1]
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


        modifier = Modifier

            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.primary)
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Past Active",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhi as MadhiMudharay).hum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).huma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).hua!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhi as MadhiMudharay).hunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).humaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).hia!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhi as MadhiMudharay).antum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).antuma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).anta!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhi as MadhiMudharay).antunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).antumaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).anti!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhi as MadhiMudharay).nahnu!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhi as MadhiMudharay).ana!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        //mudharay
        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Past Passive",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhimajhool as MadhiMudharay).hum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).huma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).hua!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhimajhool as MadhiMudharay).hunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).humaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).hia!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhimajhool as MadhiMudharay).antum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).antuma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).anta!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhimajhool as MadhiMudharay).antunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).antumaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).anti!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (madhimajhool as MadhiMudharay).nahnu!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (madhimajhool as MadhiMudharay).ana!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

///mudharay

        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Present Active",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymaroof as MadhiMudharay).hum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).huma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).hua!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymaroof as MadhiMudharay).hunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).humaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).hia!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymaroof as MadhiMudharay).antum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).antuma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).anta!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymaroof as MadhiMudharay).antunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).antumaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).anti!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymaroof as MadhiMudharay).nahnu!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymaroof as MadhiMudharay).ana!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        //muhdary maroof


        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },

            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Present Passive",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymajhool as MadhiMudharay).hum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).huma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).hua!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymajhool as MadhiMudharay).hunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).humaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).hia!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymajhool as MadhiMudharay).antum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).antuma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).anta!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymajhool as MadhiMudharay).antunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).antumaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).anti!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (mudharaymajhool as MadhiMudharay).nahnu!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (mudharaymajhool as MadhiMudharay).ana!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }



        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),


            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    horizontal = 10.dp,
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Command",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (amr as AmrNahiAmr).antum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amr as AmrNahiAmr).antuma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amr as AmrNahiAmr).anta!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (amr as AmrNahiAmr).antunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amr as AmrNahiAmr).antumaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amr as AmrNahiAmr).anti!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }

        }

        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Prohibition",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (amrnahi as AmrNahiAmr).antum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amrnahi as AmrNahiAmr).antuma!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amrnahi as AmrNahiAmr).anta!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

        Column {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (amrnahi as AmrNahiAmr).antunna!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amrnahi as AmrNahiAmr).antumaf!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (amrnahi as AmrNahiAmr).anti!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }

        }

    }
}

data class TabItems(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun participlescreen(faelmafool: java.util.ArrayList<*>) {


    var allofQuran: List<QuranEntity>? = null

    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)
    val utils = Utils(QuranGrammarApplication.context)
    //     val mujarrad: ArrayList<ArrayList<*>> =
    //    GatherAll.instance.getMujarradListing("Indicative", root, conjugation)


    val corpus = CorpusUtilityorig


    val faleactive = faelmafool[0]
    val faelpassive = faelmafool[1]

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


        modifier = Modifier

            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.primary)
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Active Participle Masc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faleactive as FaelMafool).nomplurarM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).nomdualM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).nomsinM!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faleactive as FaelMafool).accplurarlM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).accdualM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).accsinM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faleactive as FaelMafool).genplurarM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).gendualM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).gensinM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


///mudharay


        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Active Participle-Feminine",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faleactive as FaelMafool).nomplurarF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).nomdualF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).nomsinF!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faleactive as FaelMafool).accplurarlF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).accdualF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).accsinF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faleactive as FaelMafool).genplurarF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).gendualF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faleactive as FaelMafool).gensinF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

////

        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Pssive Participle Masc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faelpassive as FaelMafool).nomplurarM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).nomdualM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).nomsinM!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faelpassive as FaelMafool).accplurarlM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).accdualM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).accsinM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faelpassive as FaelMafool).genplurarM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).gendualM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).gensinM!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


///mudharay


        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Passive Participle-Feminine",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faelpassive as FaelMafool).nomplurarF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).nomdualF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).nomsinF!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faelpassive as FaelMafool).accplurarlF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).accdualF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).accsinF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = (faelpassive as FaelMafool).genplurarF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).gendualF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = (faelpassive as FaelMafool).gensinF!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


    }


///mudharay


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ismalascreen(mifalun: IsmAlaMifalun, mifalatun: IsmAlaMifalatun, mifaalun: IsmAlaMifaalun) {


    var allofQuran: List<QuranEntity>? = null

    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)
    val utils = Utils(QuranGrammarApplication.context)
    //     val mujarrad: ArrayList<ArrayList<*>> =
    //    GatherAll.instance.getMujarradListing("Indicative", root, conjugation)


    val corpus = CorpusUtilityorig



    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


        modifier = Modifier

            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.primary)
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Mifalun",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifalun.nomplurarMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalun.nomdualMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalun.nomsinMifalun!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Nom.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifalun.accplurarlMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalun.accdualMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalun.accsinMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Acc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifalun.genplurarMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalun.gendualMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalun.gensinMifalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Gen.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


///mudharay


        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Mifalatun",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifalatun.nomplurarMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalatun.nomdualMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalatun.nomsinMifalatun!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Nom.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifalatun.accplurarlMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalatun.accdualMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalatun.accsinMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Acc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifalatun.genplurarMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalatun.gendualMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifalatun.gensinMifalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Gen.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

////

        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "Mifalun",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifaalun.nomplurarMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifaalun.nomdualMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifaalun.nomsinMifaalun!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Nom.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifaalun.accplurarlMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifaalun.accdualMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifaalun.accsinMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Acc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mifaalun.genplurarMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifaalun.gendualMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mifaalun.gensinMifaalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Gen.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


///mudharay


    }


///mudharay


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ismzarfscreen(mafilun: IsmZarfMafilun, zmafalatun: IsmZarfMafalatun, zmafalun: IsmZarfMafalun) {


    var allofQuran: List<QuranEntity>? = null

    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)
    val utils = Utils(QuranGrammarApplication.context)
    //     val mujarrad: ArrayList<ArrayList<*>> =
    //    GatherAll.instance.getMujarradListing("Indicative", root, conjugation)


    val corpus = CorpusUtilityorig



    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


        modifier = Modifier

            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.primary)
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "mafilun",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mafilun.nomplurarMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mafilun.nomdualMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mafilun.nomsinMafilun!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Nom.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mafilun.accpluralMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mafilun.accdualMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mafilun.accsinMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Acc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = mafilun.genplurarMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mafilun.gendualMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = mafilun.gensinMafilun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Gen.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


///mudharay


        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "zmafalatun",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = zmafalatun.nomplurarMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalatun.nomdualMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalatun.nomsinMafalatun!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Nom.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = zmafalatun.accplurarlMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalatun.accdualMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalatun.accsinMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Acc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = zmafalatun.genplurarMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalatun.gendualMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalatun.gensinMafalatun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Gen.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }

////

        Card(
            onClick = { Log.d("Click", "CardExample: Card Click") },
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
                    //vertical = 8.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = "mafilun",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,

                    fontFamily = indopak
                )
            }
        }

        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = zmafalun.nomplurarMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalun.nomdualMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalun.nomsinMafalun!!.toString().replace("[", "")
                        .replace("]", ""),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Nom.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = zmafalun.accplurarlMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalun.accdualMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalun.accsinMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Acc.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }
        Column {


            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Text(
                    text = zmafalun.genplurarMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalun.gendualMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = zmafalun.gensinMafalun!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
                Text(
                    text = "Gen.",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = indopak
                )
            }
        }


///mudharay


    }


///mudharay


}









