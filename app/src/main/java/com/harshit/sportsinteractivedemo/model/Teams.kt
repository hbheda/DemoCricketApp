package com.harshit.sportsinteractivedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Teams(var id: Int =0) {

    @SerializedName("Name_Full")
    @Expose
    var nameFull: String? = null

    @SerializedName("Name_Short")
    @Expose
    var nameShort: String? = null

    @SerializedName("Players")
    @Expose
    var players:MutableMap<String?, Players?>? = null

}