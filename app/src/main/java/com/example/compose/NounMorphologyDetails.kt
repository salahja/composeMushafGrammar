package com.example.compose


import android.text.SpannableStringBuilder
import androidx.compose.ui.text.AnnotatedString
import com.example.compose.AnnotatedQuranMorphologyDetails.Companion.expandTags
import com.example.compose.AnnotatedQuranMorphologyDetails.Companion.getGenderNumberdetails
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.utility.AnnotationUtility.Companion.AnnotatedSetWordSpanTag
import com.example.utility.AnnotationUtility.Companion.AnnotatedWordSpan
import com.example.utility.CorpusConstants
import com.example.utility.CorpusConstants.verbfeaturesenglisharabic
import com.example.utility.CorpusUtilityorig.Companion.NewSetWordSpan

import java.util.Objects


open class NounMorphologyDetails(
    corpusSurahWord: QuranCorpusWbw,
    corpusNounWord: NounCorpus?,

    ) {
    open var form = 0
    open var Thulathi: String? = null

    // --Commented out by Inspection (16/08/23, 1:44 pm):private CorpusWbwWord word;
    private var corpusNoun: NounCorpus? = corpusNounWord

    //  private var corpusSurahWord: ArrayList<NewCorpusExpandWbwPOJO>? = null
    private var corpusSurahWord: QuranCorpusWbw? = corpusSurahWord


    init {
        this.corpusSurahWord = corpusSurahWord
    }


    //     pngsb.append("," + "(form").append(verbcorpusform.get(0).getForm()).append(")");


    public fun convertForms(mform: String?) {
        when (mform) {
            "IV" -> form = 1
            "II" -> form = 2
            "III" -> form = 3
            "VII" -> form = 4
            "VIII" -> form = 5
            "VI" -> form = 7
            "V" -> form = 8
            "X" -> form = 9
            else -> {

            }
        }
    }

    //get the root,since vercopus is not checked
//    wordbdetail.put("form", AnnotatedString("I"));
    //chedk if particple
    open val wordDetails: HashMap<String, AnnotatedString>
        get() {
            val wordbdetail = HashMap<String, AnnotatedString>()
            wordbdetail["surahid"] = AnnotatedString(
                java.lang.String.valueOf(
                    corpusSurahWord!!.corpus.surah
                )
            )
            wordbdetail["ayahid"] = AnnotatedString(
                java.lang.String.valueOf(
                    corpusSurahWord!!.corpus.ayah
                )
            )
            wordbdetail["wordno"] = AnnotatedString(
                java.lang.String.valueOf(
                    corpusSurahWord!!.corpus.wordno
                )
            )
            //    wordbdetail["wordtranslation"] =
            //       AnnotatedString(corpusSurahWord!!.get(0).wbw.en)
            val arabicword: String =
                corpusSurahWord!!.corpus.araone + (corpusSurahWord!!.corpus.aratwo + (
                        corpusSurahWord!!.corpus.arathree
                                + (
                                corpusSurahWord!!.corpus.arafour
                                        + (corpusSurahWord!!.corpus.arafour) + corpusSurahWord!!.corpus.arafive
                                )
                        )
                        )
            wordbdetail["arabicword"] = AnnotatedString(arabicword)
            //  if (corpusNoun!!.size > 0) {
            if (corpusNoun!!.proptwo.equals(CorpusConstants.NominalsProp.PCPL)) {
                val form: String? = corpusNoun!!.form
                val mform = form?.replace("[()]".toRegex(), "")
                if (mform != "I") {
                    convertForms(mform)
                    wordbdetail["form"] = AnnotatedString(this.form.toString())
                    getRoot(corpusSurahWord!!, wordbdetail)
                    //chedk if particple
                } else {
                    wordbdetail["form"] =
                        AnnotatedString(corpusNoun!!.form.toString())
                }
                if (corpusNoun!!.proptwo.equals("PCPL")) {
                    wordbdetail["PCPL"] =
                        AnnotatedString(
                            corpusNoun!!.propone + (
                                    corpusNoun!!.proptwo
                                    )
                        )
                    wordbdetail["particple"] = AnnotatedString("PART")
                    //    wordbdetail.put("form", AnnotatedString("I"));
                } else {
                    wordbdetail["PCPL"] = AnnotatedString("NONE")
                    wordbdetail["particple"] = AnnotatedString("PART")
                    //   wordbdetail.put("form", AnnotatedString("I"));
                }
            }
            //  }
            GetPronounDetails(corpusSurahWord!!, wordbdetail)
            GetLemmArabicwordWordDetails(corpusSurahWord!!, wordbdetail)
            val sb = StringBuilder()
            //get the root,since vercopus is not checked
            getRoot(corpusSurahWord!!, wordbdetail)
            getNdetails(corpusNoun, wordbdetail, sb)
            getProperNounDetails(corpusNoun, wordbdetail, sb)
            val isNoun = wordbdetail["worddetails"].toString().contains("Noun")
            if (wordbdetail["worddetails"] != null) { //todo need refactor based on wordcount
                if (!isNoun) {
                    val relative =
                        wordbdetail["worddetails"].toString().contains("Relative Pronoun")
                    val prep = Objects.requireNonNull(
                        wordbdetail["worddetails"]
                    ).toString().contains("Prepositions")
                    val cond =
                        wordbdetail["worddetails"].toString().contains("Conditional particle")
                    val pron = wordbdetail["worddetails"].toString().contains("Pronouns")
                    val dem =
                        wordbdetail["worddetails"].toString().contains("Demonstrative Pronoun")
                    val time = wordbdetail["worddetails"].toString().contains("Time Adverb")
                    val harfnasb =
                        wordbdetail["worddetails"].toString().contains("Accusative(حرف نصب)")
                    if (relative) {
                        wordbdetail["relative"] = AnnotatedString("relative")
                    } else if (cond || time) {
                        wordbdetail["cond"] = AnnotatedString("cond")
                    } else if (harfnasb) {
                        wordbdetail["harfnasb"] = AnnotatedString("harfnasb")
                    } else if (prep) {
                        wordbdetail["prep"] = AnnotatedString("prep")
                    } else if (dem) {
                        wordbdetail["dem"] = AnnotatedString("dem")
                    }
                }
            }
            return wordbdetail
        }

    private fun getProperNounDetails(
        corpusNoun: NounCorpus?,
        wordbdetail: HashMap<String, AnnotatedString>,
        sbs: StringBuilder
    ) {
        val sb = StringBuilder()

        if (corpusNoun!!.tag == "PN" || corpusNoun!!.tag == "ADJ") {
            val propone: String? = corpusNoun!!.propone
            val proptwo: String? = corpusNoun!!.proptwo
            var pcpl = ""
            if (propone != "null" && proptwo != "null") {
                pcpl = pcpl + propone + proptwo
            }
            if (corpusNoun!!.propone.equals("VN")) {
                sb.append("Proper/Verbal Noun")
            } else if (corpusNoun!!.tag == "ADJ") {
                sb.append("Adjective:")
            } else {
                sb.append("Proper Noun:")
            }
            val form: String? = corpusNoun!!.form
            val gendernumber: String? = corpusNoun!!.gendernumber
            val type: String? = corpusNoun!!.type
            val cases: String? = corpusNoun!!.cases
            // sb.append("Proper Noun:");
            if (cases != "null") {
                when (cases) {
                    "NOM" -> sb.append(CorpusConstants.NominalsProp.NOM)
                    "ACC" -> sb.append(CorpusConstants.NominalsProp.ACC)
                    "GEN" -> {
                        sb.append(CorpusConstants.NominalsProp.GEN)
                        sb.append(CorpusConstants.NominalsProp.GEN)
                    }
                }
            }
            if (gendernumber != "null") {
                if (gendernumber != null) {
                    if (gendernumber.length == 1) {
                        when (gendernumber) {
                            "M" -> sb.append(CorpusConstants.png.M + " ")
                            "F" -> sb.append(CorpusConstants.png.F + " ")
                            "P" -> sb.append(CorpusConstants.png.P + " ")
                        }
                    } else if (gendernumber.length == 2) {
                        val gender = gendernumber.substring(0, 1)
                        val number = gendernumber.substring(1, 2)
                        when (gender) {
                            "M" -> sb.append(CorpusConstants.png.M + " ")
                            "F" -> sb.append(CorpusConstants.png.F + " ")
                        }
                        when (number) {
                            "P" -> sb.append(CorpusConstants.png.P + " ")
                            "S" -> sb.append(CorpusConstants.png.S + " ")
                            "D" -> sb.append(CorpusConstants.png.D + " ")
                        }
                    }
                }
            }
            if (form != "null") {
                sb.append("(form ").append(form).append(")")
                wordbdetail["formnumber"] = AnnotatedString(form.toString())
            }
            if (propone != "null" && proptwo != "null") {
                if ((pcpl == "ACTPCPL")) {
                    sb.append(CorpusConstants.NominalsProp.ACTPCPL)
                    wordbdetail["particple"] = AnnotatedString("PART")
                } else if ((pcpl == "PASSPCPL")) {
                    sb.append(CorpusConstants.NominalsProp.PASSPCPL)
                    wordbdetail["particple"] = AnnotatedString("PART")
                }
            }
            if (type != "null") {
                sb.append(CorpusConstants.NominalsProp.INDEF)
                sb.append(",")
            }
            if (sb.length > 5) {
                wordbdetail["noun"] = AnnotatedString(sb.toString())
            }
        }
    }


private fun getNdetails(
    corpusNoun: NounCorpus?,
    wordbdetail: HashMap<String, AnnotatedString>,
    sb: StringBuilder
) {

    if ((corpusNoun!!.tag == "N" || corpusNoun!!.tag == "PN" ||
                corpusNoun!!.tag == "VN" ||
                corpusNoun!!.tag == "ADJ")
    ) {
        //   if (corpusNoun.get(0).tag.equals("N")) {
        val propone: String? = corpusNoun!!.propone
        val proptwo: String? = corpusNoun!!.proptwo
        var pcpl = ""
        if (propone != "null" && proptwo != "null") {
            pcpl = pcpl + propone + proptwo
        }
        val form: String? = corpusNoun!!.form
        val gendernumber: String? = corpusNoun!!.gendernumber
        val type: String? = corpusNoun!!.type
        val cases: String? = corpusNoun!!.cases
        when (corpusNoun!!.tag) {
            "N" -> sb.append("Noun:" + " ")
            "ADJ" -> sb.append("Adjective:" + " ")
            "PN" -> sb.append("Porper Noun:" + " ")
            "VN" -> sb.append("Verbal Noun:" + " ")
        }
        //   sb.append(corpusNoun.get(0).tag.concat(" "));
        if (cases != "null") {
            when (cases) {
                "NOM" -> {
                    wordbdetail["nouncase"] = AnnotatedString("NOM")
                    sb.append(CorpusConstants.NominalsProp.NOM + " ")
                }

                "ACC" -> {
                    wordbdetail["nouncase"] = AnnotatedString("ACC")
                    sb.append(CorpusConstants.NominalsProp.ACC + " ")
                }

                "GEN" -> {
                    wordbdetail["nouncase"] = AnnotatedString("GEN")
                    sb.append(CorpusConstants.NominalsProp.GEN + " ")
                }
            }
        }
        if (gendernumber != "null") {
            if (gendernumber != null) {
                if (gendernumber.length == 1) {
                    when (gendernumber) {
                        "M" -> sb.append(CorpusConstants.png.M)
                        "F" -> sb.append(CorpusConstants.png.F)
                        "P" -> sb.append(CorpusConstants.png.P)
                    }
                } else if (gendernumber.length == 2) {
                    val gender = gendernumber.substring(0, 1)
                    val number = gendernumber.substring(1, 2)
                    when (gender) {
                        "M" -> sb.append(CorpusConstants.png.M + " ")
                        "F" -> sb.append(CorpusConstants.png.F + " ")
                    }
                    when (number) {
                        "P" -> sb.append(CorpusConstants.png.P + " ")
                        "S" -> sb.append(CorpusConstants.png.S + " ")
                        "D" -> sb.append(CorpusConstants.png.D + " ")
                    }
                }
            }
        }
        if (form != "null") {
            sb.append("(form").append(" ").append(form).append(")")
        }
        if (propone != "null" && proptwo != "null") {
            if ((pcpl == "ACTPCPL")) {
                sb.append(CorpusConstants.NominalsProp.ACTPCPL)
            } else if ((pcpl == "PASSPCPL")) {
                sb.append(CorpusConstants.NominalsProp.PASSPCPL)
            }
        }
        if (type != "null") {
            sb.append(CorpusConstants.NominalsProp.INDEF)
            sb.append(",")
        }
        if (sb.length > 5) {
            wordbdetail["noun"] = AnnotatedString(sb.toString())
        }
    } else if ((corpusNoun!!.tag == "PN" ||
                corpusNoun!!.tag == "VN" ||
                corpusNoun!!.tag == "ADJ")
    ) {
        val cases: String? = corpusNoun!!.cases
        //  sb.append("Noun:");
        if (cases != "null") {
            when (cases) {
                "NOM" -> wordbdetail["nouncase"] = AnnotatedString("NOM")
                "ACC" -> wordbdetail["nouncase"] = AnnotatedString("ACC")
                "GEN" -> wordbdetail["nouncase"] = AnnotatedString("GEN")
            }
        }
    }
}


private fun getRoot(
    corpusSurahWord: QuranCorpusWbw,
    wordbdetail: HashMap<String, AnnotatedString>
) {
   // if (corpusSurahWord.size > 0) {
        if (corpusSurahWord.corpus.wordcount === 1) {
            if (corpusSurahWord.corpus.rootaraone!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaraone!!)
            }
        } else if (corpusSurahWord.corpus.wordcount === 2) {
            if (corpusSurahWord.corpus.rootaraone!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaraone!!)
            } else if (corpusSurahWord.corpus.rootaratwo!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaratwo!!)
            }
        } else if (corpusSurahWord.corpus.wordcount === 3) {
            if (corpusSurahWord.corpus.rootaraone!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaraone!!)
            } else if (corpusSurahWord.corpus.rootaratwo!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaratwo!!)
            } else if (corpusSurahWord.corpus.rootarathree!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootarathree!!)
            }
        } else if (corpusSurahWord.corpus.wordcount === 4) {
            if (corpusSurahWord.corpus.rootaraone!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaraone!!)
            } else if (corpusSurahWord.corpus.rootaratwo!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaratwo!!)
            } else if (corpusSurahWord.corpus.rootarathree!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootarathree!!)
            } else if (corpusSurahWord.corpus.rootarafour!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootarafour!!)
            }
        } else if (corpusSurahWord.corpus.wordcount === 5) {
            if (corpusSurahWord.corpus.rootaraone!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaraone!!)
            } else if (corpusSurahWord.corpus.rootaratwo!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootaratwo!!)
            } else if (corpusSurahWord.corpus.rootarathree!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootarathree!!)
            } else if (corpusSurahWord.corpus.rootarafour!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootarafour!!)
            } else if (corpusSurahWord.corpus.rootarafive!!.isNotEmpty()) {
                wordbdetail["root"] =
                    AnnotatedString(corpusSurahWord.corpus.rootarafive!!)
            }
        }
  //  }
}

