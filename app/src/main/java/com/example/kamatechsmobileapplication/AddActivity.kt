package com.example.kamatechsmobileapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.kamatechsmobileapplication.databinding.ActivityAddBinding
import com.example.kamatechsmobileapplication.storagedb.Storage
import com.example.kamatechsmobileapplication.storagedb.StorageDB
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var storage: Storage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = intent.getSerializableExtra("Data") as Storage?


        if(storage == null) {
            binding.btnAddsUp.text = "Input Data"
        }
        else {
            binding.btnAddsUp.text= "Update"
            binding.editTemp.editText?.setText(storage?.temp.toString())
            binding.editHumid.editText?.setText(storage?.humid.toString())
            binding.editStat.editText?.setText(storage?.stat.toString())
            binding.editAdj.editText?.setText(storage?.adj.toString())
        }


        binding.btnAddsUp.setOnClickListener { Adds() }

        val actionbar = supportActionBar
        actionbar!!.title = "Add Storage Data"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun Adds() {
        val temp = binding.editTemp.editText?.text.toString()
        val humid = binding.editHumid.editText?.text.toString()
        val stat = binding.editStat.editText?.text.toString()
        val adj = binding.editAdj.editText?.text.toString()

        lifecycleScope.launch {
            if(storage==null){
                val storage = Storage(temp = temp, humid = humid, stat = stat, adj = adj)
                StorageDB(this@AddActivity).getStorageDao().addStorage(storage)

                finish()
            }else {
                val s = Storage(temp,humid, stat, adj)
                s.id=storage?.id?:0
                StorageDB(this@AddActivity).getStorageDao().updateStorage(s)
                finish()
            }
        }
    }
}
