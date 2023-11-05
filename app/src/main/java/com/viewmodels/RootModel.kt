package com.viewmodels


import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.mushafconsolidated.Entities.CorpusNounWbwOccurance
import com.example.mushafconsolidated.Entities.NounCorpusBreakup
import com.example.mushafconsolidated.Entities.VerbCorpusBreakup
import com.example.mushafconsolidated.Utils

import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
class RootModel(
 //   application: Application,

    root: String,

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
    private val _cards = MutableStateFlow(listOf<AllRootWords>())


    val verbroot: StateFlow<List<AllRootWords>> get() = _cards
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    var counter = 0;
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    private var nounCorpusArrayList: ArrayList<NounCorpusBreakup>? = null
    private var verbCorpusArray: ArrayList<VerbCorpusBreakup>? = null
    private var occurances: ArrayList<CorpusNounWbwOccurance>? = null
    val loading = mutableStateOf(false)

    init {

        shared = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.context!!)
        var job = Job()
        util = Utils(QuranGrammarApplication.context!!)


        getVerbRoot(root)


    }

     fun getVerbRoot(root: String) {
        viewModelScope.launch {
            loading.value = true
            //delay(2000)
            val testList = arrayListOf<AllRootWords>()



                val verbrootlist =   util.getRootVerbDetails(root)

                              val corpusrootlist=             util.getQuranCorpusWbwbyrootNouns(root)
         //   val corpusrootlist=             util.getQuranCorpusWbwbyroot(root)

          val nouncorpus=util.getQuranNounsbyroot(root)
             val nounrootlist=            util.getRootNounDetails(root)

            testList += AllRootWords(
                verbrootlist!!,
                corpusrootlist,

                nounrootlist!!,
                nouncorpus!!

            )






                _cards.emit(testList)






          loading.value = false



        }
        //    closeDialog()
   //     loading.value = false
    }

}







