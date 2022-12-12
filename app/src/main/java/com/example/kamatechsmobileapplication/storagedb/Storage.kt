package com.example.kamatechsmobileapplication.storagedb

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Storage(
    var temp: String = "",
    var humid: String = "",
    var stat: String = "",
    var adj: String = "",

    ):  Serializable{
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}