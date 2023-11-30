package com.example.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.appscreens.WordAlertDialog
import com.appscreens.aid
import com.appscreens.cid
import com.appscreens.wid
import kotlinx.coroutines.launch

var modecolor: Color? = null

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextChip(
  isDarkmode: Boolean,
  isSelected: Boolean,
  text: AnnotatedString,
  onChecked: (Boolean) -> Unit,
  selectedColor: Color = DarkGray,
  modecolor: Color
) {
  if (isSelected && isDarkmode) {

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
        .clip(shape = RectangleShape)
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

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextChipWBW(
  showword: Boolean,
  navController: NavHostController,
  isDarkmode: Boolean,
  isSelected: Boolean,
  text: AnnotatedString,
  onChecked: (Boolean) -> Unit,
  selectedColor: Color = DarkGray
) {
  if (isDarkmode) {
    modecolor = Color.White
  } else {
    modecolor = Color.Black
  }
  val coroutineScope = rememberCoroutineScope()

/*
  if (showword && cid != 0 && aid != 0) {
    Box {
      WordAlertDialog(viewModel(), cid, aid, wid, navController)

    }
  }
*/

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
          width = 0.3.dp,
          color = if (isSelected) selectedColor else LightGray,
          shape = RectangleShape
      )
        .background(
            color = if (isSelected) Transparent else Transparent,
         //   shape = RectangleShape
        )

        .clip(shape = RectangleShape)
        .clickable {

            onChecked(!isSelected)
        }
        .padding(4.dp)
  ) {


    (if (isSelected) White else White)?.let {
      if (isSelected && cid != 0 && aid != 0) {
        Box {
          WordAlertDialog(viewModel(), cid, aid, wid, navController)

        }
      }
      Text(
        text = text,
        //  color = it,
        fontSize = 18.sp,

        )
    }
  }
}

