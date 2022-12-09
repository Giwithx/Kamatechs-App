package com.example.kamatechsmobileapplication.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kamatechsmobileapplication.storagedb.StorageRepository
import java.lang.IllegalArgumentException

class StorageViewModelFactory(private val repository: StorageRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StorageViewModel::class.java)){
            return StorageViewModel(repository) as T
        }
            throw IllegalArgumentException("Unknown View Model Classs")
    }
}