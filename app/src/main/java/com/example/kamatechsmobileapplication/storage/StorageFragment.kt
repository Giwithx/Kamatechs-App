package com.example.kamatechsmobileapplication.storage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kamatechsmobileapplication.R
import com.example.kamatechsmobileapplication.databinding.ActivityMainBinding
import com.example.kamatechsmobileapplication.databinding.FragmentStorageBinding
import com.example.kamatechsmobileapplication.storagedb.Storage
import com.example.kamatechsmobileapplication.storagedb.StorageDAO
import com.example.kamatechsmobileapplication.storagedb.StorageDatabase
import com.example.kamatechsmobileapplication.storagedb.StorageRepository


class StorageFragment : Fragment() {

    private lateinit var  storageViewModel: StorageViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStorageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_storage,
            container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = StorageDatabase.getInstance(application).storageDAO
        val repository = StorageRepository(dao)
        val factory = StorageViewModelFactory(repository)
        storageViewModel = ViewModelProvider(this, factory).get(StorageViewModel::class.java)

        binding.myViewModel= storageViewModel
        binding.lifecycleOwner = this

        fun listItemClicked(storage: Storage){
            //Toast.makeText(this.context, "Selected a Data Entry", Toast.LENGTH_LONG).show()
            storageViewModel.initUpdateAndDelete(storage)
        }
        fun displayStorageList(){
            storageViewModel.storage.observe(viewLifecycleOwner, Observer {
                Log.i("MYTAG", it.toString())
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            })

        }


        fun initRecyclerView(){
            binding.storageRecyclerView.layoutManager = LinearLayoutManager(this.context)
                adapter = MyRecyclerViewAdapter({selectedItem: Storage->listItemClicked(selectedItem)})
                binding.storageRecyclerView.adapter = adapter
            displayStorageList()
        }

        storageViewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled().let{
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        })
        initRecyclerView()
        return binding.root
    }





}