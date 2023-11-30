package com.appscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.activities.NavigationType
import com.codelab.basics.ui.theme.indopak
import com.corpusutility.AnnotationUtility
import com.corpusutility.NounMorphologyDetails
import com.example.compose.WordOccuranceLoading
import com.example.compose.theme.showProgress
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.RootNounDetails
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

    val nounCorpus = roots[0].nouncorpus
    val nounCorpusWbw = roots[0].corpusSurahWordlist

    val nouns = roots[0].nounrootlist
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


            items(nounCorpusWbw.size) { index ->
                //          indexval=index
                NounGrid(nouns!![index], navController,  nounCorpusWbw[index], nounCorpus[index])
            }


        }
    }
}

@Composable
fun NounGrid(
    nounRootdetails: RootNounDetails?,
    navController: NavHostController,

    quranCorpusWbw: QuranCorpusWbw,
    nounCorpus1: NounCorpus?,

    ) {
    val sb = StringBuilder()
    val annotatedString = AnnotationUtility.AnnotatedSetWordSpanTag(
        nounRootdetails!!.tagone!!,
         nounRootdetails!!.tagtwo!!,
         nounRootdetails!!.tagthree!!,
         nounRootdetails!!.tagfour!!,
         nounRootdetails!!.tagfive!!,
         nounRootdetails!!.araone!!,
         nounRootdetails!!.aratwo!!,
         nounRootdetails!!.arathree!!,
         nounRootdetails!!.arafour!!,
         nounRootdetails!!.arafive!!
    )
    val nm = NounMorphologyDetails(quranCorpusWbw, nounCorpus1)
    var wordbdetail = HashMap<String, AnnotatedString>()
    wordbdetail = nm.wordDetails
    val arabicword: AnnotatedString = wordbdetail["arabicword"]!!
    //  sb.append(lughat.getSurah()).append("   ").append(lughat.getNamearabic()).append(lughat.getAyah()).append(" ").append(lughat.getArabic());
    sb.append( nounRootdetails!!.ayah).append("  ")
        .append( nounRootdetails!!.namearabic).append("   ")
        .append( nounRootdetails!!.surah).append(" ").append(nounRootdetails.en)
    val sbs = StringBuilder()
    sbs.append( nounRootdetails!!.surah.toString()).append(":")

        .append(": " +  nounRootdetails!!.ayah.toString())
        .append(":" +  nounRootdetails!!.wordno.toString()).append(
            "    "
        ).append(nounRootdetails.en)
    val start = nounRootdetails!!.qurantext!!.indexOf(nounRootdetails.arabic!!)
    val builder = AnnotatedString.Builder()
    builder.append(nounRootdetails.qurantext)
    val tagonestyle = SpanStyle(

        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
    )
    if (start != -1) {
        builder.addStyle(tagonestyle, start, start + nounRootdetails.arabic!!.length)
    }
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
                text = annotatedString,

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = indopak
                )
            )


        }



        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        )


        {
            ClickableText(
                text = AnnotatedString(nounRootdetails!!.namearabic!!),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = indopak
                )
            )



            ClickableText(
                text = AnnotatedString(sbs.toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
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


            ClickableText(
                text = AnnotatedString(wordbdetail["noun"].toString()),

                onClick = {


                }, style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
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
                text = AnnotatedString("Translation :" + nounRootdetails.translation!!)


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

       /*     nounRootdetails.tafsir_kathir?.let {
                Text(

                    text = it,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.burntamber)
                )
            }*/
           ExpandableText(

                text = AnnotatedString("Tafsir Ibne Kathir:" + nounRootdetails.tafsir_kathir)


            )
            Spacer(modifier = Modifier.width(20.dp))


        }
    }
}
