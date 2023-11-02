package com.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.viewmodels.CardsViewModel
import com.viewmodels.RootModel
import com.viewmodels.VerseModel

class newViewModelFactory(private val dbname: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        VerseModel(dbname) as T
}

class CardViewModelFactory(
    private val dbname: String,
    private val nounroot: String,
    private val isharf: Boolean
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        CardsViewModel(dbname, nounroot, isharf) as T
}


class RootViewModelFactory(
    private val dbname: String,


    ) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        RootModel(dbname) as T
}




class surahViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        QuranVIewModel() as T
}