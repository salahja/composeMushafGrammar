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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextChip(
    isSelected: Boolean,
    text: AnnotatedString,
    onChecked: (Boolean) -> Unit,
    selectedColor: Color = DarkGray
)
 {
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
                shape = CircleShape
            )
            .background(
                color = if (isSelected) selectedColor else Transparent,
                shape = CircleShape
            )
            .clip( shape = CircleShape)
            .clickable {
                onChecked(!isSelected)
            }
            .padding(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 25.sp,
            color = if (isSelected) White else Unspecified
        )
    }
}

@Composable
fun TextChip(
    isSelected: Boolean,
    text: String,
    onChecked: (Boolean) -> Unit,
    selectedColor: Color = DarkGray
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 2.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = if (isSelected) selectedColor else LightGray,
                shape = CircleShape
            )
            .background(
                color = if (isSelected) selectedColor else Transparent,
                shape = CircleShape
            )
            .clip( shape = CircleShape)
            .clickable {
                onChecked(!isSelected)
            }
            .padding(4.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) White else Unspecified
        )
    }
}

