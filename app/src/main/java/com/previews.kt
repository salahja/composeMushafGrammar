package com

import android.util.Size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


@Composable
@Preview
private fun SplitLayouts(

) {
    Row(Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(0.2f)) {

            Card(


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
                        vertical = 8.dp
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()

                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    androidx.compose.material.Text(text = "entry")
                }
            }
        }
        //    twoPaneScope.list(twoPaneScope.items, onSelectionChange)

        Box(modifier = Modifier.weight(1f)) {

            Card(


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
                        vertical = 8.dp
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        //  .clickable { onSelectionChange(entry) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    androidx.compose.material.Text(text = "entry")
                }
            }
        }

    }


}