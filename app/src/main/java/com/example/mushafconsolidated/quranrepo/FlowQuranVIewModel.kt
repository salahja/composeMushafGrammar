package com.example.mushafconsolidated.quranrepo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.GrammarRules
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Utils
import com.example.utility.QuranGrammarApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

