package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.helper.HelperClass
import com.example.madlevel4task2.model.Game
import kotlinx.android.synthetic.main.results.view.*

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            val help = HelperClass()
            itemView.tvDate.text = game.date
            itemView.tvResultTitle.text = help.calcResultText(game.result)
            itemView.ivComputerChoice.setImageResource(help.calcChosenIcon(game.moveComputer))
            itemView.ivPlayerChoice.setImageResource(help.calcChosenIcon(game.movePlayer))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}