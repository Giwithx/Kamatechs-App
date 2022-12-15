package com.example.kamatechsmobileapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.kamatechsmobileapplication.databinding.ActivityAddBinding
import com.example.kamatechsmobileapplication.storagedb.Storage
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

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var storage: Storage? = null

    lateinit var longitudes: String
    lateinit var latitudes: String

    private var temp = ""
    private var humid=  ""
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        longitudes = ""
        latitudes = ""

        var sharedPreferences = getSharedPreferences("Datas",MODE_PRIVATE)!!
        temp = sharedPreferences.getString("flags", "default").toString()
        humid = sharedPreferences.getString("flagss", "default").toString()

        storage = intent.getSerializableExtra("Data") as Storage?

        if(storage == null) {

            Log.i  ("ooocc", "$temp $humid")
            binding.editTemp.editText?.setText(temp)
            binding.editHumid.editText?.setText(humid)
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

    private fun checkPermissions() {
        Log.i("ooo", "entered")
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        } else {
            getLocations()
        }
    }

    private fun getLocations() {
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
                            humid = weatherResponse.main!!.humidity.toString()
                            Log.i  ("oooc", "$temp $humid")

                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                    }

                })
            }
        }

    }

    private fun Adds() {
//
        var temps = temp
        val humids = humid
        val stats = binding.editStat.editText?.text.toString()
        val adjs = binding.editAdj.editText?.text.toString()

        val sharedPreference =  getSharedPreferences("Data",MODE_PRIVATE)
        var editor = sharedPreference.edit()

        lifecycleScope.launch {
            if(storage==null){
                val storage = Storage(temp = temps, humid = humids, stat = stats, adj = adjs)
                StorageDB(this@AddActivity).getStorageDao().addStorage(storage)

                editor.putString("flag","1")
                editor.commit()

                finish()
            }else {
                val s = Storage(temps,humids, stats, adjs)
                s.id=storage?.id?:0
                StorageDB(this@AddActivity).getStorageDao().updateStorage(s)

                editor.putString("flag","2")
                editor.commit()

                finish()
            }
        }
    }
}