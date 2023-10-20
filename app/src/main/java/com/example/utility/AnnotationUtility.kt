package com.example.utility

import android.content.Context

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import com.example.ComposeConstant


class AnnotationUtility(private var context: Context?) {
    var ayah = 0
    private var dark = true

    constructor(context: Context, suraid: Int, ayah: Int) : this(context) {

        this.ayah = ayah
    }

    companion object {
        val dark=false
        @JvmStatic
        fun AnnotatedStrings(
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
            wordno: Int,
        ): LinkedHashMap<AnnotatedString, Int> {
            var list= LinkedHashMap<AnnotatedString, Int>()
            var araone = araone
            var aratwo = aratwo
            var arathree = arathree
            var arafour = arafour
            var arafive = arafive
            var str: String
            val istagnull =
                tagone == null || tagtwo == null || tagthree == null || tagfour == null || tagfive == null
            val builder = AnnotatedString.Builder()
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
  val returstr=araone+aratwo+arathree+arafour+arafive
            val spanhash: Map<String?, Color> = stringForegroundColorSpanMap
            //  }else{
            //   spanhash = getColorSpanforPhrasesLight();
            //  }
            if (tagcounter == 1) {

                val source = araone
                // builder to attach metadata(link)
                builder.append(source)
                val start = source.indexOf(araone)
                val end = start + araone.length



                val tagonecolor = spanhash[tagone]
                val tagonestyle = SpanStyle(
                    color = tagonecolor!!,
                    // textDecoration = TextDecoration.Underline
                )





                builder.addStyle(tagonestyle, start, end)

                list[builder.toAnnotatedString()]=wordno

            }else

            if (tagcounter == 2) {

                val source = araone + aratwo
                // builder to attach metadata(link)
                builder.append(source)
                val start = source.indexOf(araone)
                val end = start + araone.length

                val twostart = source.indexOf(aratwo)
                val twoend = twostart + aratwo.length

                val tagonecolor = spanhash[tagone]
                val tagonestyle = SpanStyle(
                    color = tagonecolor!!,
                   // textDecoration = TextDecoration.Underline
                )

                val tagtwostyle = SpanStyle(
                    color = spanhash[tagtwo]!!,
                   // textDecoration = TextDecoration.Underline
                )



                builder.addStyle(tagonestyle, start, end)
                builder.addStyle(tagtwostyle, twostart, twoend)
                list[builder.toAnnotatedString()]=wordno

            } else if(tagcounter==3){
                val source = araone + aratwo+arathree
                // builder to attach metadata(link)
                builder.append(source)
                val start = source.indexOf(araone)
                val end = start + araone.length

                val twostart = source.indexOf(aratwo)
                val twoend = twostart + aratwo.length

                val threestart = source.indexOf(arathree)
                val threeend = threestart + aratwo.length

                val tagonecolor = spanhash[tagone]
                val tagonestyle = SpanStyle(
                    color = tagonecolor!!,
                   // textDecoration = TextDecoration.Underline
                )

                val tagtwostyle = SpanStyle(
                    color = spanhash[tagtwo]!!,
                   // textDecoration = TextDecoration.Underline
                )
                val tagstylethree = SpanStyle(
                    color = spanhash[tagthree]!!,
                   // textDecoration = TextDecoration.Underline
                )

                builder.addStyle(tagonestyle, start, end)
                builder.addStyle(tagtwostyle, twostart, twoend)
                builder.addStyle(tagstylethree, threestart, threeend)
                list[builder.toAnnotatedString()]=wordno
            } else if(tagcounter==4){

                val source = araone + aratwo+arathree+arafour
                // builder to attach metadata(link)
                builder.append(source)
                val start = source.indexOf(araone)
                val end = start + araone.length

                val twostart = source.indexOf(aratwo)
                val twoend = twostart + aratwo.length

                val threestart = source.indexOf(arathree)
                val threeend = threestart + aratwo.length

                val fourstart = source.indexOf(arafour)
                val fourend = fourstart + arafour.length


                val tagonestyle = SpanStyle(
                    color = spanhash[tagone]!!,
                   // textDecoration = TextDecoration.Underline
                )

                val tagtwostyle = SpanStyle(
                    color = spanhash[tagtwo]!!,
                   // textDecoration = TextDecoration.Underline
                )
                val tagstylethree = SpanStyle(
                    color = spanhash[tagthree]!!,
                   // textDecoration = TextDecoration.Underline
                )
                val tagstylefour = SpanStyle(
                    color = spanhash[tagfour]!!,
                   // textDecoration = TextDecoration.Underline
                )

                builder.addStyle(tagonestyle, start, end)
                builder.addStyle(tagtwostyle, twostart, twoend)
                builder.addStyle(tagstylethree, threestart, threeend)
                builder.addStyle(tagstylefour, fourstart, fourend)
                list[builder.toAnnotatedString()]=wordno
            } else if(tagcounter==5){

                val source = araone + aratwo+arathree+arafour+arafive
                // builder to attach metadata(link)
                builder.append(source)
                val start = source.indexOf(araone)
                val end = start + araone.length

                val twostart = source.indexOf(aratwo)
                val twoend = twostart + aratwo.length

                val threestart = source.indexOf(arathree)
                val threeend = threestart + aratwo.length

                val fourstart = source.indexOf(arafour)
                val fourend = fourstart + arafour.length

                val fivestart = source.indexOf(arafour)
                val fiveend = fivestart + arafour.length
                val tagonestyle = SpanStyle(
                    color = spanhash[tagone]!!,
                   // textDecoration = TextDecoration.Underline
                )

                val tagtwostyle = SpanStyle(
                    color = spanhash[tagtwo]!!,
                   // textDecoration = TextDecoration.Underline
                )
                val tagstylethree = SpanStyle(
                    color = spanhash[tagthree]!!,
                   // textDecoration = TextDecoration.Underline
                )
                val tagstylefour = SpanStyle(
                    color = spanhash[tagfour]!!,
                   // textDecoration = TextDecoration.Underline
                )
                val tagstylefive = SpanStyle(
                    color = spanhash[tagfive]!!,
                   // textDecoration = TextDecoration.Underline
                )
                builder.addStyle(tagonestyle, start, end)
                builder.addStyle(tagtwostyle, twostart, twoend)
                builder.addStyle(tagstylethree, threestart, threeend)
                builder.addStyle(tagstylefour, fourstart, fourend)
                builder.addStyle(tagstylefive, fivestart, fiveend)
                list[builder.toAnnotatedString()]=wordno

            }


            else{
                builder.append(returstr)

            }
             return list
            //return builder.toAnnotatedString()
        }


        @JvmStatic
        val stringForegroundColorSpanMap: Map<String?, androidx.compose.ui.graphics.Color>
            get() {
                val spanhash: MutableMap<String?, Color> = HashMap()
                if (dark) {
                    spanhash["PN"] = ComposeConstant.propernounspanDark
                    spanhash["REL"] = ComposeConstant.relativespanDark
                    spanhash["DEM"] = ComposeConstant.demonstrativespanDark
                    spanhash["N"] = ComposeConstant.nounspanDark
                    spanhash["PRON"] = ComposeConstant.pronounspanDark
                    spanhash["DET"] = ComposeConstant.determinerspanDark
                    spanhash["V"] = ComposeConstant.verbspanDark
                    spanhash["P"] = ComposeConstant.prepositionspanDark
                    spanhash["T"] = ComposeConstant.timeadverbspanDark
                    spanhash["LOC"] = ComposeConstant.locationadverspanDark
                    spanhash["ADJ"] = ComposeConstant.adjectivespanDark
                    spanhash["VN"] = ComposeConstant.verbalnounspanDark
                    spanhash["EMPH"] =
                        ComposeConstant.emphspanDark // "Emphatic lām prefix(لام التوكيد) ",
                    spanhash["IMPV"] =
                        ComposeConstant.lamimpvspanDark // "Imperative lāmprefix(لام الامر)",
                    spanhash["PRP"] =
                        ComposeConstant.lamtaleelspandDark // "Purpose lāmprefix(لام التعليل)",
                    spanhash["SUB"] =
                        ComposeConstant.masdariaspanDark // "	Subordinating conjunction(حرف مصدري)",
                    spanhash["ACC"] =
                        ComposeConstant.nasabspanDark // "	Accusative particle(حرف نصب)",
                    spanhash["ANS"] =
                        ComposeConstant.answerspanDark // "	Answer particle	(حرف جواب)",
                    spanhash["CAUS"] =
                        ComposeConstant.harfsababiaspanDark // "Particle of cause	(حرف سببية)",
                    spanhash["CERT"] =
                        ComposeConstant.certainityspanDark // "Particle of certainty	(حرف تحقيق)",
                    spanhash["CIRC"] =
                        ComposeConstant.halspanDark // "Circumstantial particle	(حرف حال)",
                    spanhash["CONJ"] =
                        ComposeConstant.particlespanDark // "Coordinating conjunction(حرف عطف)",
                    spanhash["COND"] =
                        ComposeConstant.harfshartspanDark // "Conditional particle(حرف شرط)",
                    spanhash["AMD"] =
                        ComposeConstant.particlespanDark // "	Amendment particle(حرف استدراك)	",
                    spanhash["AVR"] =
                        ComposeConstant.particlespanDark // "	Aversion particle	(حرف ردع)",
                    spanhash["COM"] =
                        ComposeConstant.particlespanDark // "	Comitative particle	(واو المعية)",
                    spanhash["EQ"] =
                        ComposeConstant.particlespanDark // "	Equalization particle(حرف تسوية)",
                    spanhash["EXH"] =
                        ComposeConstant.particlespanDark // "	Exhortation particle(حرف تحضيض)",
                    spanhash["EXL"] =
                        ComposeConstant.particlespanDark // "	Explanation particle(حرف تفصيل)",
                    spanhash["EXP"] =
                        ComposeConstant.particlespanDark // "	Exceptive particle	(أداة استثناء)",
                    spanhash["FUT"] =
                        ComposeConstant.particlespanDark // "	Future particle	(حرف استقبال)",
                    spanhash["INC"] =
                        ComposeConstant.particlespanDark // "	Inceptive particle	(حرف ابتداء)",
                    spanhash["INT"] =
                        ComposeConstant.particlespanDark // "	Particle of interpretation(حرف تفسير)",
                    spanhash["RET"] =
                        ComposeConstant.particlespanDark // "	Retraction particle	(حرف اضراب)",
                    spanhash["PREV"] =
                        ComposeConstant.particlespanDark // "Preventive particle	(حرف كاف)",
                    spanhash["VOC"] =
                        ComposeConstant.particlespanDark // "	Vocative particle	(حرف نداء)",
                    spanhash["INL"] =
                        ComposeConstant.particlespanDark // "	Quranic initials(	(حروف مقطعة	";
                    spanhash["INTG"] =
                        ComposeConstant.interrogativespanDark // "Interogative particle	(حرف استفهام)",
                    spanhash["NEG"] =
                        ComposeConstant.negativespanDark // "	Negative particle(حرف نفي)",
                    spanhash["PRO"] =
                        ComposeConstant.prohibitionspanDark // "	Prohibition particle(حرف نهي)",
                    spanhash["REM"] =
                        ComposeConstant.resumtionspanDark // "	Resumption particle	(حرف استئنافية)",
                    spanhash["RES"] =
                        ComposeConstant.restrictivespanDark // "	Restriction particle(أداة حصر)",
                    spanhash["RSLT"] =
                        ComposeConstant.resultparticlespanDark // "Result particle(حرف واقع في جواب الشرط)",
                    spanhash["SUP"] =
                        ComposeConstant.supplementspoanDark // "	Supplemental particle	(حرف زائد)",
                    spanhash["SUR"] =
                        ComposeConstant.surprisespanDark // "	Surprise particle	(حرف فجاءة)",
                } else {
                    spanhash["PN"] = ComposeConstant.propernounspanLight
                    spanhash["REL"] = ComposeConstant.relativespanLight
                    spanhash["DEM"] = ComposeConstant.demonstrativespanLight
                    spanhash["N"] = ComposeConstant.nounspanLight
                    spanhash["PRON"] = ComposeConstant.pronounspanLight
                    spanhash["DET"] = ComposeConstant.determinerspanLight
                    spanhash["V"] = ComposeConstant.verbspanLight
                    spanhash["P"] = ComposeConstant.prepositionspanLight
                    spanhash["T"] = ComposeConstant.timeadverbspanLight
                    spanhash["LOC"] = ComposeConstant.locationadverspanLight
                    spanhash["ADJ"] = ComposeConstant.adjectivespanLight
                    spanhash["VN"] = ComposeConstant.verbalnounspanLight
                    spanhash["EMPH"] =
                        ComposeConstant.emphspanLight // "Emphatic lām prefix(لام التوكيد) ",
                    spanhash["IMPV"] =
                        ComposeConstant.lamimpvspanLight // "Imperative lāmprefix(لام الامر)",
                    spanhash["PRP"] =
                        ComposeConstant.lamtaleelspandLight // "Purpose lāmprefix(لام التعليل)",
                    spanhash["SUB"] =
                        ComposeConstant.masdariaspanLight // "	Subordinating conjunction(حرف مصدري)",
                    spanhash["ACC"] =
                        ComposeConstant.nasabspanLight // "	Accusative particle(حرف نصب)",
                    spanhash["ANS"] =
                        ComposeConstant.answerspanLight // "	Answer particle	(حرف جواب)",
                    spanhash["CAUS"] =
                        ComposeConstant.harfsababiaspanLight // "Particle of cause	(حرف سببية)",
                    spanhash["CERT"] =
                        ComposeConstant.certainityspanLight // "Particle of certainty	(حرف تحقيق)",
                    spanhash["CIRC"] =
                        ComposeConstant.particlespanLight // "Circumstantial particle	(حرف حال)",
                    spanhash["CONJ"] =
                        ComposeConstant.particlespanLight // "Coordinating conjunction(حرف عطف)",
                    spanhash["COND"] =
                        ComposeConstant.eqspanlight // "Conditional particle(حرف شرط)",
                    spanhash["AMD"] =
                        ComposeConstant.ammendedparticle // "	Amendment particle(حرف استدراك)	",
                    spanhash["AVR"] =
                        ComposeConstant.particlespanLight // "	Aversion particle	(حرف ردع)",
                    spanhash["COM"] =
                        ComposeConstant.particlespanLight // "	Comitative particle	(واو المعية)",
                    spanhash["EQ"] =
                        ComposeConstant.particlespanLight // "	Equalization particle(حرف تسوية)",
                    spanhash["EXH"] =
                        ComposeConstant.particlespanLight // "	Exhortation particle(حرف تحضيض)",
                    spanhash["EXL"] =
                        ComposeConstant.particlespanLight // "	Explanation particle(حرف تفصيل)",
                    spanhash["EXP"] =
                        ComposeConstant.particlespanLight // "	Exceptive particle	(أداة استثناء)",
                    spanhash["FUT"] =
                        ComposeConstant.particlespanLight // "	Future particle	(حرف استقبال)",
                    spanhash["INC"] =
                        ComposeConstant.nasabspanLight // "	Inceptive particle	(حرف ابتداء)",
                    spanhash["INT"] =
                        ComposeConstant.particlespanLight // "	Particle of interpretation(حرف تفسير)",
                    spanhash["RET"] =
                        ComposeConstant.particlespanLight // "	Retraction particle	(حرف اضراب)",
                    spanhash["PREV"] =
                        ComposeConstant.inceptivepartile // "Preventive particle	(حرف كاف)",
                    spanhash["VOC"] =
                        ComposeConstant.particlespanLight // "	Vocative particle	(حرف نداء)",
                    spanhash["INL"] =
                        ComposeConstant.particlespanLight // "	Quranic initials(	(حروف مقطعة	";
                    spanhash["INTG"] =
                        ComposeConstant.interrogativespanLight // "Interogative particle	(حرف استفهام)",
                    spanhash["NEG"] =
                        ComposeConstant.negativespanLight // "	Negative particle(حرف نفي)",
                    spanhash["PRO"] =
                        ComposeConstant.prohibitionspanLight // "	Prohibition particle(حرف نهي)",
                    spanhash["REM"] =
                        ComposeConstant.particlespanLight // "	Resumption particle	(حرف استئنافية)",
                    spanhash["RES"] =
                        ComposeConstant.restrictivespanLight // "	Restriction particle(أداة حصر)",
                    spanhash["RSLT"] =
                        ComposeConstant.resultparticlespanLight // "Result particle(حرف واقع في جواب الشرط)",
                    spanhash["SUP"] =
                        ComposeConstant.supplementspanLight // "	Supplemental particle	(حرف زائد)",
                    spanhash["SUR"] =
                        ComposeConstant.surprisespanLight // "	Surprise particle	(حرف فجاءة)",
                }
                return spanhash
            }
    }
}




