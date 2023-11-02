package com.example.compose

import android.content.res.TypedArray
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.utility.QuranGrammarApplication.Companion.context

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //   .background(colorResource(id = R.color.colorPrimaryDark))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Home View",
            fontWeight = FontWeight.Bold,
            //
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier

            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
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
                    .border(1.dp, Color.Red)
            ) {


                androidx.compose.material3.Text(
                    text = "Past Tense",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                   

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Present Tense",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

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


                androidx.compose.material3.Text(
                    text = "Nahnu",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
                androidx.compose.material3.Text(
                    text = "ana",
                    fontWeight = FontWeight.Bold,
                 

                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }






}

@Preview(showBackground = true)
@Composable
fun MusicScreenPreview() {
    MusicScreen()
}


@Composable
fun BooksScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //  .background(colorResource(id = R.color.colorPrimaryDark))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Books View",
            fontWeight = FontWeight.Bold,
            //
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BooksScreenPreview() {
    BooksScreen()
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //  .background(colorResource(id = R.color.colorPrimaryDark))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Profile View",
            fontWeight = FontWeight.Bold,
         
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

@Composable
fun GridScreens(enableDarkMode: Boolean) {
    val imgs = context!!.resources.obtainTypedArray(R.array.sura_imgs)
    val utils = Utils(context)
    val allAnaChapters = utils.getAllAnaChapters()!!;
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        content = {
            items(allAnaChapters!!.size) { index ->
                GridList(allAnaChapters[index], imgs, enableDarkMode)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
private
@Composable

fun GridList(surahModelList: ChaptersAnaEntity?, imgs: TypedArray, enableDarkMode: Boolean) {
    val img = imgs.getDrawable(surahModelList!!.chapterid.toInt() - 1)


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
    )
    {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(

                text = surahModelList!!.chapterid.toString(),
                fontSize = 20.sp
            )
            Text(

                text = surahModelList!!.namearabic,
                fontSize = 20.sp
            )
            Text(

                text = surahModelList.nameenglish,
                fontSize = 10.sp
            )


            if (!enableDarkMode) {
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