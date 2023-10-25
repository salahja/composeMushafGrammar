package com.example.compose

import android.text.SpannableString
import androidx.compose.runtime.Immutable
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.model.QuranCorpusWbw

@Immutable
data class QuranArrays(
 val newnewadapterlist : LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>> =  LinkedHashMap(),
    val corpusSurahWord: List<QuranCorpusWbw> =listOf<QuranCorpusWbw>(),
    val quranbySurah: List<QuranEntity> = listOf<QuranEntity>(),
    val chapterlist: List<ChaptersAnaEntity> = listOf<ChaptersAnaEntity>()
)