package com.example.kamatechsmobileapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kamatechsmobileapplication.AddActivity
import com.example.kamatechsmobileapplication.databinding.ActivityStorageBinding
import com.example.kamatechsmobileapplication.storagedb.Storage
import com.example.kamatechsmobileapplication.storagedb.StorageAdapter
import com.example.kamatechsmobileapplication.storagedb.StorageDB
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorageBinding
    private var mAdapter: StorageAdapter? = null
    private var name = "default"

    lateinit var longitudes: String
    lateinit var latitudes: String

    private var temp = ""
    private var humid=  ""
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        longitudes = ""
        latitudes = ""

        getLocations()
        binding.btnAddstorage.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Storage"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.btnDeleteAll.setOnClickListener {
            val builder = AlertDialog.Builder(this@StorageActivity)
            builder.setMessage("Are you sure you want to delete all?")
            builder.setPositiveButton("YES"){p0, p1 ->
                lifecycleScope.launch{
                    StorageDB(this@StorageActivity).getStorageDao().deleteAllStorage()
                    val list = StorageDB(this@StorageActivity).getStorageDao().getStorage()
                    setAdapter(list)
                    Snackbar.make(binding.myCoordinatorLayout,"All Data Deleted Successfully", Snackbar.LENGTH_SHORT).show()
                }
                p0.dismiss()
            }

            builder.setNegativeButton("NO"){p0, p1 ->
                p0.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }
    }

    private fun setAdapter(list: List<Storage>){
        mAdapter?.setData(list)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()

        getLocations()

        var sharedPreference = getSharedPreferences("Data",MODE_PRIVATE)!!
        name = sharedPreference.getString("flag","defaultName").toString()
        var editor = sharedPreference.edit()

        if (name =="1"){
            Snackbar.make(binding.myCoordinatorLayout,"Data Input Successfully", Snackbar.LENGTH_SHORT).show()
            editor.putString("flag","default")
            editor.commit()
        }else if (name =="2"){
            Snackbar.make(binding.myCoordinatorLayout,"Data Updated Successfully", Snackbar.LENGTH_SHORT).show()
            editor.putString("flag","default")
            editor.commit()
        }

        lifecycleScope.launch{
            val storageList = StorageDB(this@StorageActivity).getStorageDao().getStorage()

            mAdapter = StorageAdapter()
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(this@StorageActivity)
                adapter =mAdapter
                setAdapter(storageList)

                mAdapter?.setOnActionEditListner {
                    val intent = Intent(this@StorageActivity, AddActivity::class.java)
                    intent.putExtra("Data",it)
                    startActivity(intent)
                }

                mAdapter?.setOnDeleteListner {
                    val builder = AlertDialog.Builder(this@StorageActivity)
                    builder.setMessage("Are you sure you want to delete ?")
                    builder.setPositiveButton("YES"){p0, p1 ->
                        lifecycleScope.launch{
                            StorageDB(this@StorageActivity).getStorageDao().deleteStorage(it)
                            val list = StorageDB(this@StorageActivity).getStorageDao().getStorage()
                            setAdapter(list)
                            Snackbar.make(binding.myCoordinatorLayout,"Deleted Successfully", Snackbar.LENGTH_SHORT).show()
                        }
                        p0.dismiss()
                    }

                    builder.setNegativeButton("NO"){p0, p1 ->
                        p0.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }
    private fun getLocations() {
        var sharedPreferences = getSharedPreferences("Datas",MODE_PRIVATE)!!
        var editors = sharedPreferences.edit()
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if(it==null){
                Toast.makeText(this, "Sorry Can't get Location", Toast.LENGTH_SHORT).show()
            }else it.apply{
                val lat = it.latitude
                val long = it.longitude
                val retrofit = Retrofit.Builder()
                    .baseUrl(WeatherActivity.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(WeatherService::class.java)
                val call = service.getCurrentWeatherData(lat.toString(), long.toString(),
                    WeatherActivity.AppId,
                    WeatherActivity.units
                )
                call.enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: Response<WeatherResponse>
                    ) {
                        if (response.code() == 200){

                            val weatherResponse = response.body()!!

                            Log.i("ooob", "entered asgain")
                            temp = weatherResponse.main!!.temp.toString()
                            editors.putString("flags","$temp")
                            humid = weatherResponse.main!!.humidity.toString()
                            editors.putString("flagss","$humid")
                            editors.commit()
                            Log.i  ("oooc", "$temp $humid")

                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                    }

                })
            }
        }

    }

}