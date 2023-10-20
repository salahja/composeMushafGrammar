package com.example.mushafconsolidated.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mushafconsolidated.R

class MainActivitys: ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Creating a Simple Scaffold
            // Layout for the application
            Scaffold(

                // Creating a Top Bar
                topBar = { TopAppBar(title = { Text("GFG | Collapsing Toolbar", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) },

                // Creating Content
                content = {

                    // Creating a Column Layout
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                        // Creating a Scrollable list of 100 items
                        val items = (1..100).map { "Item $it" }
                        val lazyListState = rememberLazyListState()
                        var scrolledY = 0f
                        var previousOffset = 0
                        LazyColumn(
                            Modifier.fillMaxSize(),
                            lazyListState,
                        ) {
                            // Setting the Image as the first
                            // item and making it collapsible
                            item {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_quran_nav_top_54),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .graphicsLayer {
                                            scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                            translationY = scrolledY * 0.5f
                                            previousOffset = lazyListState.firstVisibleItemScrollOffset
                                        }
                                        .height(240.dp)
                                        .fillMaxWidth()
                                )
                            }

                            // Displaying the remaining 100 items
                            items(items) {
                                Text(
                                    text = it,
                                    Modifier
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}
