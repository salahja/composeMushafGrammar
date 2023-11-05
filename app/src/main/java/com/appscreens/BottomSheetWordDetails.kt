package com.appscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.corpusutility.AnnotatedQuranMorphologyDetails
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.utility.QuranGrammarApplication


//lateinit var worddetails: HashMap<String, SpannableStringBuilder?>
lateinit var worddetails: HashMap<String, AnnotatedString?>
@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun BottomSheetWordDetails(

    navController: NavHostController,
    mainViewModel: QuranVIewModel,
    chapterid: Int?,
    verseid: Int?,
    wordno: Int?
) {


    val corpusSurahWord = mainViewModel.getQuranCorpusWbw(chapterid!!, verseid!!, wordno!!).value

    var vbdetail = HashMap<String, String?>()
    val quran = mainViewModel.getsurahayahVerseslist(chapterid!!, verseid!!).value
    val corpusNounWord = mainViewModel.getNouncorpus(chapterid!!, verseid!!, wordno!!).value

    val verbCorpusRootWord =
        mainViewModel.getVerbRootBySurahAyahWord(chapterid!!, verseid!!, wordno!!).value


    /*  val am = NewQuranMorphologyDetails(
          corpusSurahWord!!,
          corpusNounWord as ArrayList<NounCorpus>?,
          verbCorpusRootWord as ArrayList<VerbCorpus>?,
          QuranGrammarApplication.context
      )*/

    val am = AnnotatedQuranMorphologyDetails(
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


    //Lets define bottomSheetScaffoldState which will hold the state of Scaffold
    val coroutineScope = rememberCoroutineScope()
    //  val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),

          sheetBackgroundColor = MaterialTheme.colorScheme.primaryContainer,

        sheetContent = {
            //Ui for bottom sheet
            Column(
                content = {

                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
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
                        if (worddetails["word"] != null) {
                            val annotatedString=      worddetails["word"]
                            if (annotatedString != null) {
                                /*         Text(
                                             text = annotatedString,
                                             modifier = Modifier
                                                 .fillMaxWidth(),
                                             textAlign = TextAlign.Center,
                                             fontWeight = FontWeight.Bold,
                                             fontSize = 21.sp,

                                             )*/

                                AssistChip(
                                    elevation = AssistChipDefaults.assistChipElevation(
                                        elevation = 16.dp
                                    ),
                                    modifier=Modifier.padding(10.dp),
                                    onClick = {

                                    },
                                    label = {
                                        Text( annotatedString)

                                    },

                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.Settings,
                                            contentDescription = "Localized description",
                                            Modifier.size(AssistChipDefaults.IconSize)
                                        )
                                    }
                                )











                            }
                        }
                    }


                    Row (modifier= Modifier.padding(10.dp)){

                        if (worddetails["PRON"] != null) {
                            Text(
                                text = worddetails["PRON"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )
                        }
                    }
                    Row (modifier= Modifier.padding(10.dp)){
                        val annotatedString = worddetails["worddetails"]
                        if (worddetails["worddetails"] != null) {
                            Text(
                                text = annotatedString!!,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Thin,
                                fontSize = 18.sp,

                                )
                        }
                    }




                    Row (modifier= Modifier.padding(10.dp)){
                        if (worddetails["noun"] != null) {
                            val annotatedString= worddetails["noun"];
                            if (annotatedString != null) {
                                Text(
                                    text =annotatedString,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,

                                    )
                            }
                        }
                    }
                  Row (modifier= Modifier.padding(10.dp)){
                      //  if (worddetails["lemma"] != null || worddetails["lemma"]!!.isNotEmpty()) {
                            Text(
                                text =  corpusSurahWord[0].wbw.en,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )

                    //    }
                    }

                    Row (modifier= Modifier.padding(10.dp)){
                        if (worddetails["lemma"] != null || worddetails["lemma"]!!.isNotEmpty()) {
                            Text(
                                text = "Lemma:  " + worddetails["lemma"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )

                        }
                    }

                    Row (modifier= Modifier.padding(10.dp)){
                        if (worddetails["translation"] != null) {
                            Text(
                                text = "Translation" + worddetails["translation"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )
                        }
                    }



                    Row (modifier= Modifier.padding(10.dp)){
                        if (worddetails["root"] != null) {
                            Text(
                                text = "Root:" + worddetails["root"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )

                        }
                    }
                    Row (modifier= Modifier.padding(10.dp)){
                        if (vbdetail["formnumber"] != null) {
                            Text(
                                text = vbdetail["formnumber"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )


                        }
                    }

                    //
                    Row (modifier= Modifier.padding(10.dp)){
                        if (vbdetail["mazeed"] != null) {
                            Text(
                                text = vbdetail["mazeed"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )


                        }
                    }
               /*     Row (modifier= Modifier.padding(10.dp)){
                        if (vbdetail["form"] != null) {
                            Text(
                                text = vbdetail["form"].toString(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,

                                )


                        }
                    }
*/

                    Row (modifier= Modifier.padding(10.dp)){
                        if (vbdetail["verbmood"] != null) {
                            Text(
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


                        Row (modifier= Modifier.padding(10.dp)){

                            Text(
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

                            AssistChip(
                                elevation = AssistChipDefaults.assistChipElevation(
                                    elevation = 16.dp
                                ),

                                onClick = {

                                    navController.navigate(
                                        "conjugator/${conjugation}/${root}/${mood}"
                                    )
                                },
                                label = { Text( "Conjugate" + vbdetail["thulathi"].toString())},
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Settings,
                                        contentDescription = "Localized description",
                                        Modifier.size(AssistChipDefaults.IconSize)
                                    )
                                }
                            )
                            /*       Button(
                                       modifier = Modifier
                                           .padding(20.dp),
                                       onClick = {

                                           navController.navigate(
                                               "conjugator/${conjugation}/${root}/${mood}"
                                           )
                                           *//*     val intent = Intent(Intent.ACTION_VIEW)
                                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                         val i = Intent(context, SurahComposeAct::class.java)
                                         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                         context!!.startActivity(i)

     *//*
                                }
                            ) {
                                Text(
                                    text = "Conjugate" + vbdetail["thulathi"].toString()
                                )
                            }*/


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
                            AssistChip(
                                elevation = AssistChipDefaults.assistChipElevation(
                                    elevation = 16.dp
                                ),

                                onClick = {

                                    navController.navigate(
                                        "conjugator/${conjugation}/${root}/${mood}"
                                    )
                                },
                                label = { Text("Conjugate" + vbdetail["formnumber"].toString()) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Settings,
                                        contentDescription = "Localized description",
                                        Modifier.size(AssistChipDefaults.IconSize)
                                    )
                                }
                            )



                            /*
                                    Button(
                                        modifier = Modifier
                                            .padding(20.dp),
                                        onClick = {
                                            navController.navigate(
                                                "conjugator/${conjugation}/${root}/${mood}"
                                            )
                                        }
                                    ) {
                                        Text(
                                            text = "Conjugate" + vbdetail["formnumber"].toString()
                                        )
                                    }
        */

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
                            val root = worddetails["root"]
                            AssistChip(
                                elevation = AssistChipDefaults.assistChipElevation(
                                    elevation = 16.dp
                                ),

                                onClick = {

                                    navController.navigate(
                                        "wordoccurance/${root}"
                                    )

                                },
                                label = { Text("Word Occurance" + worddetails["root"].toString()) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Settings,
                                        contentDescription = "Localized description",
                                        Modifier.size(AssistChipDefaults.IconSize)
                                    )
                                }
                            )





                            val textChipRememberOneState = remember {
                                mutableStateOf(false)
                            }
                            /*          Button(
                                          modifier = Modifier
                                              .padding(20.dp),
                                          onClick = {

                                              navController.navigate(
                                                  "wordoccurance/${root}"
                                              )

                                }
                            ) {
                                Text(
                                    text = "Word Occurance" + worddetails["root"].toString()
                                )
                            }
*/

                        }

                    }


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()


                    //     .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),

                )
        },
        sheetPeekHeight = 60.dp,

        ) {


        //Add button to open bottom sheet

    }
}
