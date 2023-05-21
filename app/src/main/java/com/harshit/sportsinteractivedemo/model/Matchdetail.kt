package com.harshit.sportsinteractivedemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Matchdetail(var id: Int =0) {

    @SerializedName("Team_Home")
    @Expose
    var teamHome: String = ""

    @SerializedName("Team_Away")
    @Expose
    var teamAway: String = ""

    @SerializedName("Match")
    @Expose
    var match: Match? = null

    @SerializedName("Venue")
    @Expose
    var venue: Venue? = null

    /*@SerializedName("Series")
    @Expose
    var series: Series? = null

    @SerializedName("Officials")
    @Expose
    var officials: Officials? = null*/

    @SerializedName("Weather")
    @Expose
    var weather: String? = null

    @SerializedName("Tosswonby")
    @Expose
    var tosswonby: String? = null

    @SerializedName("Status")
    @Expose
    var status: String? = null

    @SerializedName("Status_Id")
    @Expose
    var statusId: String? = null

    @SerializedName("Player_Match")
    @Expose
    var playerMatch: String? = null

    @SerializedName("Result")
    @Expose
    var result: String? = null

    @SerializedName("Winningteam")
    @Expose
    var winningteam: String? = null

    @SerializedName("Winmargin")
    @Expose
    var winmargin: String? = null

    @SerializedName("Equation")
    @Expose
    var equation: String? = null
}