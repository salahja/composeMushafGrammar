package com.viewmodels


import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.CorpusNounWbwOccurance
import com.example.mushafconsolidated.Entities.NounCorpusBreakup
import com.example.mushafconsolidated.Entities.Page
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Entities.VerbCorpusBreakup
import com.example.mushafconsolidated.Utils

import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import com.example.viewmodels.QuranPageArrays
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
class QuranPagesModel(
 //   application: Application,

    chapid: Int,
    themepref: BooleanPreferenceSettingValueState,


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
    private val _cards = MutableStateFlow(listOf<QuranPageArrays>())


    val cards: StateFlow<List<QuranPageArrays>> get() = _cards
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


        getZarf(chapid,themepref)


    }

     fun getZarf(chapid: Int, themepref: BooleanPreferenceSettingValueState) {
        viewModelScope.launch {
       val isdark=     themepref.value
             lateinit var fullQuranPages: ArrayList<Page>
            val testList = arrayListOf<QuranPageArrays>()
            val pages: MutableList<Page> = ArrayList()
            val quranEntities: List<QuranEntity?>? = util.getQuranbySurah(chapid)
            val firstpage = quranEntities?.get(0)!!.page
            lateinit var page: Page
            var ayahItems: List<QuranEntity?>?
            for (i in firstpage..quranEntities[quranEntities.size - 1]!!.page) {
                ayahItems = util    .getAyahsByPageQuran(chapid, i)
                if (ayahItems != null) {
                    if (ayahItems.isNotEmpty()) {
                        page = Page()
                        page.ayahItemsquran = ayahItems
                        //    page.se(ayahItems);
                        page.pageNum = i
                        page.juz = ayahItems[0]!!.juz
                        pages.add(page)
                    }
                }
            }
      val chapters=   util.getAllAnaChapters()
            fullQuranPages = ArrayList(pages)

            testList += QuranPageArrays(
                pages,

                chapters as List<ChaptersAnaEntity>
            )





            _cards.emit(testList)





            loading.value = false



        }
        //    closeDialog()
        loading.value = false
    }

}








