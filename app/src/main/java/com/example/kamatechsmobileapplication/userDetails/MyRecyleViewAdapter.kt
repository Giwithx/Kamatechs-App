package com.example.kamatechsmobileapplication.userDetails

import android.provider.ContactsContract.Profile
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kamatechsmobileapplication.R
import com.example.kamatechsmobileapplication.database.RegisterEntity
import com.example.kamatechsmobileapplication.databinding.ProfileListItemBinding

class MyRecycleViewAdapter(private val usersList :List<RegisterEntity>): RecyclerView.Adapter<MyviewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ProfileListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.profile_list_item,parent,false)
        return MyviewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(usersList[position])

    }


}

class MyviewHolder(private val binding :ProfileListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(user : RegisterEntity){
        binding.FirstNameTextView.text = user.firstName
        binding.secondNameTextView.text = user.lastName
        binding.userTextField.text = user.userName
    }

}