private fun GetLemmArabicwordWordDetails(
    corpusSurahWord: QuranCorpusWbw,
    wordbdetail: HashMap<String, AnnotatedString>
) {
    val tagone: String = corpusSurahWord.corpus.tagone!!
    val tagtwo: String = corpusSurahWord.corpus.tagtwo!!
    val tagthree: String = corpusSurahWord.corpus.tagthree!!
    val tagfour: String = corpusSurahWord.corpus.tagfour!!
    val tagfive: String = corpusSurahWord.corpus.tagfive!!
    val araone: String = corpusSurahWord.corpus.araone!!
    val aratwo: String = corpusSurahWord.corpus.aratwo!!
    val arathree: String = corpusSurahWord.corpus.arathree!!
    val arafour: String = corpusSurahWord.corpus.arafour!!
    val arafive: String = corpusSurahWord.corpus.arafive!!
    if (corpusSurahWord.corpus.wordcount === 1) {
        //noun yelllo
        //verb cyan
        //
        val expandTagsone = expandTags(tagone)
        wordbdetail["lemma"] =
            AnnotatedString(corpusSurahWord.corpus.lemaraone!!)
        val spannableString =
            AnnotatedWordSpan(tagone, "", "", "", "", araone, "", "", "", "")
        val tagspannables =
            AnnotatedSetWordSpanTag(tagone, "", "", "", "", araone, "", "", "", expandTagsone)
        wordbdetail["word"] = AnnotatedString(spannableString.toString())
        wordbdetail["worddetails"] = AnnotatedString(tagspannables.toString())
        if (corpusSurahWord.corpus.detailsone!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        }
    } else if (corpusSurahWord.corpus.wordcount === 2) {
        wordbdetail["lemma"] = AnnotatedString(
            corpusSurahWord.corpus.lemaraone + corpusSurahWord.corpus.lemaratwo
        )
        val arabicspannable = SpannableStringBuilder(
            araone + aratwo
        )
        val expandTagsone = expandTags(tagone)

        val expandTagstwo = expandTags(tagtwo)
        if (corpusSurahWord.corpus.detailsone!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailstwo!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        }
        val one: Int = corpusSurahWord.corpus.araone!!.length //2
        val two: Int = corpusSurahWord.corpus.aratwo!!.length //3
        val spannableString =
            AnnotatedWordSpan(tagone, tagtwo, "", "", "", araone, aratwo, "", "", "")
        val tagspannables = AnnotatedSetWordSpanTag(
            tagone,
            tagtwo,
            "",
            "",
            "",
            araone,
            aratwo,
            "",
            expandTagstwo,
            expandTagsone
        )
        wordbdetail["word"] = AnnotatedString(spannableString.toString())
        wordbdetail["worddetails"] = AnnotatedString(tagspannables.toString())
    } else if (corpusSurahWord.corpus.wordcount === 3) {
        val sb = StringBuilder()
        wordbdetail["lemma"] = AnnotatedString(
            (corpusSurahWord.corpus.lemaraone + corpusSurahWord.corpus.lemaratwo +
                    corpusSurahWord.corpus.lemarathree)
        )
        val one: Int = corpusSurahWord.corpus.araone!!.length //2
        val two: Int = corpusSurahWord.corpus.aratwo!!.length //3
        var expandTagsthree = ""
        val expandTagsone = expandTags(
            corpusSurahWord.corpus.tagone!!
        )
        val expandTagstwo = expandTags(
            corpusSurahWord.corpus.tagtwo!!
        )

        expandTagsthree =
            if (corpusSurahWord.corpus.detailsthree!!.contains("SUFFIX|+n:EMPH")) {
                "EMPH – emphatic suffix nūn"
            } else {
                expandTags(
                    corpusSurahWord.corpus.tagthree!!
                )
            }


        sb.append(corpusSurahWord.corpus.tagthree)
        sb.append("|")
        sb.append(corpusSurahWord.corpus.tagtwo)
        sb.append("|")
        sb.append(corpusSurahWord.corpus.tagone)
        // 0,tagthree
        // tagthree,tagtwo
        // tagtwo,tagone
        val spannableString =
            AnnotatedWordSpan(tagone, tagtwo, tagthree, "", "", araone, aratwo, arathree, "", "")
        val tagspannables = AnnotatedSetWordSpanTag(
            tagone,
            tagtwo,
            tagthree,
            "",
            "",
            " ",
            " ",
            expandTagsthree,
            expandTagstwo,
            expandTagsone
        )
        wordbdetail["word"] = AnnotatedString(spannableString.toString())
        wordbdetail["worddetails"] = AnnotatedString(tagspannables.toString())
        if (corpusSurahWord.corpus.detailsone!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailstwo!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailsthree!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        }
    } else if (corpusSurahWord.corpus.wordcount === 4) {
        wordbdetail["lemma"] = AnnotatedString(
            (corpusSurahWord.corpus.lemaraone + corpusSurahWord.corpus.lemaratwo +
                    corpusSurahWord.corpus.lemarathree)
        )
        val one: Int = corpusSurahWord.corpus.araone!!.length //2
        val two: Int = corpusSurahWord.corpus.aratwo!!.length //3
        val onetag: Int = corpusSurahWord.corpus.tagone!!.length //1
        val twotag: Int = corpusSurahWord.corpus.tagtwo!!.length //3
        val expandTagsone = expandTags(
            corpusSurahWord.corpus.tagone!!
        )
        val expandTagstwo = expandTags(
            corpusSurahWord.corpus.tagtwo!!
        )
        val expandTagsthree = expandTags(
            corpusSurahWord.corpus.tagthree!!
        )
        val expandTagsfour = expandTags(
            corpusSurahWord.corpus.tagfour!!
        )
        val spannableString = AnnotatedWordSpan(
            tagone,
            tagtwo,
            tagthree,
            tagfour,
            "",
            araone,
            aratwo,
            arathree,
            arafour,
            ""
        )
        // SpannableString tagspannables = CorpusUtilityorig.AnnotatedSetWordSpanTag(tagone, tagtwo, tagthree, tagfour, tagfive," ", expandTagsfour, expandTagsthree, expandTagstwo, expandTagsone);
        val tagspannables = AnnotatedSetWordSpanTag(
            tagone, tagtwo, tagthree,
            tagfour, tagfive, " ", expandTagsfour, expandTagsthree, expandTagstwo, expandTagsone
        )
        wordbdetail["word"] = AnnotatedString(spannableString.toString())
        wordbdetail["worddetails"] = AnnotatedString(tagspannables.toString())
        if (corpusSurahWord.corpus.detailsone!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailstwo!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailsthree!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailsfour!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        }
    } else if (corpusSurahWord.corpus.wordcount === 5) {
        val expandTagsone = expandTags(
            corpusSurahWord.corpus.tagone!!
        )
        val expandTagstwo = expandTags(
            corpusSurahWord.corpus.tagtwo!!
        )
        val expandTagsthree = expandTags(
            corpusSurahWord.corpus.tagthree!!
        )
        val expandTagsfour = expandTags(
            corpusSurahWord.corpus.tagfour!!
        )
        val expandTagsfive = expandTags(
            corpusSurahWord.corpus.tagfive!!
        )
        val sb = StringBuilder()
        wordbdetail["lemma"] = AnnotatedString(
            (corpusSurahWord.corpus.lemaraone + corpusSurahWord.corpus.lemaratwo +
                    corpusSurahWord.corpus.lemarathree + corpusSurahWord.corpus
                .lemarafour + corpusSurahWord.corpus.lemarafive)
        )
        val tagspannables = AnnotatedSetWordSpanTag(
            tagone,
            tagtwo,
            tagthree,
            tagfour,
            tagfive,
            expandTagsfive,
            expandTagsfour,
            expandTagsthree,
            expandTagstwo,
            expandTagsone
        )
        sb.append(corpusSurahWord.corpus.tagfive)
        sb.append("|")
        sb.append(corpusSurahWord.corpus.tagfour)
        sb.append("|")
        sb.append(corpusSurahWord.corpus.tagthree)
        sb.append("|")
        sb.append(corpusSurahWord.corpus.tagtwo)
        sb.append("|")
        sb.append(corpusSurahWord.corpus.tagone)
        wordbdetail["word"] = AnnotatedString(
            (corpusSurahWord.corpus.araone + corpusSurahWord.corpus.aratwo +
                    corpusSurahWord.corpus.arathree + corpusSurahWord.corpus
                .arafour + corpusSurahWord.corpus.tagfive)
        )
        //   wordbdetail.put("worddetails", AnnotatedString(sb));
        wordbdetail["worddetails"] = AnnotatedString(tagspannables.toString())
        if (corpusSurahWord.corpus.detailsone!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailstwo!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailsthree!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailsfour!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        } else if (corpusSurahWord.corpus.detailsfive!!.contains("SP:kaAn")) {
            wordbdetail["spkana"] = AnnotatedString("spkana")
        }
    }
}

