package com.example.utility

import android.app.Activity
import android.content.Context

import android.graphics.Color.CYAN
import android.graphics.Color.GREEN
import android.preference.PreferenceManager
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.sourceInformation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import com.corpusutility.AnnotationUtility
import com.example.ComposeConstant
import com.example.ComposeConstant.harfinnaspanDark
import com.example.ComposeConstant.harfismspanDark
import com.example.ComposeConstant.harfkhabarspanDark
import com.example.Constant
import com.example.justJava.FrameSpan
import com.example.mushafconsolidated.Entities.NewMudhafEntity
import com.example.mushafconsolidated.Entities.NewShartEntity
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Entities.SifaEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.utility.QuranGrammarApplication.Companion.context
import java.util.regex.Pattern

class CorpusUtilityorig(private var context: Context?) {
    var ayah=0
    constructor(context: Context, suraid: Int, ayah: Int) : this(context){

        this.ayah=ayah
    }

    // --Commented out by Inspection (15/08/23, 4:17 pm):final ArrayList<MousufSifa> NEWmousufSifaArrayList = new ArrayList<>();
    private val preferences: String?
    var activity: Activity? = null

    init {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        preferences = prefs.getString("theme", "dark")
        val preferences = prefs.getString("theme", "dark")
        dark = preferences == "dark" || preferences == "blue" || preferences == "green"
    }

    //overload




