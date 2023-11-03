package com.appscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.activities.NavigationType
import com.corpusutility.AnnotationUtility
import com.corpusutility.NounMorphologyDetails
import com.example.compose.WordOccuranceLoading
import com.example.compose.theme.showProgress
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.utility.QuranGrammarApplication
import com.viewmodels.RootModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NounRootScreens(
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
    val nounroots = roots[0].corpusSurahWordlist
    val chapters = roots[0].chapterlist
    val nouns = roots[0].nounlist
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


                items(nounroots.size) { index ->
                    //          indexval=index
                    NounGrid(nounroots[index], navController, chapters, nouns[index])
                }


        }
    }
}

@Composable
fun NounGrid(
    quranCorpusWbw: QuranCorpusWbw,
    navController: NavHostController,
    chapters: List<ChaptersAnaEntity?>,
    nounCorpus: NounCorpus?,

    ) {
    val sb = StringBuilder()
    val spannableString = AnnotationUtility.AnnotatedSetWordSpanTag(
        quranCorpusWbw.corpus.tagone!!,
        quranCorpusWbw.corpus.tagtwo!!,
        quranCorpusWbw.corpus.tagthree!!,
        quranCorpusWbw.corpus.tagfour!!,
        quranCorpusWbw.corpus.tagfive!!,
        quranCorpusWbw.corpus.araone!!,
        quranCorpusWbw.corpus.aratwo!!,
        quranCorpusWbw.corpus.arathree!!,
        quranCorpusWbw.corpus.arafour!!,
        quranCorpusWbw.corpus.arafive!!
    )
    val nm = NounMorphologyDetails(quranCorpusWbw, nounCorpus)
    var wordbdetail = HashMap<String, AnnotatedString>()
    wordbdetail = nm.wordDetails
    val arabicword: AnnotatedString = wordbdetail["arabicword"]!!
    //  sb.append(lughat.getSurah()).append("   ").append(lughat.getNamearabic()).append(lughat.getAyah()).append(" ").append(lughat.getArabic());
    sb.append(quranCorpusWbw.corpus.ayah).append("  ")
        .append(chapters.get(quranCorpusWbw.corpus.surah)!!.namearabic).append("   ")
        .append(quranCorpusWbw.corpus.surah).append(" ").append(quranCorpusWbw.wbw.en)

    Card(

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
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

            ClickableText(
                text = arabicword,

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                )
            )
            Text(
                text = wordbdetail["noun"].toString(),
            )


        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            ClickableText(
                text = AnnotatedString(chapters.get(quranCorpusWbw.corpus.surah)!!.namearabic.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                ))
            Text(
                text = "Ayah  " + quranCorpusWbw.corpus.ayah.toString(),
            )

            // indexval = surahModelList!!.chapterid
            ClickableText(
                text = spannableString,

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                ))
            ClickableText(
                text = AnnotatedString(quranCorpusWbw.wbw.en.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Cursive
                ))


        }


    }
}
