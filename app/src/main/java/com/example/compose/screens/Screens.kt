package com.example.compose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.Constant
import com.example.compose.NavigationItem
import com.example.compose.NewQuranMorphologyDetails
import com.example.compose.TextChip

import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.utility.QuranGrammarApplication

@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun BottomSheetWordDetails(
    navController: NavHostController,
    mainViewModel: QuranVIewModel,
    chapterid: Int,
    verseid: Int,
    wordno: Int?
) {
    val utils = Utils(QuranGrammarApplication.context)
  //  val getwordroot = utils.getwordroot(chapterid,verseid,wordno)
   // getwordroot[0].wordno
    val corpusSurahWord = mainViewModel.getQuranCorpusWbw(chapterid!!, verseid!!, wordno!!
    ).value

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
    var worddetails = am.wordDetails
    // wordbdetail = am.wordDetails
    if (verbCorpusRootWord != null) {
        if (verbCorpusRootWord.isNotEmpty() && verbCorpusRootWord[0].tag.equals("V")) {
            vbdetail = am.verbDetails
            //  isVerb = true
        }
    }


    //Lets define bottomSheetScaffoldState which will hold the state of Scaffold

    //   val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
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
                        if (vbdetail["thulathi"] != null) {
                            val conjugation = vbdetail["wazan"].toString()
                            val root = vbdetail["root"].toString()
                            var mood: String
                            if(vbdetail["emph"]!=null){
                             mood="Emphasized"

                            }else {
                                 mood = vbdetail["verbmood"].toString()

                            }


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
                                Text(
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
                            var mood = vbdetail["verbmood"].toString()
                            if(vbdetail["emph"]!=null){
                                mood="Emphasized"

                            }else {
                                 mood = vbdetail["verbmood"].toString()

                            }


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
                    Row {

                        if (worddetails["worddetails"] != null) {
                            Text(
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
                            Text(
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
                            Text(
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
                            Text(
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



                    Row {
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
                    Row {
                        if (worddetails["formnumber"] != null) {
                            Text(
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
                    Row {
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


                    Row {
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


                        Row {

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


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)


                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp),

                )
        },
        sheetPeekHeight = 0.dp,

        ) {
        Modifier.padding(40.dp)

        //Add button to open bottom sheet

    }
}


@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    // BottomNavigationBar()
}

