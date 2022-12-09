package com.example.kamatechsmobileapplication.storage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kamatechsmobileapplication.R
import com.example.kamatechsmobileapplication.databinding.ListsItemBinding

import com.example.kamatechsmobileapplication.storagedb.Storage

class MyRecyclerViewAdapter(private val clickListener:(Storage)->Unit) : RecyclerView.Adapter<MyViewHolder>()
{
    private val storageList = ArrayList<Storage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            val binding: ListsItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.lists_item,parent,false)
//        val binding : ListItemBinding =
//            DataBindingUtil.inflate(layoutInflater, R.layout.lists_item,parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(storageList[position], clickListener)
    }

    fun setList(storage: List<Storage>){
        storageList.clear()
        storageList.addAll(storage)
    }
}

class MyViewHolder(val binding:ListsItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(storage: Storage, clickListener:(Storage)->Unit){
        binding.tempTextView.text = storage.temp
        binding.humidTextView.text= storage.humid
        binding.statTextView.text = storage.stat
        binding.adjTextView.text  = storage.adj
        binding.listItemLayout.setOnClickListener {
            clickListener(storage)
        }
    }
}

