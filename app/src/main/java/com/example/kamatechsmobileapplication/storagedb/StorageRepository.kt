package com.example.kamatechsmobileapplication.storagedb



class StorageRepository(private val dao: StorageDAO) {

    val storage = dao.getAllStorage()

    suspend fun insert(storage: Storage) : Long{
        return dao.insertStorage(storage)
    }

    suspend fun update(storage: Storage) : Int {
        return dao.updateStorage(storage)
    }

    suspend fun delete(storage: Storage) : Int{
        return dao.deleteStorage(storage)
    }

    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }
}