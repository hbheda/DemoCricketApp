package com.harshit.sportsinteractivedemo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.harshit.sportsinteractivedemo.R
import com.harshit.sportsinteractivedemo.adapters.PlayersAdapter
import com.harshit.sportsinteractivedemo.databinding.ActivityMainBinding
import com.harshit.sportsinteractivedemo.databinding.ActivityTeamPlayersBinding
import com.harshit.sportsinteractivedemo.factories.MatchDataFactory
import com.harshit.sportsinteractivedemo.model.Players
import com.harshit.sportsinteractivedemo.repository.MatchDataRepo
import com.harshit.sportsinteractivedemo.retrofit.ApiInterface
import com.harshit.sportsinteractivedemo.utils.Common
import com.harshit.sportsinteractivedemo.viewModel.MainActivityViewModel

class TeamPlayersActivity : AppCompatActivity() {

    private lateinit var activityTeamPlayersBinding: ActivityTeamPlayersBinding

    lateinit var mainActivityViewModel: MainActivityViewModel
    var apiInterface: ApiInterface? = null

    var playersList = mutableListOf<Players>()
    var tempPlayersList = mutableListOf<Players>()
    lateinit var adapter :PlayersAdapter

    var teamHome = 0
    var teamAway = 0

    var currentMatch = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_team_players)
        activityTeamPlayersBinding =  ActivityTeamPlayersBinding.inflate(layoutInflater)
        setContentView(activityTeamPlayersBinding.root)

        currentMatch = intent.extras?.getString("match").toString()

        init()
        setObserver()
    }

    override fun onResume() {
        super.onResume()
        if (Common.checkForInternet(this)) {

            mainActivityViewModel.getMatchData(currentMatch)

        } else {
            Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {

        apiInterface = ApiInterface.getInstance()
        val matchDataRepo = MatchDataRepo(apiInterface!!)

        mainActivityViewModel = ViewModelProvider(
            this,
            MatchDataFactory(matchDataRepo)
        )[MainActivityViewModel::class.java]

        activityTeamPlayersBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when(tab?.id){
                    0->{
                        tempPlayersList.clear()
                        tempPlayersList.addAll(playersList.sortedWith(compareBy({ it.nameFull })))
                    }
                    teamHome->{
                        tempPlayersList.clear()
                        tempPlayersList.addAll(playersList.filter { it.id == teamHome })
                    }
                    teamAway->{
                        tempPlayersList.clear()
                        tempPlayersList.addAll(playersList.filter { it.id == teamAway })
                    }
                    else->{}


                }
                adapter.notifyDataSetChanged()

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        adapter = PlayersAdapter(
            this@TeamPlayersActivity,
            tempPlayersList
        )

        activityTeamPlayersBinding.rvPlayers.layoutManager =
            LinearLayoutManager(
                this@TeamPlayersActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        activityTeamPlayersBinding.rvPlayers.adapter = adapter

    }

    private fun setObserver(){

        mainActivityViewModel.errorMessage.observe(this){ errorMessage->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        mainActivityViewModel.matchData.observe(this){ responseData->

            teamHome = responseData.Matchdetail.teamHome.toInt()
            teamAway = responseData.Matchdetail.teamAway.toInt()

            activityTeamPlayersBinding.tabs.removeAllTabs()
            activityTeamPlayersBinding.tabs.addTab(activityTeamPlayersBinding.tabs.newTab().setText("All").setId(0))
            activityTeamPlayersBinding.tabs.addTab(activityTeamPlayersBinding.tabs.newTab()
                .setText(responseData.teams?.get(responseData.Matchdetail.teamAway)?.nameFull)
                .setId(teamAway))
            activityTeamPlayersBinding.tabs.addTab(activityTeamPlayersBinding.tabs.newTab()
                .setText(responseData.teams?.get(responseData.Matchdetail.teamHome)?.nameFull)
                .setId(teamHome))

            playersList.clear()
            val teamHomePlayers = responseData.teams?.get(teamHome.toString())?.players
            for (key in teamHomePlayers?.keys!!) {
                teamHomePlayers[key]?.let { playersList.add(it).apply { it.id = teamHome} }
            }

            val teamAwayPlayers = responseData.teams?.get(teamAway.toString())?.players
            for (key in teamAwayPlayers?.keys!!) {
                teamAwayPlayers[key]?.let { playersList.add(it).apply { it.id = teamAway} }
            }

            tempPlayersList.clear()
            tempPlayersList.addAll(playersList.sortedWith(compareBy { it.nameFull }))
            adapter.notifyDataSetChanged()

            //activityTeamPlayersBinding.tabs.getTabAt(0)?.select()
        }

    }
}