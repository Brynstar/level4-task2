package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val games = arrayListOf<Game>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())
        getRemindersFromDatabase()
        rock.setOnClickListener {
            startGame("rock")
        }
        paper.setOnClickListener {
            startGame("paper")
        }
        scissors.setOnClickListener {
            startGame("scissors")
        }
    }

    private fun getRemindersFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@GameFragment.games.clear()
            this@GameFragment.games.addAll(games)
            assignResultsToStatistics()
//            reminderAdapter.notifyDataSetChanged()
        }
    }

    private fun assignResultsToStatistics() {
        var wins = 0
        var draws = 0
        var losses = 0
        for (game in games) {
            if (game.result == "win") {
                wins++
            } else if (game.result == "draw") {
                draws++
            } else {
                losses++
            }
        }
        win_draw_lose.setText(resources.getString(R.string.win_draw_lose, wins, draws, losses))
    }

    private fun startGame(choice: String) {
        var computerChoice = calcComputerChoice()
    }

    private fun calcComputerChoice(): String {
        val choice = (1..3).random()
        if (choice == 1) {
            return "rock"
        } else if (choice == 2) {
            return "paper"
        } else {
            return "scissors"
        }
    }
}