    companion object {

        var dark = false

        @JvmStatic
        fun NewSetWordSpanTag(
            tagone: String,
            tagtwo: String,
            tagthree: String,
            tagfour: String,
            tagfive: String,
            arafive: String,
            arafour: String,
            arathree: String,
            aratwo: String,
            araone: String,
        ): SpannableString? {
            var arafive = arafive
            var arafour = arafour
            var arathree = arathree
            var aratwo = aratwo
            var araone = araone
            var str: SpannableString? = null
            var tagcounter = 0
            val b = tagone.isNotEmpty()
            val bb = tagtwo.isNotEmpty()
            val bbb = tagthree.isNotEmpty()
            val bbbb = tagfour.isNotEmpty()
            val bbbbb = tagfive.isNotEmpty()
            if (b && !bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 1
            } else if (b && bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 2
            } else if (b && bb && bbb && !bbbb && !bbbbb) {
                tagcounter = 3
            } else if (b && bb && bbb && bbbb && !bbbbb) {
                tagcounter = 4
            } else if (b && bb && bbb && bbbb) {
                tagcounter = 5
            }
            araone = araone.trim { it <= ' ' }
            aratwo = aratwo.trim { it <= ' ' }
            arathree = arathree.trim { it <= ' ' }
            arafour = arafour.trim { it <= ' ' }
            arafive = arafive.trim { it <= ' ' }
            //
            val spanhash: Map<String?, ForegroundColorSpan> = stringForegroundColorSpanMap
            when (tagcounter) {
                1 -> {
                    //   Set<String> strings = spanhash.keySet();
                    str =
                        SpannableString(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' })
                    str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                2 -> {
                    val strone = SpannableString(araone.trim { it <= ' ' })
                    val strtwo = SpannableString(aratwo.trim { it <= ' ' })
                    strtwo.setSpan(spanhash[tagtwo], 0, aratwo.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    strone.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    val charSequence = TextUtils.concat(strtwo, strone)
                    str = SpannableString(charSequence)
                }
                3 -> {
                    spanhash[tagone]
                    val strone = SpannableString(araone.trim { it <= ' ' })
                    val strtwo = SpannableString(aratwo.trim { it <= ' ' })
                    val strthree = SpannableString(arathree.trim { it <= ' ' })
                    strthree.setSpan(
                        spanhash[tagthree],
                        0,
                        arathree.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    strtwo.setSpan(spanhash[tagtwo], 0, aratwo.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    strone.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    val charSequence = TextUtils.concat(strthree, strtwo, strone)
                    str = SpannableString(charSequence)
                }
                4 -> {
                    //  str = new SpannableString(arafour.trim() + arathree.trim() + aratwo.trim() + araone.trim());
                    val strone = SpannableString(araone.trim { it <= ' ' })
                    val strtwo = SpannableString(aratwo.trim { it <= ' ' })
                    val strthree = SpannableString(arathree.trim { it <= ' ' })
                    val strfour = SpannableString(arafour.trim { it <= ' ' })
                    strfour.setSpan(
                        spanhash[tagfour],
                        0,
                        arafour.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    strthree.setSpan(
                        spanhash[tagthree],
                        0,
                        arathree.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    strtwo.setSpan(spanhash[tagtwo], 0, aratwo.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    strone.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    val charSequence = TextUtils.concat(strfour, strthree, strtwo, strone)
                    str = SpannableString(charSequence)
                }
                5 -> {
                    val strone = SpannableString(araone.trim { it <= ' ' })
                    val strtwo = SpannableString(aratwo.trim { it <= ' ' })
                    val strthree = SpannableString(arathree.trim { it <= ' ' })
                    val strfour = SpannableString(arafour.trim { it <= ' ' })
                    val strfive = SpannableString(arafive.trim { it <= ' ' })
                    strfive.setSpan(
                        spanhash[tagone],
                        0,
                        arafive.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    strfour.setSpan(
                        spanhash[tagtwo],
                        0,
                        arafour.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    strthree.setSpan(
                        spanhash[tagthree],
                        0,
                        arathree.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    strtwo.setSpan(
                        spanhash[tagfour],
                        0,
                        aratwo.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    strone.setSpan(
                        spanhash[tagfive],
                        0,
                        araone.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    val charSequence = TextUtils.concat(strone, strtwo, strthree, strfour, strfive)
                    str = SpannableString(charSequence)
                }
            }
            return str
        }

        @JvmStatic
        fun ColorizeRootword(
            tagone: String, tagtwo: String, tagthree: String, tagfour: String, tagfive: String,
            rootword: String,
        ): SpannableString? {
            var str: SpannableString? = null
            var tagcounter = 0
            val b = tagone.isNotEmpty()
            val bb = tagtwo.isNotEmpty()
            val bbb = tagthree.isNotEmpty()
            val bbbb = tagfour.isNotEmpty()
            val bbbbb = tagfive.isNotEmpty()
            if (b && !bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 1
            } else if (b && bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 2
            } else if (b && bb && bbb && !bbbb && !bbbbb) {
                tagcounter = 3
            } else if (b && bb && bbb && bbbb && !bbbbb) {
                tagcounter = 4
            } else if (b && bb && bbb && bbbb) {
                tagcounter = 5
            }
            //   SharedPreferences sharedPreferences =
            //      androidx.preference.PreferenceManager.getDefaultSharedPreferences(DarkThemeApplication.context!!);
            //   String isNightmode = sharedPreferences.getString("themepref", "dark" );
            //   if (isNightmode.equals("dark")||isNightmode.equals("blue")) {
            val spanhash: Map<String?, ForegroundColorSpan> = stringForegroundColorSpanMap
            //  }else{
            //   spanhash = getColorSpanforPhrasesLight();
            //  }
            if (tagcounter == 1) {
                str = SpannableString(rootword.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, rootword.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else if (tagcounter == 2) {
                if (tagone == "N" || tagone == "ADJ" || tagone == "PN" || tagone == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagone],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else if (tagtwo == "N" || tagtwo == "ADJ" || tagtwo == "PN" || tagtwo == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagtwo],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } else if (tagcounter == 3) {
                if (tagone == "N" || tagone == "ADJ" || tagone == "PN" || tagone == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagone],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else if (tagtwo == "N" || tagtwo == "ADJ" || tagtwo == "PN" || tagtwo == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagtwo],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else if (tagthree == "N" || tagthree == "ADJ" || tagthree == "PN" || tagthree == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagthree],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } else if (tagcounter == 4) {
                if (tagone == "N" || tagone == "ADJ" || tagone == "PN" || tagone == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagone],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else if (tagtwo == "N" || tagtwo == "ADJ" || tagtwo == "PN" || tagtwo == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagtwo],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else if (tagthree == "N" || tagthree == "ADJ" || tagthree == "PN" || tagthree == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagthree],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else if (tagfour == "N" || tagfour == "ADJ" || tagfour == "PN" || tagfour == "V") {
                    str = SpannableString(rootword.trim { it <= ' ' })
                    str.setSpan(
                        spanhash[tagthree],
                        0,
                        rootword.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            return str
        }












        @JvmStatic
        fun NewSetWordSpan(
            tagone: String?,
            tagtwo: String?,
            tagthree: String?,
            tagfour: String?,
            tagfive: String?,
            araone: String,
            aratwo: String,
            arathree: String,
            arafour: String,
            arafive: String,
        ): SpannableString {
            var araone = araone
            var aratwo = aratwo
            var arathree = arathree
            var arafour = arafour
            var arafive = arafive
            var str: SpannableString
            val istagnull =
                tagone == null || tagtwo == null || tagthree == null || tagfour == null || tagfive == null
            if (istagnull) {
                return SpannableString(araone).also { str = it }
            }
            var tagcounter = 0
            val b = tagone!!.isNotEmpty()
            val bb = tagtwo!!.isNotEmpty()
            val bbb = tagthree!!.isNotEmpty()
            val bbbb = tagfour!!.isNotEmpty()
            val bbbbb = tagfive!!.isNotEmpty()
            if (b && !bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 1
            } else if (b && bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 2
            } else if (b && bb && bbb && !bbbb && !bbbbb) {
                tagcounter = 3
            } else if (b && bb && bbb && bbbb && !bbbbb) {
                tagcounter = 4
            } else if (b && bb && bbb && bbbb) {
                tagcounter = 5
            }
            araone = araone.trim { it <= ' ' }
            aratwo = aratwo.trim { it <= ' ' }
            arathree = arathree.trim { it <= ' ' }
            arafour = arafour.trim { it <= ' ' }
            arafive = arafive.trim { it <= ' ' }
            //   SharedPreferences sharedPreferences =
            //      androidx.preference.PreferenceManager.getDefaultSharedPreferences(DarkThemeApplication.context!!);
            //   String isNightmode = sharedPreferences.getString("themepref", "dark" );
            //   if (isNightmode.equals("dark")||isNightmode.equals("blue")) {
            val spanhash: Map<String?, ForegroundColorSpan> = stringForegroundColorSpanMap
            //  }else{
            //   spanhash = getColorSpanforPhrasesLight();
            //  }
            if (tagcounter == 1) {
                str = SpannableString(araone.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else if (tagcounter == 2) {
                str = SpannableString(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(
                    spanhash[tagtwo],
                    araone.length,
                    araone.length + aratwo.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else if (tagcounter == 3) {
                spanhash[tagone]
                str =
                    SpannableString(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(
                    spanhash[tagtwo],
                    araone.length,
                    araone.length + aratwo.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagthree],
                    araone.length + aratwo.length,
                    araone.length + aratwo.length + arathree.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else if (tagcounter == 4) {
                str =
                    SpannableString(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' } + arafour.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(
                    spanhash[tagtwo],
                    araone.length,
                    araone.length + aratwo.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagthree],
                    araone.length + aratwo.length,
                    araone.length + aratwo.length + arathree.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagfour],
                    araone.length + aratwo.length + arathree.length,
                    araone.length + aratwo.length + arathree.length + arafour.trim { it <= ' ' }.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                //    str.setSpan(attachedpronoun, araone.length() + aratwo.length() + arathree.length() + arafour.length(), araone.length() + aratwo.length() + arathree.length() + arafour.trim().length() + arafive.trim().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (tagcounter == 5) {
                str =
                    SpannableString(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' } + arafour.trim { it <= ' ' } + arafive.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(
                    spanhash[tagtwo],
                    araone.length,
                    araone.length + aratwo.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagthree],
                    araone.length + aratwo.length,
                    araone.length + aratwo.length + arathree.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagfour],
                    araone.length + aratwo.length + arathree.length,
                    araone.length + aratwo.length + arathree.length + arafour.trim { it <= ' ' }.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagfive],
                    araone.length + aratwo.length + arathree.length + arafour.length,
                    araone.length + aratwo.length + arathree.length + arafour.trim { it <= ' ' }.length + arafive.trim { it <= ' ' }.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                str =
                    SpannableString(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' } + arafour.trim { it <= ' ' } + arafive.trim { it <= ' ' })
            }
            return str
        }// "Emphatic lām prefix(لام التوكيد) ",

        // "Imperative lāmprefix(لام الامر)",
        // "Purpose lāmprefix(لام التعليل)",
        // "	Subordinating conjunction(حرف مصدري)",
        // "	Accusative particle(حرف نصب)",
        // "	Answer particle	(حرف جواب)",
        // "Particle of cause	(حرف سببية)",
        // "Particle of certainty	(حرف تحقيق)",
        // "Circumstantial particle	(حرف حال)",
        // "Coordinating conjunction(حرف عطف)",
        // "Conditional particle(حرف شرط)",
        // "	Amendment particle(حرف استدراك)	",
        // "	Aversion particle	(حرف ردع)",
        // "	Comitative particle	(واو المعية)",
        // "	Equalization particle(حرف تسوية)",
        // "	Exhortation particle(حرف تحضيض)",
        // "	Explanation particle(حرف تفصيل)",
        // "	Exceptive particle	(أداة استثناء)",
        // "	Future particle	(حرف استقبال)",
        // "	Inceptive particle	(حرف ابتداء)",
        // "	Particle of interpretation(حرف تفسير)",
        // "	Retraction particle	(حرف اضراب)",
        // "Preventive particle	(حرف كاف)",
        // "	Vocative particle	(حرف نداء)",
        // "	Quranic initials(	(حروف مقطعة	";
        // "Interogative particle	(حرف استفهام)",
        // "	Negative particle(حرف نفي)",
        // "	Prohibition particle(حرف نهي)",
        // "	Resumption particle	(حرف استئنافية)",
        // "	Restriction particle(أداة حصر)",
        // "Result particle(حرف واقع في جواب الشرط)",
        // "	Supplemental particle	(حرف زائد)",
        // "	Surprise particle	(حرف فجاءة)",
        // "Emphatic lām prefix(لام التوكيد) ",
        // "Imperative lāmprefix(لام الامر)",
        // "Purpose lāmprefix(لام التعليل)",
        // "	Subordinating conjunction(حرف مصدري)",
        // "	Accusative particle(حرف نصب)",
        // "	Answer particle	(حرف جواب)",
        // "Particle of cause	(حرف سببية)",
        // "Particle of certainty	(حرف تحقيق)",
        // "Circumstantial particle	(حرف حال)",
        // "Coordinating conjunction(حرف عطف)",
        // "Conditional particle(حرف شرط)",
        // "	Amendment particle(حرف استدراك)	",
        // "	Aversion particle	(حرف ردع)",
        // "	Comitative particle	(واو المعية)",
        // "	Equalization particle(حرف تسوية)",
        // "	Exhortation particle(حرف تحضيض)",
        // "	Explanation particle(حرف تفصيل)",
        // "	Exceptive particle	(أداة استثناء)",
        // "	Future particle	(حرف استقبال)",
        // "	Inceptive particle	(حرف ابتداء)",
        // "	Particle of interpretation(حرف تفسير)",
        // "	Retraction particle	(حرف اضراب)",
        // "Preventive particle	(حرف كاف)",
        // "	Vocative particle	(حرف نداء)",
        // "	Quranic initials(	(حروف مقطعة	";
        // "Interogative particle	(حرف استفهام)",
        // "	Negative particle(حرف نفي)",
        // "	Prohibition particle(حرف نهي)",
        // "	Resumption particle	(حرف استئنافية)",
        // "	Restriction particle(أداة حصر)",
        // "Result particle(حرف واقع في جواب الشرط)",
        // "	Supplemental particle	(حرف زائد)",
        // "	Surprise particle	(حرف فجاءة)",
        @JvmStatic
        val stringForegroundColorSpanMap: Map<String?, ForegroundColorSpan>
            get() {
                val spanhash: MutableMap<String?, ForegroundColorSpan> = HashMap()
                if (dark) {
                    spanhash["PN"] = Constant.propernounspanDark
                    spanhash["REL"] = Constant.relativespanDark
                    spanhash["DEM"] = Constant.demonstrativespanDark
                    spanhash["N"] = Constant.nounspanDark
                    spanhash["PRON"] = Constant.pronounspanDark
                    spanhash["DET"] = Constant.determinerspanDark
                    spanhash["V"] = Constant.verbspanDark
                    spanhash["P"] = Constant.prepositionspanDark
                    spanhash["T"] = Constant.timeadverbspanDark
                    spanhash["LOC"] = Constant.locationadverspanDark
                    spanhash["ADJ"] = Constant.adjectivespanDark
                    spanhash["VN"] = Constant.verbalnounspanDark
                    spanhash["EMPH"] = Constant.emphspanDark // "Emphatic lām prefix(لام التوكيد) ",
                    spanhash["IMPV"] =
                        Constant.lamimpvspanDark // "Imperative lāmprefix(لام الامر)",
                    spanhash["PRP"] =
                        Constant.lamtaleelspandDark // "Purpose lāmprefix(لام التعليل)",
                    spanhash["SUB"] =
                        Constant.masdariaspanDark // "	Subordinating conjunction(حرف مصدري)",
                    spanhash["ACC"] = Constant.nasabspanDark // "	Accusative particle(حرف نصب)",
                    spanhash["ANS"] = Constant.answerspanDark // "	Answer particle	(حرف جواب)",
                    spanhash["CAUS"] =
                        Constant.harfsababiaspanDark // "Particle of cause	(حرف سببية)",
                    spanhash["CERT"] =
                        Constant.certainityspanDark // "Particle of certainty	(حرف تحقيق)",
                    spanhash["CIRC"] = Constant.halspanDark // "Circumstantial particle	(حرف حال)",
                    spanhash["CONJ"] =
                        Constant.particlespanDark // "Coordinating conjunction(حرف عطف)",
                    spanhash["COND"] =
                        Constant.harfshartspanDark // "Conditional particle(حرف شرط)",
                    spanhash["AMD"] =
                        Constant.particlespanDark // "	Amendment particle(حرف استدراك)	",
                    spanhash["AVR"] = Constant.particlespanDark // "	Aversion particle	(حرف ردع)",
                    spanhash["COM"] =
                        Constant.particlespanDark // "	Comitative particle	(واو المعية)",
                    spanhash["EQ"] =
                        Constant.particlespanDark // "	Equalization particle(حرف تسوية)",
                    spanhash["EXH"] =
                        Constant.particlespanDark // "	Exhortation particle(حرف تحضيض)",
                    spanhash["EXL"] =
                        Constant.particlespanDark // "	Explanation particle(حرف تفصيل)",
                    spanhash["EXP"] =
                        Constant.particlespanDark // "	Exceptive particle	(أداة استثناء)",
                    spanhash["FUT"] = Constant.particlespanDark // "	Future particle	(حرف استقبال)",
                    spanhash["INC"] =
                        Constant.particlespanDark // "	Inceptive particle	(حرف ابتداء)",
                    spanhash["INT"] =
                        Constant.particlespanDark // "	Particle of interpretation(حرف تفسير)",
                    spanhash["RET"] =
                        Constant.particlespanDark // "	Retraction particle	(حرف اضراب)",
                    spanhash["PREV"] = Constant.particlespanDark // "Preventive particle	(حرف كاف)",
                    spanhash["VOC"] = Constant.particlespanDark // "	Vocative particle	(حرف نداء)",
                    spanhash["INL"] =
                        Constant.particlespanDark // "	Quranic initials(	(حروف مقطعة	";
                    spanhash["INTG"] =
                        Constant.interrogativespanDark // "Interogative particle	(حرف استفهام)",
                    spanhash["NEG"] = Constant.negativespanDark // "	Negative particle(حرف نفي)",
                    spanhash["PRO"] =
                        Constant.prohibitionspanDark // "	Prohibition particle(حرف نهي)",
                    spanhash["REM"] =
                        Constant.resumtionspanDark // "	Resumption particle	(حرف استئنافية)",
                    spanhash["RES"] =
                        Constant.restrictivespanDark // "	Restriction particle(أداة حصر)",
                    spanhash["RSLT"] =
                        Constant.resultparticlespanDark // "Result particle(حرف واقع في جواب الشرط)",
                    spanhash["SUP"] =
                        Constant.supplementspoanDark // "	Supplemental particle	(حرف زائد)",
                    spanhash["SUR"] = Constant.surprisespanDark // "	Surprise particle	(حرف فجاءة)",
                } else {
                    spanhash["PN"] = Constant.propernounspanLight
                    spanhash["REL"] = Constant.relativespanLight
                    spanhash["DEM"] = Constant.demonstrativespanLight
                    spanhash["N"] = Constant.nounspanLight
                    spanhash["PRON"] = Constant.pronounspanLight
                    spanhash["DET"] = Constant.determinerspanLight
                    spanhash["V"] = Constant.verbspanLight
                    spanhash["P"] = Constant.prepositionspanLight
                    spanhash["T"] = Constant.timeadverbspanLight
                    spanhash["LOC"] = Constant.locationadverspanLight
                    spanhash["ADJ"] = Constant.adjectivespanLight
                    spanhash["VN"] = Constant.verbalnounspanLight
                    spanhash["EMPH"] =
                        Constant.emphspanLight // "Emphatic lām prefix(لام التوكيد) ",
                    spanhash["IMPV"] =
                        Constant.lamimpvspanLight // "Imperative lāmprefix(لام الامر)",
                    spanhash["PRP"] =
                        Constant.lamtaleelspandLight // "Purpose lāmprefix(لام التعليل)",
                    spanhash["SUB"] =
                        Constant.masdariaspanLight // "	Subordinating conjunction(حرف مصدري)",
                    spanhash["ACC"] = Constant.nasabspanLight // "	Accusative particle(حرف نصب)",
                    spanhash["ANS"] = Constant.answerspanLight // "	Answer particle	(حرف جواب)",
                    spanhash["CAUS"] =
                        Constant.harfsababiaspanLight // "Particle of cause	(حرف سببية)",
                    spanhash["CERT"] =
                        Constant.certainityspanLight // "Particle of certainty	(حرف تحقيق)",
                    spanhash["CIRC"] =
                        Constant.particlespanLight // "Circumstantial particle	(حرف حال)",
                    spanhash["CONJ"] =
                        Constant.particlespanLight // "Coordinating conjunction(حرف عطف)",
                    spanhash["COND"] = Constant.eqspanlight // "Conditional particle(حرف شرط)",
                    spanhash["AMD"] =
                        Constant.ammendedparticle // "	Amendment particle(حرف استدراك)	",
                    spanhash["AVR"] = Constant.particlespanLight // "	Aversion particle	(حرف ردع)",
                    spanhash["COM"] =
                        Constant.particlespanLight // "	Comitative particle	(واو المعية)",
                    spanhash["EQ"] =
                        Constant.particlespanLight // "	Equalization particle(حرف تسوية)",
                    spanhash["EXH"] =
                        Constant.particlespanLight // "	Exhortation particle(حرف تحضيض)",
                    spanhash["EXL"] =
                        Constant.particlespanLight // "	Explanation particle(حرف تفصيل)",
                    spanhash["EXP"] =
                        Constant.particlespanLight // "	Exceptive particle	(أداة استثناء)",
                    spanhash["FUT"] =
                        Constant.particlespanLight // "	Future particle	(حرف استقبال)",
                    spanhash["INC"] = Constant.nasabspanLight // "	Inceptive particle	(حرف ابتداء)",
                    spanhash["INT"] =
                        Constant.particlespanLight // "	Particle of interpretation(حرف تفسير)",
                    spanhash["RET"] =
                        Constant.particlespanLight // "	Retraction particle	(حرف اضراب)",
                    spanhash["PREV"] = Constant.inceptivepartile // "Preventive particle	(حرف كاف)",
                    spanhash["VOC"] = Constant.particlespanLight // "	Vocative particle	(حرف نداء)",
                    spanhash["INL"] =
                        Constant.particlespanLight // "	Quranic initials(	(حروف مقطعة	";
                    spanhash["INTG"] =
                        Constant.interrogativespanLight // "Interogative particle	(حرف استفهام)",
                    spanhash["NEG"] = Constant.negativespanLight // "	Negative particle(حرف نفي)",
                    spanhash["PRO"] =
                        Constant.prohibitionspanLight // "	Prohibition particle(حرف نهي)",
                    spanhash["REM"] =
                        Constant.particlespanLight // "	Resumption particle	(حرف استئنافية)",
                    spanhash["RES"] =
                        Constant.restrictivespanLight // "	Restriction particle(أداة حصر)",
                    spanhash["RSLT"] =
                        Constant.resultparticlespanLight // "Result particle(حرف واقع في جواب الشرط)",
                    spanhash["SUP"] =
                        Constant.supplementspanLight // "	Supplemental particle	(حرف زائد)",
                    spanhash["SUR"] =
                        Constant.surprisespanLight // "	Surprise particle	(حرف فجاءة)",
                }
                return spanhash
            }

        @JvmStatic
        fun getSpancolor(b: Boolean): BackgroundColorSpan {
            val sifaspansDark = Constant.sifaspansDark
            val mudhafspansDark = Constant.mudhafspansDark
            return if (b) {
                if (dark) {
                    Constant.mudhafspansDark = BackgroundColorSpan(Constant.MIDNIGHTBLUE)
                } else {
                    Constant.mudhafspansDark = BackgroundColorSpan(Constant.GREENYELLOW)
                }
                mudhafspansDark
            } else {
                if (dark) {
                    Constant.sifaspansDark = BackgroundColorSpan(Constant.WBURNTUMBER)
                } else {
                    Constant.sifaspansDark = BackgroundColorSpan(Constant.CYANLIGHTEST)
                }
                sifaspansDark
            }
        }


        @JvmStatic
        fun getSpannable(text: String): Spannable {
            val spannable: Spannable = SpannableString(text)
            val REGEX = "لل"
            val p = Pattern.compile(REGEX)
            val m = p.matcher(text)
            var start: Int
            var end: Int
            //region allah match
            while (m.find()) {
                start = m.start()
                while (text[start] != ' ' && start != 0) {
                    start--
                }
                end = m.end()
                while (text[end] != ' ') {
                    end++
                }
                spannable.setSpan(
                    ForegroundColorSpan(Constant.DeepPink),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            //endregion
            return spannable
        }

        fun PassageSetWordSpan(
            tagone: String?,
            tagtwo: String?,
            tagthree: String?,
            tagfour: String?,
            tagfive: String?,
            araone: String,
            aratwo: String,
            arathree: String,
            arafour: String,
            arafive: String,
        ): SpannableStringBuilder {
            var araone = araone
            var aratwo = aratwo
            var arathree = arathree
            var arafour = arafour
            var arafive = arafive
            var str: SpannableStringBuilder
            val istagnull =
                tagone == null || tagtwo == null || tagthree == null || tagfour == null || tagfive == null
            if (istagnull) {
                return SpannableStringBuilder(araone).also { str = it }
            }
            var tagcounter = 0
            val b = tagone!!.isNotEmpty()
            val bb = tagtwo!!.isNotEmpty()
            val bbb = tagthree!!.isNotEmpty()
            val bbbb = tagfour!!.isNotEmpty()
            val bbbbb = tagfive!!.isNotEmpty()
            if (b && !bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 1
            } else if (b && bb && !bbb && !bbbb && !bbbbb) {
                tagcounter = 2
            } else if (b && bb && bbb && !bbbb && !bbbbb) {
                tagcounter = 3
            } else if (b && bb && bbb && bbbb && !bbbbb) {
                tagcounter = 4
            } else if (b && bb && bbb && bbbb) {
                tagcounter = 5
            }
            araone = araone.trim { it <= ' ' }
            aratwo = aratwo.trim { it <= ' ' }
            arathree = arathree.trim { it <= ' ' }
            arafour = arafour.trim { it <= ' ' }
            arafive = arafive.trim { it <= ' ' }
            //   SharedPreferences sharedPreferences =
            //      androidx.preference.PreferenceManager.getDefaultSharedPreferences(DarkThemeApplication.context!!);
            //   String isNightmode = sharedPreferences.getString("themepref", "dark" );
            //   if (isNightmode.equals("dark")||isNightmode.equals("blue")) {
            val spanhash: Map<String?, ForegroundColorSpan> = stringForegroundColorSpanMap
            //  }else{
            //   spanhash = getColorSpanforPhrasesLight();
            //  }
            if (tagcounter == 1) {
                str = SpannableStringBuilder(araone.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else if (tagcounter == 2) {
                val stro = SpannableStringBuilder(araone.trim { it <= ' ' })
                val strt = SpannableStringBuilder(aratwo.trim { it <= ' ' })
                stro.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                strt.setSpan(spanhash[tagtwo], 0, aratwo.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                val concat = TextUtils.concat(stro, strt)
                str = concat as SpannableStringBuilder
            } else if (tagcounter == 3) {
                spanhash[tagone]
                str =
                    SpannableStringBuilder(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(
                    spanhash[tagtwo],
                    araone.length,
                    araone.length + aratwo.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagthree],
                    araone.length + aratwo.length,
                    araone.length + aratwo.length + arathree.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else if (tagcounter == 4) {
                str =
                    SpannableStringBuilder(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' } + arafour.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(
                    spanhash[tagtwo],
                    araone.length,
                    araone.length + aratwo.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagthree],
                    araone.length + aratwo.length,
                    araone.length + aratwo.length + arathree.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagfour],
                    araone.length + aratwo.length + arathree.length,
                    araone.length + aratwo.length + arathree.length + arafour.trim { it <= ' ' }.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                //    str.setSpan(attachedpronoun, araone.length() + aratwo.length() + arathree.length() + arafour.length(), araone.length() + aratwo.length() + arathree.length() + arafour.trim().length() + arafive.trim().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (tagcounter == 5) {
                str =
                    SpannableStringBuilder(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' } + arafour.trim { it <= ' ' } + arafive.trim { it <= ' ' })
                str.setSpan(spanhash[tagone], 0, araone.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                str.setSpan(
                    spanhash[tagtwo],
                    araone.length,
                    araone.length + aratwo.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagthree],
                    araone.length + aratwo.length,
                    araone.length + aratwo.length + arathree.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagfour],
                    araone.length + aratwo.length + arathree.length,
                    araone.length + aratwo.length + arathree.length + arafour.trim { it <= ' ' }.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                str.setSpan(
                    spanhash[tagfive],
                    araone.length + aratwo.length + arathree.length + arafour.length,
                    araone.length + aratwo.length + arathree.length + arafour.trim { it <= ' ' }.length + arafive.trim { it <= ' ' }.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                str =
                    SpannableStringBuilder(araone.trim { it <= ' ' } + aratwo.trim { it <= ' ' } + arathree.trim { it <= ' ' } + arafour.trim { it <= ' ' } + arafive.trim { it <= ' ' })
            }
            return str
        }

        fun composeWBWCollection(allofQuran: List<QuranEntity>?, corpusSurahWord: List<QuranCorpusWbw>?): LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>> {

            val newnewadapterlist = LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>()
            var qurancorpusarray = ArrayList<NewQuranCorpusWbw>()




            var aindex = 0
            var secondindex = 0

            while (aindex <= allofQuran!!.size) {

                var ayahWord = NewQuranCorpusWbw()

                try {
                    while (corpusSurahWord!![secondindex].corpus.ayah <= allofQuran[aindex].ayah) {
                        if (corpusSurahWord[secondindex].corpus.ayah != allofQuran[aindex].ayah) {
                            break
                        }
                        ayahWord.annotatedVerse = AnnotatedString(allofQuran[aindex].qurantext)
                        ayahWord.spannableverse = SpannableString.valueOf(allofQuran[aindex].qurantext)
                        ayahWord.wbw = corpusSurahWord[secondindex].wbw
                        ayahWord.corpus = corpusSurahWord[secondindex++].corpus
                        qurancorpusarray.add(ayahWord)

                        ayahWord = NewQuranCorpusWbw()
                    }
                }
                catch (e: IndexOutOfBoundsException) {
                    println(e.message)
                }

                if (qurancorpusarray.isNotEmpty()) {
                    newnewadapterlist[aindex] = qurancorpusarray
                    val ayahWord = NewQuranCorpusWbw()
                }
                qurancorpusarray = ArrayList()
                aindex++
            }

            return  newnewadapterlist

        }
        fun setSifa(
            newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
            chapid: Int,
            isdark: Boolean?
        ) {

            var list= ArrayList<AnnotatedString>()
            val utils = Utils(QuranGrammarApplication.context!!)
            val surah = utils.getSifabySurah(chapid)

            val spanhash: Map<String?, androidx.compose.ui.graphics.Color> =
                AnnotationUtility.stringForegroundColorSpanMap
            if (surah != null) {
                for (indexval in 0 until surah.size) {
                    val surahayah=utils.getSifabySurahAyah(chapid,indexval)
                    if (surahayah != null) {
                        if(surahayah.size > 1){
                            setMultipleSifa(surahayah,newnewadapterlist,chapid,spanhash)
                        }else if(surahayah.size==1){
                            val builder = AnnotatedString.Builder()
                            var annotatedString: AnnotatedString
                             var tagonecolor: Color
                      /*      if(isdark == true){
                             tagonecolor= ComposeConstant.adjectivespanDark
                            }else{
                                tagonecolor= ComposeConstant.adjectivespanLight
                            }*/
                            if (isdark == true) {
                                tagonecolor = ComposeConstant.sifaspansDark
                            } else {
                                tagonecolor = ComposeConstant.sifaspansLight

                            }




                            val tagonestyle = SpanStyle(
                                color = tagonecolor!!,                                )

                            val annotatedVerse =
                                newnewadapterlist[surahayah.get(0).ayah - 1]?.get(0)!!.annotatedVerse!!
                            builder.append(annotatedVerse)
                            builder.addStyle(tagonestyle,  surahayah.get(0).startindex, surahayah.get(0).endindex)

                            //  it[0]!!.get(0).setAnootedStr(builder.toAnnotatedString())
                            newnewadapterlist[surahayah.get(0).ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())

                            println(builder.toAnnotatedString())
                        }
                    }



                }
            }



        }
         fun setMudhafss(
             newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
             chapid: Int,
             isdark: Boolean?
         ) {
            var list= ArrayList<AnnotatedString>()
            val utils = Utils(QuranGrammarApplication.context!!)
            val surah = utils.getMudhafSurahNew(chapid)
             var tagonecolor: Color
            val spanhash: Map<String?, androidx.compose.ui.graphics.Color> =
                AnnotationUtility.stringForegroundColorSpanMap
            if (surah != null) {
                for (indexval in 0 until surah.size) {
                    val surahayah=utils.getMudhafSurahAyahNew(chapid,indexval)
                    if (surahayah != null) {
                        if(surahayah.size > 1){
                            seTmultiple(surahayah,newnewadapterlist,chapid,spanhash,isdark)
                        }else if(surahayah.size==1){
                            val builder = AnnotatedString.Builder()
                            var annotatedString: AnnotatedString
                        //    var tagonecolor =spanhash["mudhaf"]
                     /*       if(isdark!!){

                                    tagonecolor=        Color(ComposeConstant.MIDNIGHTBLUE)
                                } else {
                                    tagonecolor =  Color(ComposeConstant.WBURNTUMBER)
                                }*/

                            if (isdark == true) {
                                tagonecolor = Color(ComposeConstant.WBURNTUMBER)

                            } else {
                                tagonecolor = Color(ComposeConstant.MIDNIGHTBLUE)
                            }


                            val tagonestyle = SpanStyle(
                                color = tagonecolor,                                )

                            val annotatedVerse =
                                newnewadapterlist[surahayah.get(0).ayah - 1]?.get(0)!!.annotatedVerse!!
                            builder.append(annotatedVerse)
                            builder.addStyle(tagonestyle,  surahayah.get(0).startindex, surahayah.get(0).endindex)

                            //  it[0]!!.get(0).setAnootedStr(builder.toAnnotatedString())
                            newnewadapterlist[surahayah.get(0).ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())

                            println(builder.toAnnotatedString())
                        }
                    }



                }
            }

            for (NewMudhafEntity in surah!!) {
                val indexstart = NewMudhafEntity!!.startindex
                val indexend = NewMudhafEntity.endindex


                //  sifaspans = new BackgroundColorSpan(WBURNTUMBER);
                val str: AnnotatedString =        mudhafannotedlist(newnewadapterlist, NewMudhafEntity, indexstart, indexend)
                list.add(str)
            }


        }


        private fun setMultipleSifa(
            surahayah: List<SifaEntity>?,
            newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
            chapid: Int,
            spanhash: Map<String?, androidx.compose.ui.graphics.Color>
        ) {
            if(surahayah!!.size==2){

                val builder = AnnotatedString.Builder()
                var annotatedString: AnnotatedString
                val tagonecolor = spanhash["mousuf"]
                val tagonestyle = SpanStyle(
                    color = tagonecolor!!,                                )

                val annotatedVerse =
                    newnewadapterlist[surahayah.get(0).ayah - 1]?.get(0)!!.annotatedVerse!!
                builder.append(annotatedVerse)
                builder.addStyle(tagonestyle,  surahayah.get(0).startindex, surahayah.get(0).endindex)




                builder.addStyle(tagonestyle,  surahayah.get(1).startindex, surahayah.get(1).endindex)





                newnewadapterlist[surahayah.get(0).ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())

                println(builder.toAnnotatedString())
            }else        if(surahayah.size==3){

                val builder = AnnotatedString.Builder()
                var annotatedString: AnnotatedString
                val tagonecolor = spanhash["mudhaf"]
                val tagonestyle = SpanStyle(
                    color = tagonecolor!!,                                )

                val annotatedVerse =
                    newnewadapterlist[surahayah.get(0).ayah - 1]?.get(0)!!.annotatedVerse!!
                builder.append(annotatedVerse)
                builder.addStyle(tagonestyle,  surahayah.get(0).startindex, surahayah.get(0).endindex)




                builder.addStyle(tagonestyle,  surahayah.get(1).startindex, surahayah.get(1).endindex)

                builder.addStyle(tagonestyle,  surahayah.get(2).startindex, surahayah.get(2).endindex)





                newnewadapterlist[surahayah.get(0).ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())

                println(builder.toAnnotatedString())
            }


        }

        private fun seTmultiple(
            surahayah: List<NewMudhafEntity>,
            newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
            chapid: Int,
            spanhash: Map<String?, Color>,
            isdark: Boolean?

        ) {
            var tagonecolor: Color
           if(surahayah.size==2){
               if(isdark!!){

                   tagonecolor=        Color(ComposeConstant.MIDNIGHTBLUE)
               } else {
                   tagonecolor =  Color(ComposeConstant.WBURNTUMBER)
               }


               val builder = AnnotatedString.Builder()
               var annotatedString: AnnotatedString

               val tagonestyle = SpanStyle(
                   color = tagonecolor!!,                                )

               val annotatedVerse =
                   newnewadapterlist[surahayah.get(0).ayah - 1]?.get(0)!!.annotatedVerse!!
               builder.append(annotatedVerse)
               builder.addStyle(tagonestyle,  surahayah.get(0).startindex, surahayah.get(0).endindex)




               builder.addStyle(tagonestyle,  surahayah.get(1).startindex, surahayah.get(1).endindex)





               newnewadapterlist[surahayah.get(0).ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())

               println(builder.toAnnotatedString())
           }else        if(surahayah.size==3){
               if(isdark!!){

                   tagonecolor=        Color(ComposeConstant.MIDNIGHTBLUE)
               } else {
                   tagonecolor =  Color(ComposeConstant.WBURNTUMBER)
               }


               val builder = AnnotatedString.Builder()
               var annotatedString: AnnotatedString

               val tagonestyle = SpanStyle(
                   color = tagonecolor!!,                                )

               val annotatedVerse =
                   newnewadapterlist[surahayah.get(0).ayah - 1]?.get(0)!!.annotatedVerse!!
               builder.append(annotatedVerse)
               builder.addStyle(tagonestyle,  surahayah.get(0).startindex, surahayah.get(0).endindex)




               builder.addStyle(tagonestyle,  surahayah.get(1).startindex, surahayah.get(1).endindex)

               builder.addStyle(tagonestyle,  surahayah.get(2).startindex, surahayah.get(2).endindex)





               newnewadapterlist[surahayah.get(0).ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())

               println(builder.toAnnotatedString())
           }


        }

        private fun mudhafannotedlist(it: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>, mudhafindex: NewMudhafEntity, indexstart: Int, indexend: Int)
        :AnnotatedString{

            val spanhash: Map<String?, androidx.compose.ui.graphics.Color> =
                AnnotationUtility.stringForegroundColorSpanMap
            if (dark) {
                Constant.sifaspansDark = BackgroundColorSpan(Constant.WBURNTUMBER)
            } else {
                Constant.sifaspansDark = BackgroundColorSpan(Constant.CYANLIGHTEST)
            }
            val builder = AnnotatedString.Builder()
            var annotatedString: AnnotatedString
            annotatedString =
                it[mudhafindex.ayah - 1]!![0].annotatedVerse!!
            val source =  it[mudhafindex.ayah - 1]!![0].annotatedVerse!!
            // builder to attach metadata(link)
            builder.append(it[mudhafindex.ayah - 1]!![0].annotatedVerse!!)


            val tagonecolor =spanhash["mudhaf"]
            val tagonestyle = SpanStyle(
                color = tagonecolor!!,

            )





          builder.addStyle(tagonestyle, indexstart, indexend)
            return builder.toAnnotatedString()

        }




        fun setMudhaf(it: java.util.LinkedHashMap<Int, java.util.ArrayList<NewQuranCorpusWbw>>, chapid: Int)
        {

            val utils = Utils(QuranGrammarApplication.context!!)
            val surah = utils.getMudhafSurahNew(chapid)

            for (NewMudhafEntity in surah!!) {
                val indexstart = NewMudhafEntity!!.startindex
                val indexend = NewMudhafEntity.endindex


                //  sifaspans = new BackgroundColorSpan(WBURNTUMBER);
                mudhafannote(it, NewMudhafEntity, indexstart, indexend)
            }

        }

        private fun mudhafannote(
            it: java.util.LinkedHashMap<Int, java.util.ArrayList<NewQuranCorpusWbw>>,
            mudhafindex: NewMudhafEntity,
            indexstart: Int,
            indexend: Int
        ) {
            val spanhash: Map<String?, androidx.compose.ui.graphics.Color> =
                AnnotationUtility.stringForegroundColorSpanMap
            if (dark) {
                Constant.sifaspansDark = BackgroundColorSpan(Constant.WBURNTUMBER)
            } else {
                Constant.sifaspansDark = BackgroundColorSpan(Constant.CYANLIGHTEST)
            }
            val builder = AnnotatedString.Builder()
            var annotatedString: AnnotatedString
            annotatedString =
                it[mudhafindex.ayah - 1]!![0].annotatedVerse!!
            val source =  it[mudhafindex.ayah - 1]!![0].annotatedVerse!!
            // builder to attach metadata(link)
            builder.append(it[mudhafindex.ayah - 1]!![0].annotatedVerse!!)


            val tagonecolor =spanhash["mudhaf"]
            val tagonestyle = SpanStyle(
                color = tagonecolor!!,
                // textDecoration = TextDecoration.Underline
            )





            builder.addStyle(tagonestyle, indexstart, indexend)
            annotatedString=builder.toAnnotatedString()








        }

        fun setShart(
            hashlist: java.util.LinkedHashMap<Int, java.util.ArrayList<NewQuranCorpusWbw>>,
            surah_id: Int,
            isdark: Boolean?
        ) {
            val spanhash: Map<String?, androidx.compose.ui.graphics.Color> =
                AnnotationUtility.stringForegroundColorSpanMap
            val utils = Utils(QuranGrammarApplication.context!!)
            val surah = utils.getShartSurahNew(surah_id)
            //  final ArrayList<ShartEntity> surah = utils.getShartSurah(surah_id);
            //TO 9;118 IZA IN THE MEANING OF HEENA AND 9 122 IZA AS HEENA
            if (surah_id in 2..10 || surah_id in 58..114) {
                for (shart in surah!!) {
                    val indexstart = shart!!.indexstart
                    val indexend = shart.indexend
                    val shartsindex = shart.shartindexstart
                    val sharteindex = shart.shartindexend
                    val jawabstartindex = shart.jawabshartindexstart
                    val jawabendindex = shart.jawabshartindexend
                    try {
                    }
                    catch (e: ArrayIndexOutOfBoundsException) {
                        println(shart.surah.toString() + " " + shart.ayah)
                    }
                    //   spanIt(SpanType.BGCOLOR,spannableString, shart, indexstart, indexend, shartsindex, sharteindex, jawabstartindex, jawabendindex);
                    ColoredShart(
                        hashlist,
                        shart,
                        indexstart,
                        indexend,
                        shartsindex,
                        sharteindex,
                        jawabstartindex,
                        jawabendindex,
                        isdark!!
                    )
                }
            }
        }

        fun newnewHarfNasbDb(
            hashlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
            surah_id: Int,
            isdark: Boolean?
        ) {

            val utils = Utils(QuranGrammarApplication.context!!)
            val harfnasb = utils.getHarfNasbIndexesnew(surah_id)
            //TODO SURA10 7 INNA ISM INNALIZINA(0,5,6,9 AND KHABR IN 10;8 oolika(0,12,len33)
            if (surah_id == 2 || surah_id == 3 || surah_id == 4 || surah_id == 5 || surah_id == 6 || surah_id == 7 || surah_id == 8 || surah_id == 9 || surah_id == 10 || surah_id == 59 || surah_id == 60 || surah_id == 61 || surah_id == 62 || surah_id == 63 || surah_id == 64 || surah_id == 65 || surah_id == 66 || surah_id == 67 || surah_id == 68 || surah_id == 69 || surah_id == 70 || surah_id == 71 || surah_id == 72 || surah_id == 73 || surah_id == 74 || surah_id == 75 || surah_id == 76 || surah_id == 77 || surah_id == 78 || surah_id in 79..114) {
                var spannableverse: AnnotatedString
                val err = ArrayList<String>()
                for (nasb in harfnasb!!) {
                    val indexstart = nasb!!.indexstart
                    val indexend = nasb.indexend
                    val ismstartindex = nasb.ismstart
                    val ismendindex = nasb.ismend
                    val khabarstart = nasb.khabarstart
                    val khabarend = nasb.khabarend
                    val builder = AnnotatedString.Builder()
                    var annotatedString: AnnotatedString


                         val     annotatedVerse =
                        hashlist[nasb.ayah - 1]!![0].annotatedVerse!!

                        if (dark) {
                          //  Constant.harfinnaspanDark = BackgroundColorSpan(Constant.WBURNTUMBER)
                            harfinnaspanDark = Color(GREEN)
                             harfismspanDark = Color(ComposeConstant.BCYAN)
                           harfkhabarspanDark = Color(ComposeConstant.MARIGOLD)
                        } else {
                           harfinnaspanDark = Color(ComposeConstant.KASHMIRIGREEN)
                         harfismspanDark = Color(Constant.prussianblue)
                          harfkhabarspanDark = Color(ComposeConstant.deepburnsienna)
                        }




                    builder.append(annotatedVerse)
                    try {

                            builder.  addStyle(
                                style = SpanStyle(
                                    color = ComposeConstant.harfinnaspanDark!!,
                                    textDecoration = TextDecoration.Underline
                                ), start = indexstart, end = indexend
                            )



                            builder.  addStyle(
                                style = SpanStyle(
                                    color = ComposeConstant.harfinnaspanDark !!,
                                    textDecoration = TextDecoration.Underline
                                ), start = ismstartindex, end = ismendindex
                            )



                            builder.  addStyle(
                                style = SpanStyle(
                                    color = harfkhabarspanDark,
                                    textDecoration = TextDecoration.Underline
                                ), start = khabarstart, end = khabarend
                            )


                    }
                    catch (e: IndexOutOfBoundsException) {
                        //System.out.println(e.getMessage());
                    }
                    hashlist[nasb.ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())
                }
            }










        }


        fun setKana(
            hashlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
            surah_id: Int,
            isdark: Boolean?,
        ) {
            val utils = Utils(context!!.applicationContext)
            val kanalist = utils.getKananew(surah_id)
            val harfkana: Color
            val kanaism: Color
            val kanakhbar: Color
            if (dark) {
                harfkana = Color(ComposeConstant.GOLD)
                kanaism = Color(ComposeConstant.ORANGE400)
                kanakhbar = Color(android.graphics.Color.CYAN)
            } else {
                harfkana = Color(ComposeConstant.FORESTGREEN)
                kanaism = Color(ComposeConstant.KASHMIRIGREEN)
                kanakhbar = Color(ComposeConstant.WHOTPINK)
            }
            val builder=AnnotatedString.Builder()
            if (surah_id in 2..10 || surah_id in 59..114) {
                for (kana in kanalist!!) {
                    //     val spannableverse = corpusayahWordArrayList[kana!!.ayah - 1].spannableverse
                    val spannableverse =
                        hashlist[kana.ayah - 1]!![0].annotatedVerse!!
                    builder.append(spannableverse)



                    try {
                        if (spannableverse != null) {
                            builder.addStyle(
                                style = SpanStyle(
                                    color = harfkana,
                                    textDecoration = TextDecoration.Underline
                                ), start = kana.indexstart, end = kana.indexend
                            )
                        }
                        if (spannableverse != null) {
                            //    shart.setSpannedverse(spannableverse);

                            builder.addStyle(
                                style = SpanStyle(
                                    color = kanaism,
                                    textDecoration = TextDecoration.Underline
                                ), start = kana.ismkanastart, end = kana.ismkanaend
                            )
                        }
                        if (spannableverse != null) {
                            builder.addStyle(
                                style = SpanStyle(
                                    color = kanakhbar,
                                    textDecoration = TextDecoration.Underline
                                ), start = kana.khabarstart, end = kana.khabarend
                            )
                        }

                        //   shart.setSpannedverse(spannableverse);

                        //   shart.setSpannedverse(spannableverse);
                    }
                    catch (e: IndexOutOfBoundsException) {
                   //    System.out.println(e.getMessage());
                    }
                    try {
                        hashlist[kana.ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())
                    }catch (e: IllegalArgumentException){
                        println(e.message)
                    }
                }
            }
        }



    }






}

private fun ColoredShart(
    newnewadapterlist: LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>,
    shart: NewShartEntity,
    indexstart: Int,
    indexend: Int,
    shartsindex: Int,
    sharteindex: Int,
    jawabstartindex: Int,
    jawabendindex: Int,
    isdark: Boolean,
) {
    val spannableverse: AnnotatedString
    val spanhash: Map<String?, androidx.compose.ui.graphics.Color> =
        AnnotationUtility.stringForegroundColorSpanMap


    var tagonecolor: Color
    var tagtwocolor: Color
    var tagthreecolor: Color





    if (isdark) {
        tagonecolor= Color(Constant.GOLD)
        tagthreecolor= Color(Constant.ORANGE400)
        tagtwocolor  = Color(CYAN)
    } else {
       tagonecolor = Color(ComposeConstant.FORESTGREEN)
     tagtwocolor = Color(Constant.KASHMIRIGREEN)
       tagthreecolor= Color(Constant.WHOTPINK)
    }
    val builder = AnnotatedString.Builder()
    var annotatedString: AnnotatedString
    //val tagonecolor =spanhash["mudhaf"]
  /*  val tagonestyle = SpanStyle(
        color = ComposeConstant.harfshartspanDark!!,
        textDecoration =TextDecoration.Underline)
    val tagtwostyle = SpanStyle(
        color =  ComposeConstant.shartspanDark,
        textDecoration =TextDecoration.Underline)


    val tagthreestyle = SpanStyle(
        color =  ComposeConstant.jawabshartspanDark,
        textDecoration =TextDecoration.Underline)
*/












/*    builder.addStyle( tagonestyle,  indexstart, indexend)
    builder.addStyle( tagtwostyle,  shartsindex, sharteindex)
    builder.addStyle( tagthreestyle,  jawabstartindex, jawabendindex)*/





    //   spannableverse = corpusayahWordArrayList[shart.ayah - 1].spannableverse!!

    //   spannableString = SpannableString.valueOf(corpusayahWordArrayList.get(shart.getAyah() - 1).getSpannableverse());

    val annotatedVerse = newnewadapterlist[shart.ayah - 1]!![0].annotatedVerse!!
    builder.append(annotatedVerse)
    try {
        if (indexstart == 0 || indexstart > 0) {
            builder.  addStyle(
                style = SpanStyle(
                    color = tagonecolor,
                    textDecoration = TextDecoration.Underline
                ), start = indexstart, end = indexend
            )

        }
        if (shartsindex == 0 || shartsindex > 0) {
            builder.  addStyle(
                style = SpanStyle(
                    color = tagtwocolor,
                    textDecoration = TextDecoration.Underline
                ), start = shartsindex, end = sharteindex
            )
        }
        if (jawabstartindex == 0 || jawabstartindex > 0) {

            builder.  addStyle(
                style = SpanStyle(
                    color = tagthreecolor,
                    textDecoration = TextDecoration.Underline
                ), start = jawabstartindex, end = jawabendindex
            )

        }
    }
    catch (e: IndexOutOfBoundsException) {
        //System.out.println(e.getMessage());
    }
    newnewadapterlist[shart.ayah - 1]!!.get(0).setAnootedStr(builder.toAnnotatedString())
}
