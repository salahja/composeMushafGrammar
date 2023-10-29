package com.adaptive

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.compose.theme.aid
import com.example.compose.theme.cid
import com.example.compose.theme.wid
import com.example.justJava.MyTextViewZoom
import com.example.mushafconsolidated.Entities.qurandictionary
import com.example.mushafconsolidated.Utils
import com.example.utility.QuranGrammarApplication
import com.skyyo.expandablelist.theme.AppThemeSettings
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: String?,
    isOnlyDetailScreen: Boolean,
    navController: NavHostController,
    onBackPressed: () -> Unit,

) {
    val util= Utils(QuranGrammarApplication.context!!)
    val searchs= "$userId%";
    val letter: ArrayList<qurandictionary> = util.getByfirstletter(searchs!!) as ArrayList<qurandictionary>
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isOnlyDetailScreen)
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = { Text(text = "User Detail", textAlign = TextAlign.Center) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressed()
                            },
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
        },
    ) {
        LazyVerticalGrid(

            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),

            columns = GridCells.Fixed(4),

        ) {
            items(letter!!.size) { index ->
                //          indexval=index
                GridList(letter[index],navController)
            }
        }
    }

}

fun GridLists(qurandictionary: qurandictionary) {
    TODO("Not yet implemented")
}

@Composable
fun GridList(
    surahModelList: qurandictionary?,
    navController: NavHostController,

    ) {

    val darkThemeEnabled = AppThemeSettings.isDarkThemeEnabled

    Card(

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
                vertical = 8.dp
            )

    )
    {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            val root=        surahModelList!!.rootarabic
           // indexval = surahModelList!!.chapterid
            ClickableText(
                text = AnnotatedString(surahModelList!!.rootarabic),

                onClick = {
                    Log.d(MyTextViewZoom.TAG, "mode=ZOOM")

                    navController.navigate(

                        "roots/$root"

                    )



                })





        }
    }


    @Suppress("DEPRECATION")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @ExperimentalFoundationApi
    @Composable
    fun BottomDialog() {
        //Lets create list to show in bottom sheet
        data class BottomSheetItem(val title: String, val icon: ImageVector)

        val bottomSheetItems = listOf(
            BottomSheetItem(title = "Notification", icon = Icons.Default.Notifications),
            BottomSheetItem(title = "Mail", icon = Icons.Default.MailOutline),
            BottomSheetItem(title = "Scan", icon = Icons.Default.Search),
            BottomSheetItem(title = "Edit", icon = Icons.Default.Edit),
            BottomSheetItem(title = "Favorite", icon = Icons.Default.Favorite),
            BottomSheetItem(title = "Settings", icon = Icons.Default.Settings)
        )

        //Lets define bottomSheetScaffoldState which will hold the state of Scaffold
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        val coroutineScope = rememberCoroutineScope()
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetShape = RoundedCornerShape(topEnd = 30.dp),
            sheetContent = {
                //Ui for bottom sheet
                Column(
                    content = {

                        Spacer(modifier = Modifier.padding(16.dp))
                        androidx.compose.material.Text(
                            text = "Bottom Sheet",
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,
                            color = Color.White
                        )
                        LazyVerticalGrid(
                            //cells = GridCells.Fixed(3)
                            columns = GridCells.Fixed(3), //https://developer.android.com/jetpack/compose/lists
                        ) {
                            items(bottomSheetItems.size, itemContent = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp)
                                        .clickable {


                                        },
                                ) {
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    androidx.compose.material.Icon(
                                        bottomSheetItems[it].icon,
                                        bottomSheetItems[it].title,
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    androidx.compose.material.Text(text = bottomSheetItems[it].title, color = Color.White)
                                }

                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)

                        //.background(Color(0xFF6650a4))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF8E2DE2),
                                    Color(0xFF4A00E0)
                                )
                            ),
                            // shape = RoundedCornerShape(cornerRadius)
                        )
                        .padding(16.dp),

                    )
            },
            sheetPeekHeight = 0.dp,

            ) {


            //Add button to open bottom sheet
            Column(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }
                ) {
                    androidx.compose.material.Text(
                        text = "Click to show Bottom Sheet"
                    )
                }
            }
        }
    }
}