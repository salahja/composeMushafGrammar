package com.appscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.R
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.activities.TopBar
import com.codelab.basics.ui.theme.qalam
import com.viewmodels.ExpandableVerseViewModel

@Composable
fun VerseAnalysisScreen(
    versemodel: ExpandableVerseViewModel,
    navController: NavHostController,
    chapterid: Int?,
    verseid: Int?
) {

    val itemIds by versemodel.itemIds.collectAsState()

    Scaffold(
        topBar = { TopBar() }
    ) { padding ->  // We need to pass scaffold's inner padding to the content
        LazyColumn(modifier = Modifier.padding(padding)) {
            itemsIndexed(versemodel.items.value) { index, item ->
                ExpandableContainerView(
                    itemModel = item,
                    onClickItem = { versemodel.onItemClicked(index) },
                    expanded = itemIds.contains(index)
                )
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val versemodel = ExpandableVerseViewModel(thememode = true`,IntPreferenceSettingValueState.value)
    VerseAnalysisScreen(versemodel = viewModel)
}*/
//fun ExpandableContainerView(itemModel: DataModel, onClickItem: () -> Unit, expanded: Boolean) {
@Composable
fun ExpandableContainerView(
    itemModel: ExpandableVerseViewModel.VerseAnalysisModel,
    onClickItem: () -> Unit,
    expanded: Boolean
) {
    Card(
       // contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            //  .background(color = Color.White)
            .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(16.dp))
            .clickable {

            }
            .padding(16.dp)
    ) {
        Column {
            HeaderView(questionText = itemModel.grammarrule, onClickItem = onClickItem)
            ExpandableView(result = itemModel.result, isExpanded = expanded)
        }
    }
}

@Composable
fun HeaderView(questionText: String, onClickItem: () -> Unit) {
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(
                indication = null, // Removes the ripple effect on tap
                interactionSource = remember { MutableInteractionSource() }, // Removes the ripple effect on tap
                onClick = onClickItem
            )
            .padding(8.dp)
    ) {
        Text(
            text = questionText,
            fontSize = 17.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun HeaderViews(questionText: String, onClickItem: () -> Unit) {
    TODO("Not yet implemented")
}


@Composable
fun ExpandableView(result: List<AnnotatedString>, isExpanded: Boolean) {
    // Opening Animation
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    // Closing Animation
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
            //    .background(MaterialTheme.colorScheme.primaryContainer)
        )


        {


            result.forEach { verses ->
                val annotatedString = verses
                Text(
                    text = annotatedString!!,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Thin,
                    fontSize = 18.sp,

                    )


                // on below line we are specifying
                // divider for each list item
                //  Divider(color = Color.Red, thickness = 3.dp)
            }
            Divider()
        }
    }
}


//fun HeaderView(questionText: String, onClickItem: () -> Unit) {


