package com.viewmodels

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.widget.ExpandableListView
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.compose.settings.storage.preferences.IntPreferenceSettingValueState
import com.example.ComposeConstant.mudhafspanLight
import com.example.ComposeConstant.mudhafspansDark
import com.example.mushafconsolidated.Entities.NewMudhafEntity
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Entities.wbwentity
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.mushafconsolidated.model.SarfSagheerPOJO
import com.example.utility.QuranGrammarApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpandableVerseViewModel(chapterid:Int,verseid:Int,thememode: Boolean, wbwchoice: IntPreferenceSettingValueState) : ViewModel(){
    private val itemsList = MutableStateFlow(listOf<VerseAnalysisModel>())
    private var quran: List<QuranEntity>? = null
    private var whichwbw: String? = null

  //  val model: QuranVIewModel by viewModels()
    private var dark: Boolean = false

    private var corpusSurahWord: List<QuranCorpusWbw>? = null


    private var isverbconjugaton = false
    private var participles = false
    lateinit var expandableListView: ExpandableListView
    var expandableListTitle: List<String>? = null
    var expandableListDetail: LinkedHashMap<String, List<AnnotatedString>>? = null
    private var kanaExpandableListDetail: List<SpannableString>? = null
    var vbdetail = HashMap<String, String>()
    var wordbdetail: HashMap<String, SpannableStringBuilder>? = null
    private var showGrammarFragments = false
    private var ThulathiMazeedConjugatonList: ArrayList<ArrayList<*>>? = null
    var sarf: SarfSagheerPOJO? = null
    private var dialog: AlertDialog? = null




    data class VerseAnalysisModel(val grammarrule: String, val result:List<AnnotatedString>)
    val items: StateFlow<List<VerseAnalysisModel>> get() = itemsList

    private val itemIdsList = MutableStateFlow(listOf<Int>())
    val itemIds: StateFlow<List<Int>> get() = itemIdsList


    init {
        getData(thememode,wbwchoice,chapterid,verseid)
    }

    private fun getData(
        thememode: Boolean,
        wbwchoice: IntPreferenceSettingValueState,
        chapterid: Int,
        verseid: Int
    ) {
        val utils = Utils(QuranGrammarApplication.context)
        quran= utils.getsurahayahVerses(chapterid,verseid) as List<QuranEntity>?
        utils.getQuranCorpusWbwSurhAyah(chapterid,verseid,1)

        corpusSurahWord=     utils.getQuranCorpusWbwSurhAyah(chapterid,verseid,1)
        val list = java.util.LinkedHashMap<String, List<AnnotatedString>>()
        val shartarray: MutableList<AnnotatedString> =  ArrayList()
      //  newSetShart(shartarray)
        val harfnasbarray: MutableList<AnnotatedString> =  ArrayList()
   /*     setNewNasb(harfnasbarray)
        val mausoofsifaarray: MutableList<AnnotatedString> =  ArrayList()
        setMausoof(mausoofsifaarray)


        val kanaarray: MutableList<AnnotatedString> =  ArrayList()
        newsetKana(kanaarray)*/

        val mudhafarray: MutableList<AnnotatedString> =  ArrayList()
/*        expandableListDetail["Verse"] = verse
        expandableListDetail["Translation"] = translation
        expandableListDetail["Conditional/جملة شرطية\""] = shartarray
        expandableListDetail["Accusative/ "] = harfnasbarray
        expandableListDetail["Verb kāna/كان واخواتها"] = kanaarray
        expandableListDetail["Adjectival Phrases/مرکب توصیفی"] = mausoofsifaarray
        expandableListDetail["Possessive/إضافَة"] = mudhafarray*/
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val testList = arrayListOf<VerseAnalysisModel>()
                val mudhafarray: MutableList<AnnotatedString> =  ArrayList()
                setMudhaf(mudhafarray,thememode,wbwchoice,chapterid,verseid)
                val verse: MutableList<AnnotatedString> =  ArrayList()
                val translation: MutableList<AnnotatedString> =  ArrayList()
                verse.add(
                    AnnotatedString(
                          ":-" + quran!![0].qurantext
                    )
                )
                translation.add(AnnotatedString(quran!![0].translation))


                testList += VerseAnalysisModel(
                   "verse",
                    verse!!
                )

                testList += VerseAnalysisModel(
                    "Translation",
                    translation!!
                )



                itemsList.emit(testList)
                testList += VerseAnalysisModel(
                    "mudhaf",
                    mudhafarray!!





                )


                itemsList.emit(testList)



            }
        }
    }


