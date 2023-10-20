package com.example.compose


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import org.sj.conjugator.utilities.GatherAll
import org.sj.verbConjugation.MadhiMudharay



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Conjugator() {
    var allofQuran: List<QuranEntity>? = null
     val root=""
    val imgs = QuranGrammarApplication.context!!.resources.obtainTypedArray(R.array.sura_imgs)
    val utils = Utils(QuranGrammarApplication.context)
    val mujarrad: ArrayList<ArrayList<*>> =
        GatherAll.instance.getMujarradListing("Indicative", root, "5")
    val madhimudhary = mujarrad[0]
    val faelmafool = mujarrad[1]
    val amrandnahi = mujarrad[2]

    val  madhi = madhimudhary[0]
    val madhimajhool=madhimudhary[1]
    val mudharaymaroof=madhimudhary[2]
    val mudharaymajhool=madhimudhary[3]


    val corpus = CorpusUtilityorig

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier

            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .wrapContentSize(Alignment.Center)
            .padding(top = 10.dp)
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
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Past Tense",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = (madhi as MadhiMudharay).hum!!,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Present Tense",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
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
                    .border(5.dp, Color.Magenta)
            ) {


                Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                Text(
                     text = "نصر",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }






}

@Preview(showBackground = true)
@Composable
fun ConjuationPreview() {
    Conjugator()
}
