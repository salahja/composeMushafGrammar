package com.example.searchwidgetdemo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Utils
import com.example.utility.QuranGrammarApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import okhttp3.internal.http.HTTP_INSUFFICIENT_STORAGE

class SearchViewModel : ViewModel() {

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =  mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

     private val _searchText= MutableStateFlow("")
     val searchText =_searchText.asStateFlow()


    private val _issearching= MutableStateFlow(value = false)
    val isSearching =_issearching.asStateFlow()
    val utils= Utils(QuranGrammarApplication.context)
    val chapterlist=     utils.getAllAnaChapters()

    private val _ischapters= MutableStateFlow(chapterlist)







    val chapters =searchText
        .combine(_ischapters){text,chapters->
        if(text.isBlank()) {
            chapters
        } else {
            chapters!!.filter {
                it!!.doesMatchSearchQuery (text)
            }
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _ischapters.value

        )
    fun onSearchTextChange(text :String){
        _searchText.value = text
    }


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

}