package com.example.kamatechsmobileapplication.storagedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Storage::class], version = 1)
abstract class StorageDatabase : RoomDatabase() {

    abstract val storageDAO : StorageDAO

    companion object{
        @Volatile
        private var INSTANCE : StorageDatabase? = null
            fun getInstance(context: Context):StorageDatabase{
                synchronized(this){
                    var instance:StorageDatabase? = INSTANCE
                        if(instance==null) {
                            instance = Room.databaseBuilder(
                                context.applicationContext,
                                StorageDatabase::class.java,
                                "Storage_data"
                            ).build()
                        }
                    return instance
                }
            }
    }
}