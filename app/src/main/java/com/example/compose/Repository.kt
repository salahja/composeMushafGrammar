package com.example.compose

import androidx.lifecycle.LiveData
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.model.QuranCorpusWbw

interface Repository {

    fun getAllPosts(): LiveData<List<QuranCorpusWbw>>

    fun getAllOwnedPosts(): LiveData<List<ChaptersAnaEntity>>


    fun deleteAll()
}