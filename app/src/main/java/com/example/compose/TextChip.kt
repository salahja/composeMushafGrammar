package com.example.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

var modecolor: Color?=null

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextChip(
    isDarkmode: Boolean,
    isSelected: Boolean,
    text: AnnotatedString,
    onChecked: (Boolean) -> Unit,
    selectedColor: Color = DarkGray,
    modecolor: Color
)
 {
     if(isSelected && isDarkmode){

     }
    FlowRow(
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.SpaceEvenly,
        maxItemsInEachRow = 6,
        modifier = Modifier

             .padding(
                vertical = 2.dp,
                horizontal = 4.dp
            )

            .border(
                width = 1.dp,
                color = if (isSelected) selectedColor else LightGray,
                shape = RectangleShape
            )
            .background(
                color = if (isSelected) selectedColor else Transparent,
                shape = RectangleShape
            )
            .clip( shape = RectangleShape)
            .clickable {
                onChecked(!isSelected)
            }
            .padding(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,

            color = if (isSelected) White else modecolor
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextChipWBW(
    isDarkmode: Boolean,
    isSelected: Boolean,
    text: AnnotatedString,
    onChecked: (Boolean) -> Unit,
    selectedColor: Color = DarkGray
) {
 if(isDarkmode){
     modecolor= Color.White
 }else{
     modecolor=Color.Black
 }
    FlowRow(
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.SpaceEvenly,
        maxItemsInEachRow = 6,
        modifier = Modifier

            .padding(
                vertical = 2.dp,
                horizontal = 4.dp
            )
  /*          .border(
                width = 0.3.dp,
                color = if (isSelected) selectedColor else LightGray,
                shape = RectangleShape
            )*/
            .background(
                color = if (isSelected) selectedColor else Transparent,
                shape = RectangleShape
            )

            .clip( shape = RectangleShape)
            .clickable {
                onChecked(!isSelected)
            }
            .padding(4.dp)
    ){
        (if (isSelected) White else modecolor)?.let {
            Text(
                text = text,
                color = it,
                fontSize = 18.sp,
            )
        }
    }
}

