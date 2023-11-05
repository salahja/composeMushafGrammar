package com.appscreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.corpusutility.AnnotationUtility
import com.example.compose.LoadingData
import com.example.compose.TextChip
import com.example.justJava.MyTextViewZoom
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.model.NewQuranCorpusWbw

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun flowpre() {


    val item = (1..100).toList()

    LoadingData(isDisplayed = false)
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(3) }
    //  LazyColumn(state = listState!!,      modifier = Modifier.fillMaxSize(),
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(item!!.toList()) { index, item ->
            //   val img = imgs.getDrawable(surahs!!.chapid - 2)
            Card(
                colors = CardDefaults.cardColors(
                    //      containerColor = colorResource(id = R.color.bg_surface_dark_blue),
                ), elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                ), modifier = Modifier
                    .fillMaxWidth()

                    .padding(
                        horizontal = 10.dp,
                        vertical = 8.dp
                    )
            )


            {
                RightToLeftLayout {
                    FlowRow(
                        verticalArrangement = Arrangement.Top,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        maxItemsInEachRow = 6,
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(
                                horizontal = 10.dp,
                                vertical = 8.dp
                            )
                    )

                    {


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()

                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = "word one",

                                fontWeight = FontWeight.Bold,
                                color = Color.Black,

                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )

                            Text(
                                text = "word two",

                                fontWeight = FontWeight.Bold,
                                color = Color.Black,

                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )



                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()

                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = "trans one",

                                fontWeight = FontWeight.Bold,
                                color = Color.Black,

                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )




                        }

                    }
                    FlowRow(
                        verticalArrangement = Arrangement.Top,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        maxItemsInEachRow = 6,
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(
                                horizontal = 10.dp,
                                vertical = 8.dp
                            )
                    )

                    {


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()

                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = "word one",

                                fontWeight = FontWeight.Bold,
                                color = Color.Black,

                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )





                        }

                    }






                }

            }
        }
    }
}