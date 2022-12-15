package com.example.kamatechsmobileapplication.storagedb

import androidx.room.*

@Dao
interface StorageDAO {

    @Insert
    suspend fun addStorage(storage: Storage)

    @Query("SELECT * FROM storage ORDER BY id DESC")
    suspend fun getStorage(): List<Storage>

    @Query("DELETE FROM storage")
    suspend fun deleteAllStorage()

    @Update
    suspend fun updateStorage(storage: Storage)

    @Delete
    suspend fun deleteStorage(storage: Storage)
}