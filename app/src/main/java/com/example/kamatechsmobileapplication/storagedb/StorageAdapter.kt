package com.example.kamatechsmobileapplication.storagedb

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kamatechsmobileapplication.R

class StorageAdapter: RecyclerView.Adapter<StorageAdapter.StorageViewHolder>() {

    private var list = mutableListOf<Storage>()

    private var actionEdit: ((Storage)-> Unit)? = null
    private var actionDelete: ((Storage)->Unit)?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.storage_card, parent,false)

        return StorageViewHolder(view)
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        val storage = list[position]
        holder.texTemp.text = storage.temp
        holder.texHumid.text = storage.humid
        holder.texStat.text = storage.stat
        holder.texAdj.text = storage.adj

        holder.actionEdit.setOnClickListener{ actionEdit?.invoke(storage)}
        holder.actionDelete.setOnClickListener{ actionDelete?.invoke(storage)}

    }

    override fun getItemCount() = list.size

    fun setData(data: List<Storage>){
        list.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }
    fun setOnActionEditListner(callback: (Storage)-> Unit){
        this.actionEdit=callback
    }
    fun setOnDeleteListner(callback: (Storage) -> Unit){
        this.actionDelete=callback
    }
    class StorageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val texTemp: TextView = itemView.findViewById(R.id.texTemp)
        val texHumid: TextView = itemView.findViewById(R.id.texHumid)
        val texStat: TextView = itemView.findViewById(R.id.texStat)
        val texAdj: TextView = itemView.findViewById(R.id.texAdj)
        val actionEdit: ImageView = itemView.findViewById(R.id.action_edit)
        val actionDelete: ImageView = itemView.findViewById(R.id.action_delete)
    }
}