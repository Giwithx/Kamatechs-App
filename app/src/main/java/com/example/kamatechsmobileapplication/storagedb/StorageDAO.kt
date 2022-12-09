package com.example.kamatechsmobileapplication.storagedb

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface StorageDAO {

    @Insert
    suspend fun insertStorage(storage: Storage): Long

    @Update
    suspend fun updateStorage(storage: Storage) : Int

    @Delete
    suspend fun deleteStorage(storage: Storage) : Int

    @Query("DELETE FROM Storage_data")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM Storage_data")
    fun getAllStorage():LiveData<List<Storage>>

}