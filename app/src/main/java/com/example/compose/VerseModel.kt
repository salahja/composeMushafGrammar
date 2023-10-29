package com.example.compose


import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.CorpusNounWbwOccurance
import com.example.mushafconsolidated.Entities.CorpusVerbWbwOccurance
import com.example.mushafconsolidated.Entities.NounCorpusBreakup
import com.example.mushafconsolidated.Entities.VerbCorpusBreakup
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.NewQuranCorpusWbw

import com.example.utility.CorpusUtilityorig
import com.example.utility.CorpusUtilityorig.Companion.getSpannableVerses
import com.example.utility.QuranGrammarApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.LinkedHashMap


@SuppressLint("SuspiciousIndentation")
class VerseModel(
 //   application: Application,

    chapid: Int,

    ) : ViewModel() {

//private val argument = checkNotNull(savedStateHandle.get<[type]>([argument name]))




    val builder = AlertDialog.Builder(QuranGrammarApplication.context!!)
    var listss: ArrayList<String> = ArrayList<String>()
    val dialog = builder.create()
    var open = MutableLiveData<Boolean>()
    private lateinit var util: Utils
    private lateinit var corpus: CorpusUtilityorig

    // var verbroot: String = "حمد"
    private lateinit var shared: SharedPreferences
    lateinit var lemma: String
    private val _cards = MutableStateFlow(listOf<QuranArrays>())


    val cards: StateFlow<List<QuranArrays>> get() = _cards
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    var counter = 0;
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    private var nounCorpusArrayList: ArrayList<NounCorpusBreakup>? = null
    private var verbCorpusArray: ArrayList<VerbCorpusBreakup>? = null
    private var occurances: ArrayList<CorpusNounWbwOccurance>? = null
    val loading = mutableStateOf(true)

    init {
        loading.value = true
        shared = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.context!!)
        var job = Job()
        util = Utils(QuranGrammarApplication.context!!)


        getZarf(chapid)


    }

     fun getZarf(chapid: Int) {
        viewModelScope.launch {


                val testList = arrayListOf<QuranArrays>()
                val corpus = CorpusUtilityorig

                val quranbySurah = util.getQuranbySurah(chapid)
                val chapterlist = util.getAllAnaChapters()
                // quranModel.getchapters().collectAsState()
                val corpusSurahWord = util.getQuranCorpusWbwbysurah(chapid)

                val hashlist = corpus.composeWBWCollection(quranbySurah, corpusSurahWord)
                //  quranModel.setspans(newnewadapterlist, chapid)


                testList += QuranArrays(
                    hashlist,
                    corpusSurahWord,
                    quranbySurah!!,
                    chapterlist as List<ChaptersAnaEntity>
                )



                    corpus.setMudhafss(hashlist, chapid)
                    corpus.setSifa(hashlist, chapid)
                    corpus.setShart(hashlist,chapid)






                _cards.emit(testList)





            loading.value = false



        }
        //    closeDialog()
        loading.value = false
    }

}







