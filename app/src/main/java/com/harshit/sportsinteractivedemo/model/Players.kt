package com.harshit.sportsinteractivedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Players(var id: Int =0) {

    @SerializedName("Position")
    @Expose
    var position: Int? = null

    @SerializedName("Name_Full")
    @Expose
    var nameFull: String? = null

    @SerializedName("Iskeeper")
    @Expose
    var isKeeper: Boolean = false

    @SerializedName("Iscaptain")
    @Expose
    var isCaptain: Boolean = false

    @SerializedName("Batting")
    @Expose
    var batting: Batting? = null

    @SerializedName("Bowling")
    @Expose
    var bowling: Bowling? = null

}