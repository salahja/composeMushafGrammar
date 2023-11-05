package com.example.mushafconsolidated.Entities
import android.text.SpannableString
import androidx.room.Ignore


class RootNounDetails {
    @Ignore

    var verses: SpannableString? = null
    var arabic: String? = null
    var lemma: String? = null
    var araone: String? = null
    var aratwo: String? = null
    var arathree: String? = null
    var arafour: String? = null
    var arafive: String? = null
    var tagone: String? = null
    var tagtwo: String? = null
    var tagthree: String? = null
    var tagfour: String? = null
    var tagfive: String? = null



    var surah: Int = 0
    var ayah: Int = 0
    var wordno: Int = 0
    var wordcount: Int = 0

    var tag: String? = null
    var propone: String? = null
    var proptwo: String? = null
    var gendernumber: String? = null
    var cases: String? = null
    var details: String? = null



    var abjadname: String? = null
    var namearabic: String? = null
    var nameenglish: String? = null
    var qurantext: String? = null
    var translation: String? = null
    var ar_irab_two:String?=null
    var en_arberry:String?=null
    var en_jalalayn:String?=null
    var en_transliteration:String?=null
    var tafsir_kathir: String? = null
    var en: String? = null
    constructor()





}