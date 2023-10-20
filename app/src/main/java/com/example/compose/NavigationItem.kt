package com.example.compose

import com.example.mushafconsolidated.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_book, "Home")
    object Verses : NavigationItem("verses", R.drawable.baseline_audiotrack_24, "Quran")
    object Conjugator : NavigationItem("conjugator", R.drawable.ic_action_voice_search, "Conjugator")
    object Books : NavigationItem("books", R.drawable.ic_book, "Books")
    object Conjugation : NavigationItem("conjugation", R.drawable.ic_baseline_construction_24, "Conjugation")
}

