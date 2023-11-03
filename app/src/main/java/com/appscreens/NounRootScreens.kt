package com.appscreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.activities.NavigationType
import com.example.compose.WordOccuranceLoading
import com.example.compose.theme.showProgress
import com.example.mushafconsolidated.Utils
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