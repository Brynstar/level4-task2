package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.madlevel4task2.R
import com.example.madlevel4task2.helper.HelperClass
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.results.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val games = arrayListOf<Game>()
    private val help = HelperClass()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionbar = (activity as AppCompatActivity).supportActionBar
        actionbar?.title = getString(R.string.app_name)
        gameRepository = GameRepository(requireContext())
        getGamesFromDatabase()
        ivRock.setOnClickListener {
            startGame("rock")
        }
        ivPaper.setOnClickListener {
            startGame("paper")
        }
        ivScissors.setOnClickListener {
            startGame("scissors")
        }
    }

    private fun getGamesFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@GameFragment.games.clear()
            this@GameFragment.games.addAll(games)
            assignResultsToStatistics()
        }
    }

    private fun assignResultsToStatistics() {
        var wins = 0
        var draws = 0
        var losses = 0
        for (game in games) {
            when (game.result) {
                "win" -> {
                    wins++
                }
                "draw" -> {
                    draws++
                }
                "lose" -> {
                    losses++
                }
            }
        }
        tvWinDrawLose.text = resources.getString(R.string.win_draw_lose, wins, draws, losses)
    }

    private fun startGame(playerChoice: String) {
        val computerChoice = calcComputerChoice()
        val result = calcResult(computerChoice, playerChoice)
        val date = Calendar.getInstance().time
        val game =
            Game(date.toString(), computerChoice, playerChoice, result)
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
            getGamesFromDatabase()
        }
        tvResultTitle.text = help.calcResultText(result)
        ivComputerChoice.setImageResource(help.calcChosenIcon(computerChoice))
        ivPlayerChoice.setImageResource(help.calcChosenIcon(playerChoice))
        resultsScreen.visibility = View.VISIBLE
    }

    private fun calcComputerChoice(): String {
        return when ((1..3).random()) {
            1 -> {
                "rock"
            }
            2 -> {
                "paper"
            }
            else -> {
                "scissors"
            }
        }
    }

    private fun calcResult(computerChoice: String, playerChoice: String): String {
        when (playerChoice) {
            "rock" -> {
                return when (computerChoice) {
                    "scissors" -> {
                        "win"
                    }
                    "paper" -> {
                        "lose"
                    }
                    else -> {
                        "draw"
                    }
                }
            }
            "paper" -> {
                return when (computerChoice) {
                    "rock" -> {
                        "win"
                    }
                    "scissors" -> {
                        "lose"
                    }
                    else -> {
                        "draw"
                    }
                }
            }
            "scissors" -> {
                return when {
                    computerChoice == "paper" -> {
                        "win"
                    }
                    playerChoice == "rock" -> {
                        "lose"
                    }
                    else -> "draw"
                }
            }
        }
        return "draw"
    }
}