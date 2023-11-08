package com.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.alorma.compose.settings.storage.preferences.IntPreferenceSettingValueState
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.viewmodels.CardsViewModel
import com.viewmodels.ExpandableVerseViewModel
import com.viewmodels.RootModel
import com.viewmodels.VerseModel

class newViewModelFactory(private val dbname: Int,private val isdark: Boolean) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        VerseModel(dbname,isdark) as T
}

class CardViewModelFactory(
    private val dbname: String,
    private val nounroot: String,
    private val isharf: Boolean,
 private val  selectTranslation: IntPreferenceSettingValueState
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        CardsViewModel(dbname, nounroot, isharf,selectTranslation) as T
}





class RootViewModelFactory(
    private val dbname: String,


    ) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        RootModel(dbname) as T
}

class VerseAnalysisFctory(
    private val chapterid:Int,
    private val verseid:Int,
    private val thememode: Boolean,
    private val wbwchoice: IntPreferenceSettingValueState


    ) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        ExpandableVerseViewModel(chapterid,verseid,thememode,wbwchoice) as T
}




class surahViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        QuranVIewModel() as T
}