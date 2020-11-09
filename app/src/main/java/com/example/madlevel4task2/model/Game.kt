package com.example.madlevel4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gameTable")
data class Game (
    @ColumnInfo()
    var date: String,
    @ColumnInfo(name = "move_computer")
    var moveComputer: String,
    @ColumnInfo(name = "move_player")
    var movePlayer: String,
    @ColumnInfo()
    var result: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)