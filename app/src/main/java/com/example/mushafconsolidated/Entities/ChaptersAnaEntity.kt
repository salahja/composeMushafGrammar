package com.example.mushafconsolidated.Entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(indices = {@Index(value = {"chapterid"}, unique = true)})
// @Entity(tableName = "ChaptersAnaEntity",indices = {@Index(value = {"chapterid"}, unique = true)},foreignKeys = @ForeignKey(entity=ChaptersAnaEntity.class, parentColumns="chapterid", childColumns="muqattatid"))
@Entity(tableName = "chaptersana")
data class ChaptersAnaEntity(
    @field:ColumnInfo(defaultValue = "0") var revelationnumber: Int,
    @field:ColumnInfo(
        defaultValue = "0"
    ) var serialnumber: Int,
    var namearabic: String,
    var nameurdu: String?,
    var nameenglish: String,
    @field:ColumnInfo(
        defaultValue = "1"
    ) var ismakki: Int,
    @field:ColumnInfo(defaultValue = "0") var versescount: Int,
    @field:ColumnInfo(
        defaultValue = "0"
    ) var rukucount: Int,
    var abjadname: String,
    var arahsfall: String,
    var versesparahdiv: String?,
    var sajdaverses: String?,
    var cumversescount: Int, // @Entity(indices = chapterid)
    @field:PrimaryKey(autoGenerate = true) var chapterid: Int,
    var part_no: Int
) {



    init {
        chapterid = chapterid
        this.part_no = part_no
    }

    fun doesMatchSearchQuery(query :String) :Boolean{
          val matchingCombinations= listOf(
              "$abjadname$nameenglish",
              "$abjadname $nameenglish",
              "${abjadname.first()} ${nameenglish.first()}"
          )
      return matchingCombinations.any{
          it.contains(query,ignoreCase = true)
      }
    }
}



