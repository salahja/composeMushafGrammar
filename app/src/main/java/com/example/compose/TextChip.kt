package com.example.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp

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

