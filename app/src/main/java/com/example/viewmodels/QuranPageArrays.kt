package com.example.viewmodels

import androidx.compose.runtime.Immutable
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.Page

@Immutable
data class QuranPageArrays(
    val qurnaPages: List<Page> = ArrayList<Page>(),
    val chapterlist: List<ChaptersAnaEntity> = listOf<ChaptersAnaEntity>()
    )

