package com.example.madlevel4task2.helper

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.madlevel4task2.R
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*

class HelperClass {

    fun calcResultText(result: String): String {
        return when (result) {
            "win" -> {
                "You Win!"
            }
            "lose" -> {
                "You Lose!"
            }
            else -> {
                "Draw!"
            }
        }
    }

    fun calcChosenIcon(icon: String): Int {
        return when (icon) {
            "rock" -> {
                R.drawable.rock
            }
            "paper" -> {
                R.drawable.paper
            }
            else -> {
                R.drawable.scissors

            }
        }
    }
}