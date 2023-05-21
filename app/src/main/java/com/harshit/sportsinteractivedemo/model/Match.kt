package com.harshit.sportsinteractivedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Match(var ID: Int) {

    @SerializedName("Date")
    @Expose
    var date: String? = null

    @SerializedName("Time")
    @Expose
    var time: String? = null
}