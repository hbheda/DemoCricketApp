package com.harshit.sportsinteractivedemo.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harshit.sportsinteractivedemo.repository.MatchDataRepo
import com.harshit.sportsinteractivedemo.viewModel.MainActivityViewModel


class MatchDataFactory (private val matchDataRepo: MatchDataRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(this.matchDataRepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}