package com.example.compose.theme

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.compose.NewQuranMorphologyDetails
import com.example.compose.TextChip

import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.utility.QuranGrammarApplication
lateinit var worddetails: HashMap<String, SpannableStringBuilder?>

@Composable
@Preview
fun mpre(){
    fun MyUi() {}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },

        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
@Preview
fun alterpreview(){

    fun AlertDialogContent(

    ) {
    }
}
@Composable
fun AlertDialogS(
    onDismissRequest: () -> Unit,
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        AlertDialogContent(
            buttons = buttons,
            modifier = modifier,
            title = title,
            text = text,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    }
}

@Composable
fun AlertDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier,
    title: @Composable() (() -> Unit)?,
    text: @Composable() (() -> Unit)?,
    shape: Shape,
    backgroundColor: Color,
    contentColor: Color
) {
    Column {
        Text(text = "FIRST")
        Text(text = "FIRST")
        Text(text = "FIRST")
        Text(text = "FIRST")
        Text(text = "FIRST")
        Text(text = "FIRST")
        Text(text = "FIRST")
    }
}
@Composable
fun CustomDialog(
    openDialogCustom: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: QuranVIewModel,
    chapterid: Int?,
    verseid: Int?,
    wordno: Int?
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false}) {
        CustomDialogUI(openDialogCustom = openDialogCustom)
    }
}



