package com.example.tabcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mushafconsolidated.R

@Composable
fun MusicScreen() {
Column {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .width(200.dp)
            .background(colorResource(id = R.color.colorPrimary))
            .wrapContentSize(Alignment.Center)

    ) {

        Text(
            text = "hua",
            fontWeight = FontWeight.Bold,
            color = Color.Red,

            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "huma",
            fontWeight = FontWeight.Bold,
            color = Color.White,

            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "huma",
            fontWeight = FontWeight.Bold,
            color = Color.White,

            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorPrimary))
            .wrapContentSize(Alignment.Center)

    ) {

        Text(
            text = "hua",
            fontWeight = FontWeight.Bold,
            color = Color.Yellow,

            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "huma",
            fontWeight = FontWeight.Bold,
            color = Color.White,

            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "huma",
            fontWeight = FontWeight.Bold,
            color = Color.White,

            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.colorPrimary))
                .wrapContentSize(Alignment.Center)

        ) {

            Text(
                text = "hua",
                fontWeight = FontWeight.Bold,
                color = Color.Green,

                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            Text(
                text = "huma",
                fontWeight = FontWeight.Bold,
                color = Color.White,

                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            Text(
                text = "huma",
                fontWeight = FontWeight.Bold,
                color = Color.White,

                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MusicScreenPreview() {
    MusicScreen()
}

@Composable
fun MoviesScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Movies View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "Movies View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "Movies View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Text(
            text = "Movies View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    MoviesScreen()
}

@Composable
fun BooksScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorPrimary))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Books View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
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