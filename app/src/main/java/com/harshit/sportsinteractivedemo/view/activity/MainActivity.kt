package com.harshit.sportsinteractivedemo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.harshit.sportsinteractivedemo.R
import com.harshit.sportsinteractivedemo.databinding.ActivityMainBinding
import com.harshit.sportsinteractivedemo.factories.MatchDataFactory
import com.harshit.sportsinteractivedemo.repository.MatchDataRepo
import com.harshit.sportsinteractivedemo.retrofit.ApiInterface
import com.harshit.sportsinteractivedemo.utils.Common
import com.harshit.sportsinteractivedemo.utils.Constant
import com.harshit.sportsinteractivedemo.viewModel.MainActivityViewModel
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    lateinit var mainActivityViewModel: MainActivityViewModel
    var apiInterface: ApiInterface? = null

    var currentMatch = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        activityMainBinding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        init()
        setObserver()

    }

    private fun init(){

        apiInterface = ApiInterface.getInstance()
        val matchDataRepo = MatchDataRepo(apiInterface!!)

        mainActivityViewModel = ViewModelProvider(
            this,
            MatchDataFactory(matchDataRepo)
        )[MainActivityViewModel::class.java]

        activityMainBinding.btnIndVsNz.setOnClickListener {
            if (Common.checkForInternet(this)) {

                currentMatch = Constant.MATCH_IND_VS_NZ
                mainActivityViewModel.getMatchData(currentMatch)

            } else {
                Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
            }
        }

        activityMainBinding.btnSaVsPk.setOnClickListener {
            if (Common.checkForInternet(this)) {

                currentMatch = Constant.MATCH_SA_VS_PK
                mainActivityViewModel.getMatchData(currentMatch)

            } else {
                Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
            }
        }

        activityMainBinding.btnPlayerDetails.setOnClickListener {
            if(currentMatch.isNotEmpty()) {
                val intent = Intent(this@MainActivity, TeamPlayersActivity::class.java)
                intent.putExtra("match", currentMatch)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please select a match", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setObserver(){

        mainActivityViewModel.errorMessage.observe(this){ errorMessage->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        mainActivityViewModel.matchData.observe(this){ responseData->

            activityMainBinding.txtMatch.text =
                buildString {
                    append(responseData.teams?.get(responseData.Matchdetail.teamAway)?.nameFull)
                    append(" Vs ")
                    append(responseData.teams?.get(responseData.Matchdetail.teamHome)?.nameFull)
                }
            activityMainBinding.txtMatchDetails.text =
                buildString {
                    append("Match Date and Time: ")
                    append(responseData.Matchdetail.match?.date)
                    append(" ")
                    append(responseData.Matchdetail.match?.time)
                }

            activityMainBinding.txtMatchVenue.text =
                buildString {
                    append("Venue: ")
                    append(responseData.Matchdetail.venue?.name)
                }

        }

    }
}
//https://demo.sportz.io/nzin01312019187360.json
//https://demo.sportz.io/sapk01222019186652.json