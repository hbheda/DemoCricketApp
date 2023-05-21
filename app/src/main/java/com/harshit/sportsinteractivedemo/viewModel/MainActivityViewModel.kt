package com.harshit.sportsinteractivedemo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harshit.sportsinteractivedemo.model.Matchdetail
import com.harshit.sportsinteractivedemo.model.ResponseData
import com.harshit.sportsinteractivedemo.repository.MatchDataRepo
import kotlinx.coroutines.*

class MainActivityViewModel(private val matchDataRepo: MatchDataRepo) : ViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName

    val errorMessage = MutableLiveData<String>()
    val matchData = MutableLiveData<ResponseData>()

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getMatchData(matchUrl: String) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = matchDataRepo.matchData(matchUrl)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    matchData.postValue(response.body())
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        try {
            errorMessage.value = message
        } catch (e: Exception) {
            Log.e(TAG,"ERROR: ${e.message.toString()}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}