/*

    private fun newsetKana(kanaarray: MutableList<AnnotatedString>) {
        TODO("Not yet implemented")
    }


    private fun setMausoof(mausoofsifaarray: MutableList<AnnotatedString>) {
        TODO("Not yet implemented")
    }

    private fun setNewNasb(harfnasbarray: List<AnnotatedString>) {
        TODO("Not yet implemented")
    }
*/

    private fun setMudhaf(
        mudhafarray: MutableList<AnnotatedString>,
        thememode: Boolean,
        whichwbw: IntPreferenceSettingValueState,
        chapterid: Int,
        verseid: Int
    ) {
        val utils = Utils(QuranGrammarApplication.context)
        //   ArrayList<MudhafEntity> mudhafSurahAyah = utils.getMudhafSurahAyah(chapterid, ayanumber);
        val mudhafSurahAyah: List<NewMudhafEntity>? =
            utils.getMudhafSurahAyahNew(chapterid, verseid)
        var tagcolor: Color?=null
        if(thememode){
            tagcolor= mudhafspansDark
        }else{
            tagcolor= mudhafspanLight

        }
        if (mudhafSurahAyah != null) {
            for (mudhafEntity in mudhafSurahAyah) {
                val builder = AnnotatedString.Builder()
                val quranverses: String = quran!![0].qurantext
                val source = quranverses
                val start =  mudhafEntity.startindex
                val end =     mudhafEntity.endindex
             //   builder.append(source)
                val tagonestyle = SpanStyle(
                    color = tagcolor!!,
                    // textDecoration = TextDecoration.Underline
                )
                builder.addStyle(tagonestyle, start, end)


                val sequence = quranverses.subSequence(mudhafEntity.startindex, mudhafEntity.endindex)
                val str = sequence.toString()
                builder.append(str)

                builder.addStyle(tagonestyle, start, end)


            //    mudhafarray.add(AnnotatedString(sequence.toString()) )
                mudhafarray.add(builder.toAnnotatedString())
                val sb = StringBuilder()
                val wordfrom = mudhafEntity.wordfrom
                val wordto = mudhafEntity.wordto
                val strings = sequence.toString().split("\\s".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val ssb = StringBuilder()
                if (strings.size == 2) {
                    val list: List<wbwentity>? = utils.getwbwQuranbTranslation(
                        corpusSurahWord!![0].corpus.surah,
                        corpusSurahWord!![0].corpus.ayah,
                        wordfrom,
                        wordto
                    )
                    if (list != null) {
                        for (w in list) {
                            StringBuilder()
                            val temp: StringBuilder = getSelectedTranslation(w)
                            ssb.append(temp).append(" ")
                        }
                    }
                    mudhafarray.add(AnnotatedString(ssb.toString()))
                } else {
                    val list =       utils.getwbwQuranbTranslation(
                        corpusSurahWord!![0].corpus.surah,
                        corpusSurahWord!![0].corpus.ayah,
                        wordto,
                        wordto
                    )
                    if(whichwbw.value==0)
                    when (whichwbw.value) {
                        0 -> sb.append( list!!.get(0).en).append(".......")
                        1 -> sb.append(list!![0].ur).append(".......")
                        2 -> sb.append(list!![0].bn).append(".......")
                        3 -> sb.append(list!![0].id).append(".......")
                    }
                    mudhafarray.add(AnnotatedString(sb.toString()))
                }
            }
        }
    }

    private fun getSelectedTranslation(tr: wbwentity): StringBuilder {
        val sb = StringBuilder()
        when (whichwbw) {
            "en" -> sb.append(tr.en)
            "ur" -> sb.append(tr.ur)
            "bn" -> sb.append(tr.bn)
            "id" -> sb.append(tr.id)
        }
        sb.append(" ")
        return sb
    }



    /*    private fun newSetShart(shartarray: MutableList<AnnotatedString>) {
            val utils = Utils(QuranGrammarApplication.context!!)
            val quranverses: String = quran!![0].qurantext
            val shart: List<NewShartEntity>? =
                utils.getShartSurahAyahNew(chapterid, ayanumber)
            // String quranverses = corpusSurahWord!!.get(0).corpus.getQurantext();
            var sb: StringBuilder
            val sbjawab = StringBuilder()

            if (shart != null) {
                for (shartEntity in shart) {
                    var harfofverse: String
                    var shartofverse: String
                    var jawabofverrse: String
                    sb = StringBuilder()
                    val indexstart = shartEntity.indexstart
                    val indexend = shartEntity.indexend
                    val shartindexstart = shartEntity.shartindexstart
                    val shartindexend = shartEntity.shartindexend
                    val jawabstart = shartEntity.jawabshartindexstart
                    val jawabend = shartEntity.jawabshartindexend
                    val harfword = shartEntity.harfwordno
                    val shartSword = shartEntity.shartstatwordno
                    val shartEword = shartEntity.shartendwordno
                    val jawbSword = shartEntity.jawabstartwordno
                    val jawabEword = shartEntity.jawabendwordno
                    harfofverse = quranverses.substring(indexstart, indexend)
                    shartofverse = quranverses.substring(shartindexstart, shartindexend)
                    jawabofverrse = quranverses.substring(jawabstart, jawabend)
                    val isharfb = indexstart >= 0 && indexend > 0
                    val isshart = shartindexstart >= 0 && shartindexend > 0
                    val isjawab = jawabstart >= 0 && jawabend > 0
                    val a = isharfb && isshart && isjawab
                    val b = isharfb && isshart
                    var harfspannble: SpannableString
                    var shartspoannable: SpannableString
                    var jawabshartspannable: SpannableString
                    if (dark) {
                        Constant.harfshartspanDark = ForegroundColorSpan(Constant.GOLD)
                        Constant.shartspanDark = ForegroundColorSpan(Constant.ORANGE400)
                        Constant.jawabshartspanDark = ForegroundColorSpan(Color.CYAN)
                    } else {
                        Constant.harfshartspanDark = ForegroundColorSpan(Constant.FORESTGREEN)
                        Constant.shartspanDark = ForegroundColorSpan(Constant.KASHMIRIGREEN)
                        Constant.jawabshartspanDark = ForegroundColorSpan(Constant.WHOTPINK)
                    }
                    if (a) {
                        harfspannble = SpannableString(harfofverse)
                        shartspoannable = SpannableString(shartofverse)
                        jawabshartspannable = SpannableString(jawabofverrse)
                        if (dark) {
                            //    harfshartspanDark = new ForegroundColorSpan(MIDNIGHTBLUE);
                            Constant.shartspanDark = ForegroundColorSpan(Constant.ORANGE400)
                            Constant.jawabshartspanDark = ForegroundColorSpan(Color.CYAN)
                        } else {
                            //   harfshartspanDark = new ForegroundColorSpan(FORESTGREEN);
                            Constant.shartspanDark = ForegroundColorSpan(Constant.GREENDARK)
                            Constant.jawabshartspanDark = ForegroundColorSpan(Constant.WHOTPINK)
                        }
                        harfspannble.setSpan(
                            Constant.harfshartspanDark,
                            0,
                            harfofverse.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        shartspoannable.setSpan(
                            Constant.shartspanDark,
                            0,
                            shartofverse.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        jawabshartspannable.setSpan(
                            Constant.jawabshartspanDark,
                            0,
                            jawabofverrse.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        val charSequence =
                            TextUtils.concat(harfspannble, " ", shartspoannable, " ", jawabshartspannable)
                        shartarray.add(SpannableString.valueOf(charSequence))
                        val connected = jawabstart - shartindexend
                        if (connected == 1) {
                            val list: List<wbwentity>? = utils.getwbwQuranbTranslation(
                                corpusSurahWord!![0].corpus.surah,
                                corpusSurahWord!![0].corpus.ayah, harfword, jawabEword
                            )
                            if (list != null) {
                                for (w in list) {
                                    val temp: StringBuilder = getSelectedTranslation(w)
                                    sb.append(temp).append(" ")
                                }
                            }
                            //    trstr1 = getFragmentTranslations(quranverses, sb, charSequence, false);
                            shartarray.add(SpannableString.valueOf(sb.toString()))
                        } else {
                            val wbwayah: List<wbwentity>? = utils.getwbwQuranBySurahAyah(
                                corpusSurahWord!![0].corpus.surah,
                                corpusSurahWord!![0].corpus.ayah
                            )
                            if (wbwayah != null) {
                                for (w in wbwayah) {
                                    val temp: StringBuilder = getSelectedTranslation(w)
                                    if (w.wordno == harfword) {
                                        sb.append(temp).append(" ")
                                    } else if (w.wordno in shartSword..shartEword) {
                                        sb.append(temp).append(" ")
                                    } else if (w.wordno in jawbSword..jawabEword) {
                                        //     sb. append("... ");
                                        sbjawab.append(temp).append(" ")
                                    }
                                }
                            }
                            sb.append(".....")
                            sb.append(sbjawab)
                            shartarray.add(SpannableString.valueOf(sb.toString()))
                        }
                    } else if (b) {
                        harfspannble = SpannableString(harfofverse)
                        shartspoannable = SpannableString(shartofverse)
                        harfspannble.setSpan(
                            Constant.harfshartspanDark,
                            0,
                            harfofverse.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        shartspoannable.setSpan(
                            Constant.shartspanDark,
                            0,
                            shartofverse.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        val charSequence = TextUtils.concat(harfspannble, " ", shartspoannable)
                        shartarray.add(SpannableString.valueOf(charSequence))
                        //    shartarray.add(trstr);
                        if (shartindexstart - indexend == 1) {
                            val harfnshart: List<wbwentity>? = utils.getwbwQuranbTranslation(
                                corpusSurahWord!![0].corpus.surah,
                                corpusSurahWord!![0].corpus.ayah,
                                harfword,
                                shartEword
                            )
                            if (harfnshart != null) {
                                for (w in harfnshart) {
                                    StringBuilder()
                                    val temp: StringBuilder = getSelectedTranslation(w)
                                    getSelectedTranslation(w)
                                    sb.append(temp).append(" ")
                                }
                            }
                        } else {
                            val wbwayah: List<wbwentity>? = utils.getwbwQuranBySurahAyah(
                                corpusSurahWord!![0].corpus.surah,
                                corpusSurahWord!![0].corpus.ayah
                            )
                            if (wbwayah != null) {
                                for (w in wbwayah) {
                                    val temp: StringBuilder = getSelectedTranslation(w)
                                    if (w.wordno == harfword) {
                                        sb.append(temp).append(" ")
                                    } else if (w.wordno in shartSword..shartEword) {
                                        sb.append(temp).append(" ")
                                    }
                                }
                            }
                            sb.append(".....")
                            sb.append(sbjawab)
                            //   shartarray.add(SpannableString.valueOf(sb.toString()));
                        }
                        shartarray.add(SpannableString.valueOf(sb.toString()))
                    } else if (isharfb) {
                        harfspannble = SpannableString(harfofverse)
                        harfspannble.setSpan(
                            Constant.harfshartspanDark,
                            0,
                            harfofverse.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        val charSequence = TextUtils.concat(harfspannble)
                        val trstr = getFragmentTranslations(quranverses, charSequence)
                        shartarray.add(SpannableString.valueOf(charSequence))
                        shartarray.add(trstr)
                    }
                }
            }
        }*/































































    fun onItemClicked(itemId: Int) {
        itemIdsList.value = itemIdsList.value.toMutableList().also { list ->
            if (list.contains(itemId)) {
                list.remove(itemId)
            } else {
                list.add(itemId)
            }
        }
    }
}
