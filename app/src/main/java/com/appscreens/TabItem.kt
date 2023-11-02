package com.appscreens

import androidx.compose.runtime.Composable
import com.example.mushafconsolidated.R

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: String, var screen: ComposableFun) {
    object Music : TabItem(com.google.android.exoplayer2.R.drawable.exo_ic_audiotrack, "Music", { MusicScreen() })
    object Movies : TabItem(R.drawable.ic_action_jump, "Movies", { MoviesScreen() })
    object Books : TabItem(R.drawable.ic_book, "Books", { BooksScreen() })
}