package com.example.madlevel4task2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.madlevel4task2.R
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
        fabHistory.setOnClickListener {
            navController.navigate(R.id.action_gameFragment_to_historyFragment)
        }
        fabReturn.setOnClickListener {
            navController.navigate(R.id.action_historyFragment_to_gameFragment)
        }
        fabDelete.setOnClickListener {
            // Delete game list
        }
        fragmentToggler()
    }

    private fun fragmentToggler() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.historyFragment)) {
                fabHistory.hide()
                fabDelete.show()
                fabReturn.show()
            } else {
                fabReturn.hide()
                fabDelete.hide()
                fabHistory.show()
            }
        }
    }
}