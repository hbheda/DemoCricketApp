package com.harshit.sportsinteractivedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Bowling(var ID: Int) {

    @SerializedName("Style")
    @Expose
    var style: String? = null

}