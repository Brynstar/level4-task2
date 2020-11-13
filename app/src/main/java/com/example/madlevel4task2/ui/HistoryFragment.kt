package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {
    private lateinit var gameRepository: GameRepository

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val actionbar = (activity as AppCompatActivity).supportActionBar
        actionbar?.title = getString(R.string.history)
        gameRepository = GameRepository(requireContext())
        getGamesFromDatabase()
        fabDelete2.setOnClickListener {
            clearGameList()
        }
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvGames.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter
        rvGames.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL,
            )
        )
    }

    private fun getGamesFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        }
    }

    private fun clearGameList() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.clearGameList(games)
            }
            gameAdapter.notifyDataSetChanged()
        }
    }
}