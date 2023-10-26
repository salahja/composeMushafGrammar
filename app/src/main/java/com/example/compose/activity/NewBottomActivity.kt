package com.example.compose.activity

import android.content.res.TypedArray
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.bottomcompose.JetRedditApp
import com.example.mushafconsolidated.Activityimport.BaseActivity
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.R
import com.example.mushafconsolidated.quranrepo.QuranVIewModel

class NewBottomActivity : BaseActivity(){
    private val viewModel by viewModels<QuranVIewModel>()
    private lateinit var allAnaChapters: List<ChaptersAnaEntity?>
    private lateinit var imgs: TypedArray
    lateinit var mainViewModel: QuranVIewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity

        setTheme(R.style.Theme_Browns)
        super.onCreate(savedInstanceState)

        setContent {
            JetRedditApp(viewModel)
        }
    }
}