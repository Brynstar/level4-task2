package com.example.madlevel4task2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.madlevel4task2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
        history_button.setOnClickListener {
            navController.navigate(R.id.action_gameFragment_to_historyFragment)
        }
        historyButtonToggler()
    }

    private fun historyButtonToggler() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.historyFragment)) {
                history_button.hide()
            } else {
                history_button.show()
            }
        }
    }
}