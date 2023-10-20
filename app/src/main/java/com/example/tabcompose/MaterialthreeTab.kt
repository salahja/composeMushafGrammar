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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import org.sj.verbConjugation.MadhiMudharay

var mujarrad: ArrayList<ArrayList<*>>? =null
var mazeed: ArrayList<ArrayList<*>>? =null
var amrandnahi: ArrayList<*>? = null
var faelmafool: ArrayList<*>? = null
var madhimudhary: ArrayList<*>? = null

@OptIn(ExperimentalFoundationApi::class)
@Composable

fun MatTab(navController: NavHostController, conjugation: String, root: String, mood: String?) {
    var allofQuran: List<QuranEntity>? = null
val vb = VerbWazan()
    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)

    val filter = conjugation.toString().filter(Char::isDigit)
    val numeric = isNumeric(conjugation)

   vb.wazan = conjugation
    if(numeric){
      mazeed =  GatherAll.instance.getMazeedListing(mood, root, conjugation)
        madhimudhary = mazeed!![0]
        faelmafool = mazeed!![1]
        amrandnahi = mazeed!![2]
    }else{
        mujarrad =   GatherAll.instance.getMujarradListing(mood, root,  vb.wazan!! )

        madhimudhary = mujarrad!![0]
        faelmafool = mujarrad!![1]
        amrandnahi = mujarrad!![2]
    }
    vb.wazan = conjugation
    val utils = Utils(QuranGrammarApplication.context)



    val corpus = CorpusUtilityorig

    // tab items
    val tabItems = listOf(
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

            if (index == 0) {
                cscreen(madhimudhary!!, amrandnahi!!)
            } else if (index == 2) {
                //   participlescreen(faelmafool!!)
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

    // change the tab item when current page is changed
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage

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
            .background(MaterialTheme.colorScheme.primary)
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


/*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun participlescreen(faelmafool: java.util.ArrayList<*>) {
    */
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
    *//*

    val madhi = faelmafool[0]
    val madhimajhool = faelmafool[1]

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,


        modifier = Modifier

            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
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
}*/
