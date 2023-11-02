package com.viewmodels

import androidx.compose.runtime.Immutable
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.NounCorpus
import com.example.mushafconsolidated.Entities.RootVerbDetails
import com.example.mushafconsolidated.model.QuranCorpusWbw

@Immutable
data class AllRootWords(
    val verbrootlist: List<RootVerbDetails> =listOf<RootVerbDetails>(),
    val corpusSurahWordlist: List<QuranCorpusWbw> =listOf<QuranCorpusWbw>(),
    val chapterlist: List<ChaptersAnaEntity?> = listOf<ChaptersAnaEntity>(),
    val nounlist: List<NounCorpus?> = listOf<NounCorpus>(),


)