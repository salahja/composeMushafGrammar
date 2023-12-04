package com.viewmodels


import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.alorma.compose.settings.storage.preferences.IntPreferenceSettingValueState
import com.corpusutility.AnnotationUtility
import com.example.ComposeConstant
import com.example.viewmodels.ExpandableCardModelSpannableLists
import com.example.compose.QuranMorphologyDetails
import com.example.mushafconsolidated.Entities.CorpusNounWbwOccurance
import com.example.mushafconsolidated.Entities.CorpusVerbWbwOccurance
import com.example.mushafconsolidated.Entities.NounCorpusBreakup
import com.example.mushafconsolidated.Entities.VerbCorpusBreakup
import com.example.mushafconsolidated.Utils

import com.example.utility.QuranGrammarApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("SuspiciousIndentation")
class CardsViewModel(

    verbroot: String,
    nounroot: String,
    isharf: Boolean,
    selectTranslation: IntPreferenceSettingValueState?
) : ViewModel() {
    val builder = AlertDialog.Builder(QuranGrammarApplication.context!!)
    var listss: ArrayList<String> = ArrayList<String>()
    val dialog = builder.create()
    var open = MutableLiveData<Boolean>()
    private lateinit var util: Utils

    // var verbroot: String = "حمد"
    private lateinit var shared: SharedPreferences
    lateinit var lemma: String
    private val _cards = MutableStateFlow(listOf<ExpandableCardModelSpannableLists>())


    val roots: StateFlow<List<ExpandableCardModelSpannableLists>> get() = _cards
    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    var counter = 0;
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    private var nounCorpusArrayList: ArrayList<NounCorpusBreakup>? = null
    private var verbCorpusArray: ArrayList<VerbCorpusBreakup>? = null
    private var occurances: ArrayList<CorpusNounWbwOccurance>? = null
    val loading = mutableStateOf(true)

    init {
        shared = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.context!!)

        var job = Job()
        util = Utils(QuranGrammarApplication.context!!)
        if (isharf) {
            getZarf(nounroot, isharf,selectTranslation)
        } else
            getNounData(nounroot, verbroot,selectTranslation)


    }

    private fun getZarf(
        nounroot: String,
        isharf: Boolean,
        selectTranslation: IntPreferenceSettingValueState?
    ) {
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.Default) {
                val testList = arrayListOf<ExpandableCardModelSpannableLists>()
                occurances =
                    util.getnounoccuranceHarfNasbZarf(nounroot) as ArrayList<CorpusNounWbwOccurance>?
                nounCorpusArrayList = nounroot.trim()
                    .let { util.getNounBreakup(it) } as ArrayList<NounCorpusBreakup>?

                for (vers in occurances!!) {
                    //    alist.add("");
                    if (isharf) {
                        lemma = ""
                    }
                    //   lemma = vers.lemma!!
                    val lists: ArrayList<AnnotatedString> = ArrayList<AnnotatedString>()
                    var titlspan: String = ""
                    val sb = StringBuilder()
                    val annotatedVerse = AnnotationUtility.getAnnotedVerse(
                        vers.araone + vers.aratwo + vers.arathree + vers.arafour + vers.arafive,
                        vers.qurantext!!
                    )
                    sb.append(vers.surah).append(":").append(vers.ayah).append(":")
                        .append(vers.wordno).append("-").append(vers.en).append("-")
                    val ref = AnnotatedString(sb.toString())
                    val builder = AnnotatedString.Builder()
                    val source = ref
                    builder.append(source)
                    ref.length


                    val start = source.indexOf(sb.toString())
                    val end = start + sb.toString().length


                    val tagonecolor = ComposeConstant.particlespanDark
                    val tagonestyle = SpanStyle(
                        color = tagonecolor!!,
                        // textDecoration = TextDecoration.Underline
                    )
                    builder.addStyle(tagonestyle, start, end)


                    val istimeadverb =
                        vers.tagone.equals("T") || vers.tagtwo.equals("T") || vers.tagthree.equals("T") || vers.tagfour.equals(
                            "T"
                        )
                    val islocation =
                        vers.tagone.equals("LOC") || vers.tagtwo.equals("LOC") || vers.tagthree.equals(
                            "LOC"
                        ) || vers.tagfour.equals(
                            "LOC"
                        )
                    val isaccusaiveparticle =
                        vers.tagone.equals("ACC") || vers.tagtwo.equals("ACC") || vers.tagthree.equals(
                            "ACC"
                        ) || vers.tagfour.equals(
                            "ACC"
                        )



                    if (istimeadverb) {
                        titlspan = "Time Adverb"
                    }
                    if (islocation) {
                        titlspan = " accusative location adverb"
                    }
                    if (isaccusaiveparticle) {

                        titlspan = "accusative particle "
                    }

                    titlspan += vers.araone + vers.aratwo


                    //    if (trans != null) {
                    //      lists.add(trans)
                    //  }

                    val charSequence = TextUtils.concat(ref, "\n ", annotatedVerse)
                    val contentText = AnnotatedString(charSequence.toString())
                    lists.add(contentText)

                    testList += ExpandableCardModelSpannableLists(
                        id = counter++,
                        titlspan,
                        lemma,
                        lists
                    )
                }





                _cards.emit(testList)


            }
            //    closeDialog()
            loading.value = false
        }

    }

    private fun getNounData(
        nounroot: String,
        verbroot: String,
        selectTranslation: IntPreferenceSettingValueState?
    ) {
        val value = selectTranslation!!.value
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.Default) {
                val testList = arrayListOf<ExpandableCardModelSpannableLists>()

                nounCorpusArrayList = nounroot.trim()
                    .let { util.getNounBreakup(it) } as ArrayList<NounCorpusBreakup>?
                verbCorpusArray = verbroot.trim()
                    .let { util.getVerbBreakUp(it) } as ArrayList<VerbCorpusBreakup>?


                for (noun in nounCorpusArrayList!!) {
                    var nountitleStrBuilder = StringBuilder()

                    nountitleStrBuilder = stringBuilder(noun, nountitleStrBuilder)
                    lemma = noun.lemma_a.toString()
                    val verses: ArrayList<CorpusNounWbwOccurance> =
                        util.getNounOccuranceBreakVerses(noun.lemma_a!!)
                                as ArrayList<CorpusNounWbwOccurance>
                    val lists: ArrayList<AnnotatedString> = ArrayList<AnnotatedString>()


                    for (nounverse in verses) {
                        val nounverseBuilder = StringBuilder()

                        NounVerseBuilder(nounverse, nounverseBuilder,  lists,value)
                        val span = AnnotatedString(nounverseBuilder.toString())
                     //   val ref = AnnotatedString(sb.toString())
                        val builder = AnnotatedString.Builder()
                        val source = span
                        builder.append(source)
                        span.length


                        val start = source.indexOf(nounverseBuilder.toString())
                        val end = start + nounverseBuilder.toString().length


                        val tagonecolor = ComposeConstant.particlespanDark
                        val tagonestyle = SpanStyle(
                            color = tagonecolor!!,
                            // textDecoration = TextDecoration.Underline
                        )
                        builder.addStyle(tagonestyle, start, end)





                        lists.add(span)
                    }
                    testList += ExpandableCardModelSpannableLists(
                        id = counter++,
                        title = nountitleStrBuilder.toString(),
                        lemma,
                        lists
                    )

                }

                for (verbbreakup in verbCorpusArray!!) {
                    var verbtitlesbuilder = StringBuilder()

                    verbtitlesbuilder = stringBuilder1(verbbreakup, verbtitlesbuilder)


                    val verses: List<CorpusVerbWbwOccurance?>? = verbbreakup.lemma_a?.let {
                        util.getVerbOccuranceBreakVerses(
                            it
                        )
                    }

                    val lists: ArrayList<AnnotatedString> = ArrayList<AnnotatedString>()
                    lemma = verbbreakup.lemma_a.toString()
                    if (verses != null) {
                        for (ver in verses) {
                            val verbversBuilder = StringBuilder()


                            VerseVerseBuilder(verbversBuilder, ver,  selectTranslation!!.value)




                            lists.add(AnnotatedString(verbversBuilder.toString()))


                        }
                    }





                    testList += ExpandableCardModelSpannableLists(
                        id = counter++,
                        title = verbtitlesbuilder.toString(),
                        lemma,
                        lists
                    )

                }





                _cards.emit(testList)


            }
            //    closeDialog()
            loading.value = false
        }

    }


    private fun VerseVerseBuilder(
        verbversBuilder: StringBuilder,
        ver: CorpusVerbWbwOccurance?,
        which: Int?,
    ) {
        //     val s = ver?.araone + ver?.aratwo + ver.arathree + ver.arafour + ver.arafive
        val spannableVerses: AnnotatedString =
            AnnotationUtility.getAnnotedVerse(
                ver!!.araone + ver.aratwo + ver.arathree + ver.arafour + ver.arafive,
                ver.qurantext!!
            )

        verbversBuilder.append(ver.surah).append(":").append(ver.ayah)
            .append(":").append(ver.wordno).append("-")
            .append(ver.en).append("-").append("\n").append("\n")
        verbversBuilder.append(spannableVerses).append("\n").append("\n")

        when (which) {
            0 ->    verbversBuilder.append(ver.translation).append("\n").append("\n")
            1 -> verbversBuilder.append(ver.en_arberry).append("\n").append("\n")

            2 ->             verbversBuilder.append(ver.en_jalalayn).append("\n").append("\n")
        }
      //  lists.add(SpannableString.valueOf(verbversBuilder))
    }

    private fun stringBuilder1(
        verbbreakup: VerbCorpusBreakup,
        verbtitlesbuilder: StringBuilder,
    ): StringBuilder {
        var verbtitlesbuilder1 = verbtitlesbuilder
        if (verbbreakup.form == "I") {
            verbtitlesbuilder1 = StringBuilder()
            val mujarrad = java.lang.String.valueOf(
                QuranMorphologyDetails.getThulathiName(verbbreakup.thulathibab)
            )
            verbtitlesbuilder1.append(verbbreakup.count).append("-").append("times").append(" ")
                .append(verbbreakup.lemma_a).append(" ").append("occurs as the")
                .append(" ").append(mujarrad)
            //     expandVerbVerses[sb.toString()] = list
        } else {
            verbtitlesbuilder1 = StringBuilder()
            val s = verbbreakup.tense?.let { QuranMorphologyDetails.expandTags(it) }
            val s1 = verbbreakup.voice?.let { QuranMorphologyDetails.expandTags(it) }
            val mazeed = java.lang.String.valueOf(
                QuranMorphologyDetails.getFormName(verbbreakup.form)
            )
            verbtitlesbuilder1.append(verbbreakup.count).append("-").append("times").append(" ")
                .append(verbbreakup.lemma_a).append(" ").append("occurs as the")
                .append(" ").append(mazeed)
                .append(" ").append(s).append(" ").append(" ").append(s1)
            // expandVerbVerses[sb.toString()] = list
        }
        return verbtitlesbuilder1
    }

    private fun NounVerseBuilder(
        nounverse: CorpusNounWbwOccurance,
        nounverseBuilder: StringBuilder,

        lists: ArrayList<AnnotatedString>,
        value: Int,
    ) {
        val s =
            nounverse.araone + nounverse.aratwo + nounverse.arathree + nounverse.arafour + nounverse.arafive
        nounverseBuilder.append(nounverse.surah).append(":").append(nounverse.ayah)
            .append(":").append(nounverse.wordno).append("-")

            .append(nounverse.en).append("-").append("\n").append("\n")
        nounverseBuilder.append(nounverse.qurantext).append("\n").append("\n")
        when (value) {
            0 ->    nounverseBuilder.append(nounverse.translation).append("\n").append("\n")
            1 -> nounverseBuilder.append(nounverse.en_arberry).append("\n").append("\n")

            2 ->             nounverseBuilder.append(nounverse.en_jalalayn).append("\n").append("\n")
        }
        lists.add((AnnotatedString(nounverseBuilder.toString())))


    }

    private fun stringBuilder(
        noun: NounCorpusBreakup,
        nountitleStrBuilder: StringBuilder,
    ): StringBuilder {
        var nountitleStrBuilder1 = nountitleStrBuilder
        if (noun.form == "null") {
            nountitleStrBuilder1 = StringBuilder()
            val nounexpand = QuranMorphologyDetails.expandTags(noun.tag!!)
            var times = ""
            times = if (noun.count == 1) {
                "Once"
            } else {
                val count = noun.count
                val timess = count.toString()
                "$timess-times"
            }
            nountitleStrBuilder1.append(times).append(" ").append(noun.lemma_a).append(" ")
                .append("occurs as the").append(" ").append(nounexpand)

        } else {
            nountitleStrBuilder1 = StringBuilder()
            val s = QuranMorphologyDetails.expandTags(noun.propone + noun.proptwo)
            //  String s1 = QuranMorphologyDetails.expandTags(noun.getProptwo());
            val s2 = QuranMorphologyDetails.expandTags(noun.tag!!)
            var times = ""
            times = if (noun.count == 1) {
                "Once"
            } else {
                val count = noun.count
                val timess = count.toString()
                "$timess-times"
            }
            nountitleStrBuilder1.append(times).append(" ").append(noun.lemma_a).append(" ")
                .append("occurs as the").append(" ").append(noun.form)
                .append(" ")
            if (s != "null") {
                nountitleStrBuilder1.append(s).append(" ").append(" ")
            }
            nountitleStrBuilder1.append(s2)

        }
        return nountitleStrBuilder1
    }


    /*    private fun getFakeData() {
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val testList = arrayListOf<ExpandableCardModel>()
                    for (noun in nounCorpusArrayList!!) {
                        var sb = StringBuilder()
                        sb.append(noun.araword).append("occurs").append(":").append(noun.count)
                            .append("as").append(noun.tag)
                        sb.append(noun.surah).append(":").append(noun.ayah)
                        val verses: ArrayList<CorpusNounWbwOccurance> =
                            util.getNounOccuranceBreakVerses(noun.lemma_a!!)
                             as ArrayList<CorpusNounWbwOccurance>
                        val vlist: ArrayList<String> =
                            ArrayList()

                        for(vers in verses){
                            vlist.add(vers.qurantext!!)

                        }



                        testList += ExpandableCardModel(id = noun.id, title = sb.toString(),vlist)
                        _cards.emit(testList)
                    }

                    *//* repeat(20) { testList += ExpandableCardModel(id = it, title = "Card $it") }
                 _cards.emit(testList)*//*
            }
        }
    }*/

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }

    fun startThread() {
        TODO("Not yet implemented")
    }
}
