package com.example.viewmodels

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString

@Immutable
data class ExpandableCardModelSpannableLists(
    val id: Int,
    val title: String,
    val lemma: String,
    val vlist: ArrayList<AnnotatedString>
)