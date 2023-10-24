package com.example.mushafconsolidated.quranrepo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.compose.ExpandableCardModelSpannableLists
import com.example.mushafconsolidated.Entities.BadalErabNotesEnt
import com.example.mushafconsolidated.Entities.BookMarks
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.GrammarRules
import com.example.mushafconsolidated.Entities.HalEnt
import com.example.mushafconsolidated.Entities.LiajlihiEnt
import com.example.mushafconsolidated.Entities.MafoolBihi
import com.example.mushafconsolidated.Entities.MafoolMutlaqEnt
import com.example.mushafconsolidated.Entities.NewKanaEntity
import com.example.mushafconsolidated.Entities.NewMudhafEntity
import com.example.mushafconsolidated.Entities.NewNasbEntity
import com.example.mushafconsolidated.Entities.NewShartEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Entities.SifaEntity
import com.example.mushafconsolidated.Entities.TameezEnt
import com.example.mushafconsolidated.Entities.VerbCorpus
import com.example.mushafconsolidated.Entities.hanslexicon
import com.example.mushafconsolidated.Entities.lanerootdictionary
import com.example.mushafconsolidated.Entities.lughat
import com.example.mushafconsolidated.Entities.surahsummary
import com.example.mushafconsolidated.Entities.wbwentity
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.Juz
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import database.entity.kov
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sj.hisnul.entity.hcategoryEnt
import java.util.ArrayList
import java.util.LinkedHashMap

class FlowQuranVIewModel(
    private val newrepository: QuranRepository = QuranGraph.repository,
) : ViewModel() {

    private val _quranverses = MutableStateFlow(listOf<QuranEntity>())
    private val _chapters = MutableStateFlow(listOf<ChaptersAnaEntity>())


    private var chapters: LiveData<List<ChaptersAnaEntity>> = MutableLiveData()

    val util = Utils(QuranGrammarApplication.context)

  //  getQuranCorpusWbwBysurah
  private val _cards = MutableStateFlow(listOf<GrammarRules>())
    val loading = mutableStateOf(true)
init {
    getData()
}

    private fun getData() {
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.Default) {
                var asFlow = newrepository.chapters.asFlow()
            }

        }

    }


    fun getAllChapters(): LiveData<List<ChaptersAnaEntity>> {


        viewModelScope.launch {
            chapters = newrepository.chapters
        }


        return chapters
    }










    /*

          fun getitall(
               corpusSurahWord: List<QuranCorpusWbw>?,
               newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
               chapid: Int,
               quranbySurah: List<QuranEntity>?
           ) {
               val corpus = CorpusUtilityorig
               corpusSurahWord = getQuranCorpusWbwbysurah(chapid).value
               newnewadapterlist = corpus.composeWBWCollection(quranbySurah, corpusSurahWord)
                 corpus.setMudhafs(newnewadapterlist,chapid)



           }
     */



    /*    fun update(fav: Int, id: Int) = viewModelScope.launch {

            newrepository.updateFav(fav, id)
        }

        fun DuaItembyId(cat: String): LiveData<List<hduadetailsEnt>> {

            viewModelScope.launch {
                allduaitem = newrepository.getDuaitems(cat)
            }


            return allduaitem
        }*/


}

