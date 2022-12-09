package com.example.kamatechsmobileapplication.storage

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import androidx.room.Update
import com.example.kamatechsmobileapplication.storagedb.Storage
import com.example.kamatechsmobileapplication.storagedb.StorageRepository
import kotlinx.coroutines.launch
import java.util.*

class StorageViewModel(private val repository: StorageRepository) : ViewModel(), Observable{



    val storage = repository.storage
    private var isUpdateOrDelete = false
    private lateinit var storageToUpdateOrDelete: Storage

    @Bindable
    val inputTemp= MutableLiveData<String>()

    @Bindable
    val inputHumid=MutableLiveData<String>()

    @Bindable
    val inputStat= MutableLiveData<String>()

    @Bindable
    val inputAdj=MutableLiveData<String>()

    @Bindable
    val btnsaveupdateText = MutableLiveData<String>()

    @Bindable
    val btndeleteText= MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        btnsaveupdateText.value = "Save"
        btndeleteText.value = "Clear Data"
    }

    fun saveupdate(){

        if(inputTemp.value == null){
            statusMessage.value = Event("Please enter Temperature")
        }else if(inputHumid.value == null){
            statusMessage.value = Event("Please enter Humidity")
        }else if(inputStat.value == null){
            statusMessage.value = Event("Please enter Status")
        }else if(inputAdj.value == null){
            statusMessage.value = Event("Please enter Adjustment")
        }

        else if(isUpdateOrDelete){
            storageToUpdateOrDelete.temp=inputTemp.value!!
            storageToUpdateOrDelete.humid=inputHumid.value!!
            storageToUpdateOrDelete.stat=inputStat.value!!
            storageToUpdateOrDelete.adj=inputAdj.value!!
            update(storageToUpdateOrDelete)

        }else {
            val temp = inputTemp.value!!
            val humid = inputHumid.value!!
            val stat = inputStat.value!!
            val adj = inputAdj.value!!
            insert(Storage(0, temp, humid, stat, adj))
            inputTemp.value = null
            inputHumid.value = null
            inputStat.value = null
            inputAdj.value = null
        }
    }

    fun deleteall(){
            if(isUpdateOrDelete){
                delete(storageToUpdateOrDelete)
            }else {
                clearAll()
            }
    }

    fun insert(storage: Storage){
        viewModelScope.launch {
            val newRowId = repository.insert(storage)
            if (newRowId>-1){
                statusMessage.value = Event("Data Insert Success")
        }else{
                statusMessage.value = Event("An Error Has Occurred")
            }
        }
    }

    fun update(storage: Storage) {
        viewModelScope.launch {
            val noOfRows = repository.update(storage)
                if (noOfRows > 0){
                inputTemp.value = null
                inputHumid.value= null
                inputStat.value = null
                inputAdj.value  = null
                isUpdateOrDelete= false
                storageToUpdateOrDelete = storage
                btnsaveupdateText.value = "Save"
                btndeleteText.value = "Clear Data"
                statusMessage.value = Event("Update Success")
            }else{
                    statusMessage.value = Event("An Error Has Occurred")
            }
        }
    }

    fun delete(storage: Storage) {
        viewModelScope.launch {
            val noOfRowsDeleted = repository.delete(storage)
            if (noOfRowsDeleted>0){
                inputTemp.value = null
                inputHumid.value= null
                inputStat.value = null
                inputAdj.value  = null
                isUpdateOrDelete= false
                storageToUpdateOrDelete = storage
                btnsaveupdateText.value = "Save"
                btndeleteText.value = "Clear Data"
                statusMessage.value = Event("Row $noOfRowsDeleted Delete Success")

            }else{
                statusMessage.value = Event("An Error Has Occurred")

            }
        }
    }

    fun clearAll() = viewModelScope.launch{
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted>0){
            statusMessage.value = Event("$noOfRowsDeleted Data Delete Success")

        }else{
            statusMessage.value = Event("$noOfRowsDeleted Data Delete Success")

        }
    }

    fun initUpdateAndDelete(storage: Storage){
        inputTemp.value = storage.temp
        inputHumid.value= storage.humid
        inputStat.value = storage.stat
        inputAdj.value  = storage.adj
        isUpdateOrDelete= true
        storageToUpdateOrDelete = storage
        btnsaveupdateText.value = "Update"
        btndeleteText.value = "Delete"
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