private fun GetPronounDetails(
    corpusSurahWord: QuranCorpusWbw,
    wordbdetail: HashMap<String, AnnotatedString>
) {
    if (corpusSurahWord!!.corpus.wordcount === 1) {
        if (corpusSurahWord!!.corpus.tagone.equals("PRON")) {
            //   String[] parts = corpusSurahWord.get(0).getDetailsone().toString().split("\"|");
            val gendernumber: String =
                corpusSurahWord.corpus.detailsone!!.replace("^.*?(\\w+)\\W*$", "$1")
            println(gendernumber)
            // String gendernumber = parts[parts.length - 1];
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 2) {
        if (corpusSurahWord!!.corpus.tagtwo.equals("PRON")) {
            val gendernumber: String =
                corpusSurahWord.corpus.detailstwo!!.replace("^.*?(\\w+)\\W*$", "$1")
            //   String gendernumber = parts[parts.length - 1];
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 3) {
        if (corpusSurahWord!!.corpus.tagthree.equals("PRON")) {
            //   String[] parts = corpusSurahWord.get(0).corpus.getDetailsthree().toString().split("|");
            val gendernumber: String =
                corpusSurahWord.corpus.detailsthree!!.replace("^.*?(\\w+)\\W*$", "$1")
            //    String gendernumber = parts[parts.length - 1];
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 4) {
        if (corpusSurahWord!!.corpus.tagfour.equals("PRON")) {
            val parts: List<String> = corpusSurahWord.corpus.detailsfour!!.split("\\|")
            val gendernumber = parts[parts.size - 1]
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 5) {
        if (corpusSurahWord!!.corpus.tagfive.equals("PRON")) {
            val parts: List<String> = corpusSurahWord.corpus.detailsfive!!.split("\\|")
            val gendernumber = parts[parts.size - 1]
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    }
}

private fun GetHarfDetail(
    corpusSurahWord: QuranCorpusWbw,
    wordbdetail: HashMap<String, AnnotatedString>
) {
    if (corpusSurahWord!!.corpus.wordcount === 1) {
        if (corpusSurahWord!!.corpus.tagone.equals("PRON")) {
            //   String[] parts = corpusSurahWord.get(0).corpus.getDetailsone().toString().split("\"|");
            val gendernumber: String =
                corpusSurahWord.corpus.detailsone!!.replace("^.*?(\\w+)\\W*$", "$1")
            println(gendernumber)
            // String gendernumber = parts[parts.length - 1];
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 2) {
        if (corpusSurahWord!!.corpus.tagtwo.equals("PRON")) {
            val gendernumber: String =
                corpusSurahWord.corpus.detailstwo!!.replace("^.*?(\\w+)\\W*$", "$1")
            //   String gendernumber = parts[parts.length - 1];
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 3) {
        if (corpusSurahWord!!.corpus.tagthree.equals("PRON")) {
            //   String[] parts = corpusSurahWord!!.get(0).corpus.getDetailsthree().toString().split("|");
            val gendernumber: String =
                corpusSurahWord.corpus.detailsthree!!.replace("^.*?(\\w+)\\W*$", "$1")
            //    String gendernumber = parts[parts.length - 1];
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 4) {
        if (corpusSurahWord!!.corpus.tagfour.equals("PRON")) {
            val parts: List<String> = corpusSurahWord.corpus.detailsfour!!.split("\\|")
            val gendernumber = parts[parts.size - 1]
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    } else if (corpusSurahWord!!.corpus.wordcount === 5) {
        if (corpusSurahWord!!.corpus.tagfive.equals("PRON")) {
            val parts: List<String> = corpusSurahWord.corpus.detailsfive!!.split("\\|")
            val gendernumber = parts[parts.size - 1]
            val builder = getGenderNumberdetails(gendernumber)
            wordbdetail["PRON"] = AnnotatedString(builder.toString())
        }
    }
}

companion object {
    fun getFormName(form: String?): String {
        var formdetails = ""
        formdetails = when (form) {
            "IV" -> "Form-IV(إِفْعَال)"
            "II" -> "Form-II(تَفْعِيل)"
            "III" -> "Form-III(فَاعَلَ)"
            "VII" -> "Form-VII(اِنْفِعَال)"
            "VIII" -> "Form-VIII(اِفْتِعَال)"
            "VI" -> "Form-VI(تَفَاعُل)"
            "V" -> "Form-V(تَفَعُّل)"
            "X" -> "Form-X(اِسْتِفْعَال)"
            else ->
                ""
        }
        return formdetails
    }

    fun getThulathiName(s: String?): StringBuilder {
        val sb = StringBuilder()
        sb.append("(")
        sb.append(("Form I-"))
        when (s) {
            "N" -> sb.append(CorpusConstants.thulathi.NASARA)
            "Z" -> sb.append(CorpusConstants.thulathi.ZARABA)
            "F" -> sb.append(CorpusConstants.thulathi.FATAH)
            "S" -> sb.append(CorpusConstants.thulathi.SAMIA)
            "K" -> sb.append(CorpusConstants.thulathi.KARUMU)
            "H" -> sb.append(CorpusConstants.thulathi.HASIBA)
        }
        sb.append(")")
        return sb
    }

    fun getGenderNumberdetails(gendernumber: String?): StringBuilder {
        val person: String
        val gender: String
        val number: String
        val sb = StringBuilder()
        if (gendernumber != null) {
            if (gendernumber.length == 3) {
                person = gendernumber.substring(0, 1)
                gender = gendernumber.substring(1, 2)
                number = gendernumber.substring(2, 3)
                when (person) {
                    "1" -> sb.append("1st Person ")
                    "2" -> sb.append("2nd Person ")
                    "3" -> sb.append("3rd Person ")
                }
                sb.append(" ")
                if ((gender == "M")) {
                    sb.append("Mas.")
                } else if ((gender == "F")) {
                    sb.append("Fem.")
                }
                sb.append(" ")
                when (number) {
                    "S" -> sb.append("Sing.")
                    "P" -> sb.append("Plural")
                    "D" -> sb.append("Dual")
                }
            } else if (gendernumber.length == 2) {
                person = gendernumber.substring(0, 1)
                number = gendernumber.substring(1, 2)
                val chars = gendernumber.toCharArray()
                //   if ((chars >= 'a' && chars <= 'z') || (chars[0] >= 'A' && chars[0] <= 'Z')
                if ((chars[0] in 'a'..'z') || (chars[0] in 'A'..'Z')
                ) {
                    when (person) {
                        "M" -> {
                            sb.append("Masc.")
                            sb.append(" ")
                        }

                        "F" -> {
                            sb.append("Fem.")
                            sb.append(" ")
                        }
                    }
                } else {
                    when (person) {
                        "1" -> sb.append("1st Person")
                        "2" -> sb.append("2nd Person")
                        "3" -> sb.append("3rd Person")
                    }
                }
                when (number) {
                    "S" -> sb.append("Singular")
                    "P" -> sb.append("Plural")
                    "D" -> sb.append("Dual")
                }
            } else {
                gendernumber.length
                if (gendernumber.length == 1) {
                    person = gendernumber.substring(0, 1)
                    when (person) {
                        "S" -> sb.append("Singular")
                        "P" -> sb.append("Plural")
                        "D" -> sb.append("Dual")
                        "1" -> sb.append("1st Per.")
                        "2" -> sb.append("2nd Per.")
                        "3" -> sb.append("3rd Per.")
                        "M" -> sb.append("Mas.")
                        "F" -> sb.append("Fem.")
                    }
                }
            }
        }
        return sb
    }

    fun expandTags(tagtwo: String): String {
        var tagtwo = tagtwo
        when (tagtwo) {
            "PASS" -> tagtwo = verbfeaturesenglisharabic.PASS
            "PERF" -> tagtwo = verbfeaturesenglisharabic.PERF
            "IMPF" -> tagtwo = verbfeaturesenglisharabic.IMPF
            "ACTPCPL" -> tagtwo = CorpusConstants.NominalsProp.ACTPCPL
            "PASSPCPL" -> tagtwo = CorpusConstants.NominalsProp.PASSPCPL
            "V" -> tagtwo = verbfeaturesenglisharabic.V
            "N" -> tagtwo = CorpusConstants.Nominals.N
            "PN" -> tagtwo = CorpusConstants.Nominals.PN
            "ADJ" -> tagtwo = CorpusConstants.Nominals.ADJ
            "PRON" -> tagtwo = CorpusConstants.Nominals.PRON
            "DEM" -> tagtwo = CorpusConstants.Nominals.DEM
            "REL" -> tagtwo = CorpusConstants.Nominals.REL
            "T" -> tagtwo = CorpusConstants.Nominals.T
            "LOC" -> tagtwo = CorpusConstants.Nominals.LOC
            "ACT" -> tagtwo = CorpusConstants.NominalsProp.PASS
            "VN" -> tagtwo = CorpusConstants.NominalsProp.VN
            "INDEF" -> tagtwo = CorpusConstants.NominalsProp.INDEF
            "DEF" -> tagtwo = CorpusConstants.NominalsProp.DEF
            "DET" -> tagtwo = CorpusConstants.Particles.DET
            "NOM" -> tagtwo = CorpusConstants.NominalsProp.NOM
            "ACC" -> tagtwo = CorpusConstants.NominalsProp.ACC
            "P" -> tagtwo = CorpusConstants.Particles.P
            "EMPH" -> tagtwo = CorpusConstants.Particles.EMPH
            "IMPV" -> tagtwo = CorpusConstants.Particles.IMPV
            "PRP" -> tagtwo = CorpusConstants.Particles.PRP
            "CONJ" -> tagtwo = CorpusConstants.Particles.CONJ
            "SUB" -> tagtwo = CorpusConstants.Particles.SUB
            "AMD" -> tagtwo = CorpusConstants.Particles.AMD
            "ANS" -> tagtwo = CorpusConstants.Particles.ANS
            "AVR" -> tagtwo = CorpusConstants.Particles.AVR
            "CAUS" -> tagtwo = CorpusConstants.Particles.CAUS
            "CERT" -> tagtwo = CorpusConstants.Particles.CERT
            "CIRC" -> tagtwo = CorpusConstants.Particles.CIRC
            "COM" -> tagtwo = CorpusConstants.Particles.COM
            "COND" -> tagtwo = CorpusConstants.Particles.COND
            "EQ" -> tagtwo = CorpusConstants.Particles.EQ
            "EXH" -> tagtwo = CorpusConstants.Particles.EXH
            "EXL" -> tagtwo = CorpusConstants.Particles.EXL
            "EXP" -> tagtwo = CorpusConstants.Particles.EXP
            "FUT" -> tagtwo = CorpusConstants.Particles.FUT
            "INC" -> tagtwo = CorpusConstants.Particles.INC
            "INT" -> tagtwo = CorpusConstants.Particles.INT
            "INTG" -> tagtwo = CorpusConstants.Particles.INTG
            "NEG" -> tagtwo = CorpusConstants.Particles.NEG
            "PREV" -> tagtwo = CorpusConstants.Particles.PREV
            "PRO" -> tagtwo = CorpusConstants.Particles.PRO
            "REM" -> tagtwo = CorpusConstants.Particles.REM
            "RES" -> tagtwo = CorpusConstants.Particles.RES
            "RET" -> tagtwo = CorpusConstants.Particles.RET
            "RSLT" -> tagtwo = CorpusConstants.Particles.RSLT
            "SUP" -> tagtwo = CorpusConstants.Particles.SUP
            "SUR" -> tagtwo = CorpusConstants.Particles.SUR
            "VOC" -> tagtwo = CorpusConstants.Particles.VOC
            "INL" -> tagtwo = CorpusConstants.Particles.INL
        }
        return tagtwo
    }
}
}


