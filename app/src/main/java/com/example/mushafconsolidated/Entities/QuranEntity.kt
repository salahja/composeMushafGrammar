package com.example.mushafconsolidated.Entities

import android.text.SpannableStringBuilder
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "qurans",
    foreignKeys = [ForeignKey(
        entity = ChaptersAnaEntity::class,
        parentColumns = ["chapterid"],
        childColumns = ["surah"],
        onDelete = CASCADE
    )]
)
data class QuranEntity constructor(

    var surah: Int,
    var ayah: Int,
    var page: Int,
    var quarter: Int,
    var hizb: Int,
    var juz: Int,
    var qurantext: String,
    var passage_no: Int,
    var has_prostration: Int,
    var translation: String,
    var en_transliteration: String,
    var en_jalalayn: String,
    var en_arberry: String,
    var ar_irab_two: String,
    var ur_jalalayn: String,
    var ur_junagarhi: String,
    var tafsir_kathir: String,
    var quranclean: String,
    @field:PrimaryKey(
        autoGenerate = true
    ) var docid: Int,


    ) {

   /* init {
        en_transliteration = en_transliteration
        quranclean=quranclean
        translation=translation
        tafsir_kathir=tafsir_kathir
    }
*/
    fun doesMatchSearchQuery(query :String) :Boolean{
        val matchingCombinations= listOf(
/*
          "$chapterid",
              "$abjadname$nameenglish",
              "$abjadname $nameenglish",
              "${abjadname.first()} ${nameenglish.first()}   ${chapterid}
 */

           // "$ayah",
          //  "$quranclean$translation",
          //  "$quranclean$ayah",
            "$ayah",
           "${ayah}"
        )
        return matchingCombinations.any{
            it.contains(query,ignoreCase = true)
        }
    }

}