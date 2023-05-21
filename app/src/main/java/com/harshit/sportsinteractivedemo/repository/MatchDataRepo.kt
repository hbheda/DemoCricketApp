package com.harshit.sportsinteractivedemo.repository

import com.harshit.sportsinteractivedemo.retrofit.ApiInterface

class MatchDataRepo(private val apiInterface: ApiInterface) {

    suspend fun matchData(matchUrl: String) = apiInterface.matchData(matchUrl)
}