//Layout
@Composable
fun CustomDialogUI(modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>){
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),

        ) {
        Column(
            modifier
                .background(Color.White)) {

            //.......................................................................
            Image(
                painter = painterResource(id = R.drawable.ic_makkah_48),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter  = ColorFilter.tint(
                    color = Color.Green
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = "Get Updates",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                androidx.compose.material3.Text(
                    text = "Allow Permission to send you notifications when new art styles added.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
                androidx.compose.material3.Text(
                    text = "Get Updates",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                androidx.compose.material3.Text(
                    text = "Allow Permission to send you notifications when new art styles added.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )







            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(Color.Magenta),
                horizontalArrangement = Arrangement.SpaceAround) {

                androidx.compose.material3.TextButton(onClick = {
                    openDialogCustom.value = false
                }) {


                    androidx.compose.material3.Text(
                        "Not Now",
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                androidx.compose.material3.TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    androidx.compose.material3.Text(
                        "Allow",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    androidx.compose.material3.Text(
                        "Allow",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    androidx.compose.material3.Text(
                        "Allow",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview (name="Custom Dialog")
@Composable
fun MyDialogUIPreview(){
    CustomDialogUI(openDialogCustom = mutableStateOf(false))
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordALert(

    openDialogCustom: MutableState<Boolean>,
    navController: NavHostController,
    mainViewModel: QuranVIewModel,
    chapterid: Int?,
    verseid: Int?,
    wordno: Int?
) {

    val utils = Utils(QuranGrammarApplication.context)

    val corpusSurahWord = mainViewModel.getQuranCorpusWbw(chapterid!!, verseid!!, wordno!!).value

    var vbdetail = HashMap<String, String?>()
    val quran = mainViewModel.getsurahayahVerseslist(chapterid!!, verseid!!).value
    val corpusNounWord = mainViewModel.getNouncorpus(chapterid!!, verseid!!, wordno!!).value

    val verbCorpusRootWord =
        mainViewModel.getVerbRootBySurahAyahWord(chapterid!!, verseid!!, wordno!!).value


    val am = NewQuranMorphologyDetails(
        corpusSurahWord!!,
        corpusNounWord as ArrayList<NounCorpus>?,
        verbCorpusRootWord as ArrayList<VerbCorpus>?,
        QuranGrammarApplication.context
    )
    worddetails = am.wordDetails
    // wordbdetail = am.wordDetails
    if (verbCorpusRootWord != null) {
        if (verbCorpusRootWord.isNotEmpty() && verbCorpusRootWord[0].tag.equals("V")) {
            vbdetail = am.verbDetails
            //  isVerb = true
        }
    }

    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            }
        ) {
            Surface(
                   modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),


                shape = MaterialTheme.shapes.medium,

            ) {
                Column(
                    content = {

                        Spacer(modifier = Modifier.padding(16.dp))
                        androidx.compose.material.Text(
                            text = worddetails["surahid"].toString() + ":" + worddetails["ayahid"].toString() + ":" + worddetails["wordno"].toString(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,
                        )


                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 2.dp,
                                    horizontal = 4.dp
                                )

                                .clip(shape = CircleShape)

                                .padding(4.dp)
                        ) {
                            if (vbdetail["thulathi"] != null) {
                                val conjugation = vbdetail["wazan"].toString()
                                val root = vbdetail["root"].toString()
                                val mood = vbdetail["verbmood"].toString()
                                Button(
                                    modifier = Modifier
                                        .padding(20.dp),
                                    onClick = {

                                        navController.navigate(
                                            "conjugator/${conjugation}/${root}/${mood}"
                                        )
                                        /*     val intent = Intent(Intent.ACTION_VIEW)
                                             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                             val i = Intent(context, SurahComposeAct::class.java)
                                             i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                             context!!.startActivity(i)

         */
                                    }
                                ) {
                                    androidx.compose.material.Text(
                                        text = "Conjugate" + vbdetail["thulathi"].toString()
                                    )
                                }


                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 2.dp,
                                    horizontal = 4.dp
                                )

                                .clip(shape = CircleShape)

                                .padding(4.dp)
                        ) {
                            if (vbdetail["formnumber"] != null) {
                                val conjugation = vbdetail["form"].toString()
                                val root = vbdetail["root"].toString()
                                val mood = vbdetail["verbmood"].toString()
                                Button(
                                    modifier = Modifier
                                        .padding(20.dp),
                                    onClick = {
                                        navController.navigate(
                                            "conjugator/${conjugation}/${root}/${mood}"
                                        )
                                    }
                                ) {
                                    androidx.compose.material.Text(
                                        text = "Conjugate" + vbdetail["formnumber"].toString()
                                    )
                                }


                            }

                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 2.dp,
                                    horizontal = 4.dp
                                )

                                .clip(shape = CircleShape)

                                .padding(4.dp)


                        ) {
                            if (worddetails["arabicword"] != null) {
                                /*          CustomChip(
                                              selected = true,
                                              text = "ArabicWord" + worddetails["arabicword"].toString(),
                                              modifier = Modifier.padding(horizontal = 8.dp),


                                              )*/

                                val textChipRememberOneState = remember {
                                    mutableStateOf(false)
                                }
                                TextChip(
                                    isSelected = textChipRememberOneState.value,
                                    text = "ArabicWord" + worddetails["arabicword"].toString(),

                                    selectedColor = Color.DarkGray,
                                    onChecked = {
                                        textChipRememberOneState.value = it
                                    }
                                )


                            }
                            /*
                                     Chip(onClick = { *//*TODO*//* }) {



                            if (worddetails["arabicword"] != null) {
                                Text(
                                    text = "ArabicWord" + worddetails["arabicword"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                )
                            }
                        }*/

                        }

                        Row {

                            if (worddetails["PRON"] != null) {
                                androidx.compose.material.Text(
                                    text = worddetails["PRON"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )
                            }
                        }
                        Row {

                            if (worddetails["worddetails"] != null) {
                                androidx.compose.material.Text(
                                    text = worddetails["worddetails"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )
                            }
                        }




                        Row {
                            if (worddetails["noun"] != null) {
                                androidx.compose.material.Text(
                                    text = "Noun" + worddetails["noun"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )
                            }
                        }

                        Row {
                            if (worddetails["lemma"] != null || worddetails["lemma"]!!.isNotEmpty()) {
                                androidx.compose.material.Text(
                                    text = "Lemma" + worddetails["lemma"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )

                            }
                        }
                        Row {
                            if (worddetails["arabicword"] != null) {
                                androidx.compose.material.Text(
                                    text = "ArabicWord" + worddetails["arabicword"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )
                            }
                        }
                        Row {
                            if (worddetails["translation"] != null) {
                                androidx.compose.material.Text(
                                    text = "Translation" + worddetails["translation"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )
                            }
                        }



                        Row {
                            if (worddetails["root"] != null) {
                                androidx.compose.material.Text(
                                    text = "Root:" + worddetails["root"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )

                            }
                        }
                        Row {
                            if (worddetails["formnumber"] != null) {
                                androidx.compose.material.Text(
                                    text = worddetails["form"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )


                            }
                        }

                        //
                        Row {
                            if (vbdetail["mazeed"] != null) {
                                androidx.compose.material.Text(
                                    text = vbdetail["mazeed"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )


                            }
                        }
                        Row {
                            if (vbdetail["form"] != null) {
                                androidx.compose.material.Text(
                                    text = vbdetail["form"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )


                            }
                        }


                        Row {
                            if (vbdetail["verbmood"] != null) {
                                androidx.compose.material.Text(
                                    text = vbdetail["verbmood"].toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )


                            }
                        }

                        val vb: StringBuilder = StringBuilder()
                        vb.append("V-")
                        if (vbdetail["thulathi"] != null) {
                            vb.append(vbdetail["thulathi"])
                        }
                        if (vbdetail["png"] != null) {
                            vb.append(vbdetail["png"])
                        }
                        if (vbdetail["tense"] != null) {
                            vb.append(vbdetail["tense"])
                        }
                        if (vbdetail["voice"] != null) {
                            vb.append(vbdetail["voice"])
                        }
                        if (vbdetail["mood"] != null) {
                            vb.append(vbdetail["mood"])
                        }
                        if (vb.length > 2) {


                            Row {

                                androidx.compose.material.Text(
                                    text = vb.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )


                            }


                            //  holder.verbdetails.setTextSize(arabicFontsize);
                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)


                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp),

                    )



        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun WordALerts(


) {

    val openDialogCustom: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            }
        ) {
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                ) {

                    //.......................................................................
                    /*     Image(
                             painter = painterResource(id = R.drawable.ic_makkah_48),
                             contentDescription = null, // decorative
                             contentScale = ContentScale.Fit,
                             colorFilter  = ColorFilter.tint(
                                 color = Color.Green
                             ),
                             modifier = Modifier
                                 .padding(top = 35.dp)
                                 .height(70.dp)
                                 .fillMaxWidth(),

                             )*/

                    Column(modifier = Modifier.padding(16.dp)) {
                        androidx.compose.material3.Text(
                            text = "Get Updates",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        androidx.compose.material3.Text(
                            text = "Allow Permission to send you notifications when new art styles added.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        androidx.compose.material3.Text(
                            text = "Get Updates",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        androidx.compose.material3.Text(
                            text = "Allow Permission to send you notifications when new art styles added.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 2.dp,
                                horizontal = 4.dp
                            )

                            .clip(shape = CircleShape)

                            .padding(4.dp)
                    ) {

                        Button(
                            modifier = Modifier
                                .padding(20.dp),
                            onClick = {

                            }
                        ) {
                            androidx.compose.material.Text(
                                text = "vbdetails"
                            )
                        }


                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 2.dp,
                                horizontal = 4.dp
                            )

                            .clip(shape = CircleShape)

                            .padding(4.dp)


                    ) {

                        /*          CustomChip(
                                          selected = true,
                                          text = "ArabicWord" + worddetails["arabicword"].toString(),
                                          modifier = Modifier.padding(horizontal = 8.dp),


                                          )*/


                        Text(

                            text = "test",


                            )


                    }


                    //.......................................................................
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .background(Color.Magenta),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        androidx.compose.material3.TextButton(onClick = {
                            openDialogCustom.value = false
                        }) {


                            androidx.compose.material3.Text(
                                "Not Now",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                        androidx.compose.material3.TextButton(onClick = {
                            openDialogCustom.value = false
                        }) {
                            androidx.compose.material3.Text(
                                "Allow",
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )

                        }
                    }
                }
            }

        }

        }
    }
}