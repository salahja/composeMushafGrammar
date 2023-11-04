package com.example.mushafconsolidated.Entities
import android.text.SpannableString
import androidx.room.Ignore


class RootNounDetails {
    @Ignore
    var verses: SpannableString? = null
    var surah: Int = 0
    var ayah: Int = 0
    var token: Int = 0
    var araword: String? = null
    var lemma_a: String? = null
    var tag: String? = null
    var propone: String? = null
    var proptwo: String? = null
    var gendernumber: String? = null
    var cases: String? = null
    var details: String? = null
    var root_a: String? = null


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