package com.harshit.sportsinteractivedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseData(var Matchdetail: Matchdetail){

    @SerializedName("Teams")
    @Expose
    var teams:MutableMap<String?, Teams?>? = null

}
