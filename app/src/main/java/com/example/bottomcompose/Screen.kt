package com.example.bottomcompose

import com.example.mushafconsolidated.R

sealed class Screen(val titleResId: Int, val route: String) {
    object Home : Screen(R.string.home, "Home")
    object Verses : Screen(R.string.verses, "Quran")
    object Movies : Screen(R.string.movies, "Movies Post")
    object Words : Screen(R.string.books, "Books")
    object Profile : Screen(R.string.Verb, "Profile")

    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                Home.route -> Home
                Verses.route -> Verses
                Movies.route -> Movies
                Words.route -> Words
                Profile.route -> Profile
                else -> Home
            }
        }
    }
}