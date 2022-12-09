package com.example.kamatechsmobileapplication.storagedb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Storage_data")
data class Storage (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,

    @ColumnInfo(name = "temperature")
    var temp: String,

    @ColumnInfo(name = "humidity")
    var humid: String,

    @ColumnInfo(name = "status")
    var stat: String,

    @ColumnInfo(name = "adjustment")
    var adj: String

        )