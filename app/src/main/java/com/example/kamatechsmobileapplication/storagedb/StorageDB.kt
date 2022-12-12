package com.example.kamatechsmobileapplication.storagedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Storage::class], version = 1)
abstract class StorageDB: RoomDatabase(){

    abstract fun getStorageDao(): StorageDAO

    companion object{

        @Volatile
        private var instance: StorageDB? =null
        private val LOCK = Any()

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            StorageDB::class.java,
            "Storage Database"
        ).build()

    }
}