package com.harshit.sportsinteractivedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Venue(var Id: Int) {

    @SerializedName("Name")
    @Expose
    var name: String? = null
}