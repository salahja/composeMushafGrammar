package com.previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.skyyo.expandablelist.theme.AppTheme

@Preview(
    name = "small font", group = "Font scaling", fontScale = 0.85f, showBackground = true
)
@Preview(
    name = "regular font", group = "Font scaling", fontScale = 1.0f, showBackground = true
)
@Preview(
    name = "large font", group = "Font scaling", fontScale = 1.15f, showBackground = true
)
@Preview(
    name = "extra large font", group = "Font scaling", fontScale = 1.3f, showBackground = true
)
annotation class FontScalePreviews

@FontScalePreviews
@Composable
fun NonResizingTextPreview() {
    AppTheme {

        Box(modifier = Modifier.width(200.dp)) {

            Text(
                text = "This is resizing text with font scale ${LocalDensity.current.fontScale}",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize.nonScaledSp
            )
        }
    }
}
val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp


