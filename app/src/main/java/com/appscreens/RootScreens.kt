package com.appscreens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.activities.NavigationType
import com.activities.showbootomsheet
import com.corpusutility.NewQuranMorphologyDetails
import com.example.compose.QuranMorphologyDetails
import com.example.compose.WordOccuranceLoading
import com.example.compose.theme.showProgress
import com.example.mushafconsolidated.Entities.RootVerbDetails
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.utility.QuranGrammarApplication
import com.viewmodels.RootModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootScreens(
    rootmodel: RootModel,
    isOnlyDetailScreen: Boolean,
    navigationType: NavigationType,
    goToUserDetail: (String?) -> Unit,
    onDetailBackPressed: () -> Unit,
    navController: NavHostController
) {
    val util = Utils(QuranGrammarApplication.context!!)
    var loading = rootmodel.loading.value

    val roots by rootmodel.verbroot.collectAsStateWithLifecycle()
    //   loading = rootmodel.loading.value
    //  RootWordLoading(showDialog = loading)
    // val collectAsStateWithLifecycle = rootmodel.verbroot.collectAsStateWithLifecycle()
    // val collectAsState = rootmodel.verbroot.collectAsState()

    val verbroots = roots[0].verbrootlist


    // loading = rootmodel.loading.value
    //  rootmodel.open.value = true
    showProgress(199)
    /*   val nm=NounMorphologyDetails(nounroots ,nouns)
       var wordbdetail = HashMap<String, AnnotatedString>()
       wordbdetail=nm.wordDetails

       if(verbroots.isEmpty()){
           wordbdetail=nm.wordDetails

       }*/
    loading = rootmodel.loading.value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isOnlyDetailScreen)
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = { Text(text = "User Detail", textAlign = TextAlign.Center) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onDetailBackPressed()
                            },
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
        },
    )

    {
        //  if (verbroots.isNotEmpty())

        WordOccuranceLoading(loading)
        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),


            ) {


            items(verbroots.size) { index ->
                //          indexval=index
                RootGrid(verbroots[index], navController)
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RootGrid(rootdetails: RootVerbDetails, navController: NavHostController) {
    //  val darkThemeEnabled = AppThemeSettings.isDarkThemeEnabled
    var wazan = ""
    if (rootdetails.form == "I") {
        if (rootdetails.thulathibab!!.length > 1) {
            val s = rootdetails.thulathibab!!.substring(0, 1)
            wazan = QuranMorphologyDetails.getThulathiName(s).toString()
        } else {
            wazan = QuranMorphologyDetails.getThulathiName(rootdetails.thulathibab).toString()
        }


        //   QuranMorphologyDetails.getThulathiName(rootdetails.getThulathibab());
    } else {
        wazan = QuranMorphologyDetails.getFormName(rootdetails.form)
    }

    var surahinfo = StringBuilder()
    surahinfo.append(rootdetails.surah).append(":").append(rootdetails.ayah).append(":")
        .append(rootdetails.wordno)

    val gender =
        QuranMorphologyDetails.getGenderNumberdetails(rootdetails.gendernumber)
    val tense = StringBuilder()
    tense.append(rootdetails.tense).append(":").append(rootdetails.voice).append(":")
        .append(rootdetails.mood_kananumbers)

    val start = rootdetails.qurantext!!.indexOf(rootdetails.arabic!!)
    val builder = AnnotatedString.Builder()
    builder.append(rootdetails.qurantext)
    val tagonestyle = SpanStyle(

        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
    )
    if (start != -1) {
        builder.addStyle(tagonestyle, start, start + rootdetails.arabic!!.length)
    }

    val form1 = rootdetails.form!!
    var sform = ""
    if (rootdetails.form!!.isNotEmpty() && !form1.equals("I")) {
        sform = convertFormss(form1)
    } else {
        var thulathi = rootdetails.thulathibab
        extracted(rootdetails, thulathi)
        sform = thulathi.toString()
    }
    var sa = StringBuilder()
    sa.append(rootdetails.surah).append(":").append(rootdetails.ayah).append(":")
        .append(rootdetails.wordno)



    Card(

        /*   colors = CardDefaults.cardColors(
               containerColor = MaterialTheme.colorScheme.background,
           ),*/
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),


        modifier = Modifier
            .fillMaxWidth()

            .padding(
                horizontal = 5.dp,
                vertical = 5.dp
            )

    )
    {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            var sa = StringBuilder()
            sa.append(rootdetails.surah).append(":").append(rootdetails.ayah).append(":")
                .append(rootdetails.wordno)


            // indexval = surahModelList!!.chapterid
            ClickableText(
                text = AnnotatedString(rootdetails.abjadname!!),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )
            ClickableText(
                text = AnnotatedString(sa.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )
            ClickableText(
                text = AnnotatedString(rootdetails.arabic.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )
            ClickableText(
                text = AnnotatedString(wazan.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )


        }







        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            // indexval = surahModelList!!.chapterid
            ClickableText(
                text = AnnotatedString(gender.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )
            ClickableText(
                text = AnnotatedString(tense.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )
            ClickableText(
                text = AnnotatedString(rootdetails.en.toString()),

                onClick = {


                },
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )

            )


        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            ExpandableText(
                text = builder.toAnnotatedString(),


                )
            Spacer(modifier = Modifier.width(12.dp))


        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            ExpandableText(
                text = AnnotatedString("Translation :" + rootdetails.translation!!),
                        style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontFamily = FontFamily.Cursive
            )

            )
            Spacer(modifier = Modifier.width(20.dp))


        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            ExpandableText(
                text = AnnotatedString("Tafsir Ibne Kathir:" + rootdetails.tafsir_kathir!!),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )


            )
            Spacer(modifier = Modifier.width(20.dp))


        }



        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val root = rootdetails.rootarabic
            val form = rootdetails.form

            val conjugation = rootdetails.thulathibab

            if (sform.length > 1) {
                sform = sform.substring(0, 1)
            }
            val mood = "Indicative"
            IconButton(

                onClick = {
                    showbootomsheet.value = false

                    navController.navigate(
                        "conjugator/${sform}/${root}/${mood}"
                    )
                }


            ) {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_construction_24),
                        contentDescription = "Expandable Arrow",
                        //  modifier = Modifier.rotate(20.0f),

                    )
                    Text("Conjugate")
                }
            }

            IconButton(
                {
                    val root = rootdetails.rootarabic
                    val form = rootdetails.form

                    val conjugation = rootdetails.thulathibab

                    if (sform.length > 1) {
                        sform = sform.substring(0, 1)
                    }
                    val mood = "Indicative"
                    navController.navigate(
                        "conjugator/${sform}/${root}/${mood}"
                    )
                },

                )
            {
                Icon(
                    painter = painterResource(id = R.drawable.tafsir),
                    contentDescription = "Expandable Arrow",
                    modifier = Modifier.rotate(20.0f),

                    )

            }

            if (showbootomsheet.value) {
                //BottomDialog()
            }


        }


    }
}



@Composable
fun PressIconButton(
    onClick: () -> Unit,

    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
    colors: Color,
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    Button(onClick = onClick, modifier = modifier,
        colors = ButtonDefaults.buttonColors(colors),
      //  colors = ButtonConstants.defaultButtonColors(backgroundColor = Color.Yellow)
        interactionSource = interactionSource) {
        AnimatedVisibility(visible = isPressed) {
            if (isPressed) {
                Row {
                    icon()
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing),
                        )
                }
            }
        }
        text(

        )
    }
}



const val DEFAULT_MINIMUM_TEXT_LINE = 1

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: AnnotatedString,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = showLessStyle) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = showMoreStyle) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign
        )
    }

}


@Composable
private fun extracted(
    rootdetails: RootVerbDetails,
    thulathi: String?
) {
    var thulathi1 = thulathi
    if (rootdetails.thulathibab!!.isNotEmpty()) {
        when (thulathi1!!.length) {
            0 -> thulathi1 =
                null

            1 -> {
                thulathi1 = rootdetails.thulathibab
                val sb = NewQuranMorphologyDetails.getThulathiName(thulathi1)

            }

            else -> {
                thulathi1.length
                val s = thulathi1.substring(0, 1)
                val sb = NewQuranMorphologyDetails.getThulathiName(thulathi1)
                thulathi1 = s
            }
        }
    }
}

fun convertFormss(form1: String): String {
    var form = form1
    when (form) {
        "IV" -> form = 1.toString()
        "II" -> form = 2.toString()
        "III" -> form = 3.toString()
        "VII" -> form = 4.toString()
        "VIII" -> form = 5.toString()
        "VI" -> form = 7.toString()
        "V" -> form = 8.toString()
        "X" -> form = 9.toString()
        else -> {

        }
    }


    return form
}


