package com.harshit.sportsinteractivedemo.adapters

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harshit.sportsinteractivedemo.databinding.ItemPlayerBinding
import com.harshit.sportsinteractivedemo.model.Players


class PlayersAdapter(
    var context: Context,
    val playersList: List<Players>,
) :
    RecyclerView.Adapter<PlayersAdapter.AdapterPlayer>() {

    private val TAG = PlayersAdapter::class.java.simpleName

    class AdapterPlayer(val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPlayer {
        /* val view: View = LayoutInflater.from(parent.context)
             .inflate(R.layout.item_player, parent, false)
         return AdapterPlayer(view)*/
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AdapterPlayer(binding)
    }

    override fun onBindViewHolder(holder: AdapterPlayer, position: Int) {

        val player: Players = playersList[position]

        val designation = if(player.isCaptain && player.isKeeper){
            "(c & wk)"
        }else if(player.isCaptain){
            "(c)"
        }else if(player.isKeeper){
            "(wk)"
        } else {
            ""
        }

        holder.binding.txtPlayerName.text =
            buildString {
                append(player.nameFull)
                append(" $designation")
            }

        holder.binding.root.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(buildString {
                    append(player.nameFull)
                    append(" $designation")
                })
                .setMessage(buildString {
                    append("Batting Style: ${player.batting?.style}\n")
                    append("Bowling Style: ${player.bowling?.style}")
                })
                .setPositiveButton(
                    "Ok",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                .show()
        }

    }

    override fun getItemCount(): Int {
        return playersList.size
    }


}