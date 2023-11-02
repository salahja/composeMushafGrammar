package com.appscreens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.codelab.basics.ui.theme.indopak
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import org.sj.conjugator.utilities.GatherAll
import org.sj.verbConjugation.AmrNahiAmr
import org.sj.verbConjugation.MadhiMudharay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConjugationScreen(navController: NavHostController, conjugation: String, root :String, mood: String?) {
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
                    text = "Past Tense",
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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    text = "Past Tense",
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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    text = "Past Tense",
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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    text = "Past Tense",
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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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
                    horizontal = 20.dp,
                    //vertical = 8.dp
                )
        ) {


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