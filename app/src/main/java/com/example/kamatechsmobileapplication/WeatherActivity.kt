package com.example.kamatechsmobileapplication

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.jar.Manifest

class WeatherActivity: AppCompatActivity() {
    private var weatherData_Temp: TextView? = null
    private var weatherData_Temp_Min: TextView? = null
    private var weatherData_Temp_Max: TextView? = null
    private var weatherData_Place: TextView? = null
    private var weatherData_Wind: TextView? = null
    private var weatherData_Pressure: TextView? = null
    private var weatherData_Humidity: TextView? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var longitudes: String
    lateinit var latitudes: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        longitudes = ""
        latitudes = ""

        weatherData_Temp = findViewById(R.id.tvWeatherData_Temp)
        weatherData_Temp_Min = findViewById(R.id.tvWeatherData_Temp_Min)
        weatherData_Temp_Max = findViewById(R.id.tvWeatherData_Temp_Max)
        weatherData_Place = findViewById(R.id.tvWeatherData_Place)
        weatherData_Wind = findViewById(R.id.tvWeatherData_Wind)
        weatherData_Pressure = findViewById(R.id.tvWeatherData_Pressure)
        weatherData_Humidity = findViewById(R.id.tvWeatherData_Humidity)
        findViewById<View>(R.id.btnWeatherUpdate).setOnClickListener {
            checkPermissions()
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Weather"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        } else {
            getLocations()
        }
    }
    @SuppressLint("MissingPermissions")
    private fun getLocations() {
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if(it==null){
                Toast.makeText(this, "Sorry Can't get Location", Toast.LENGTH_SHORT).show()
            }else it.apply{
                val lat = it.latitude
                val long = it.longitude
                val retrofit = Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(WeatherService::class.java)
                val call = service.getCurrentWeatherData(lat.toString(), long.toString(), AppId, units)
                call.enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: Response<WeatherResponse>
                    ) {
                        if (response.code() == 200){
                            val weatherResponse = response.body()!!

                            val temp = "" + weatherResponse.main!!.temp + "°C"
                            val temp_min = "Minimum: " + weatherResponse.main!!.temp_min + "°C"
                            val temp_max = "Maximum: " + weatherResponse.main!!.temp_max + "°C"
                            val place = "" + weatherResponse.name + ", " + weatherResponse.sys!!.country
                            val wind = "Wind" + "\n" + weatherResponse.wind!!.speed
                            val humidity = "Humidity" + "\n" + weatherResponse.main!!.humidity
                            val pressure = "Pressure" + "\n" + weatherResponse.main!!.pressure

                            weatherData_Temp!!.text = temp
                            weatherData_Temp_Min!!.text = temp_min
                            weatherData_Temp_Max!!.text = temp_max
                            weatherData_Place!!.text = place
                            weatherData_Wind!!.text = wind
                            weatherData_Humidity!!.text = humidity
                            weatherData_Pressure!!.text = pressure
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        weatherData_Temp!!.text = t.message
                        weatherData_Temp_Min!!.text = t.message
                        weatherData_Temp_Max!!.text = t.message
                        weatherData_Place!!.text = t.message
                        weatherData_Wind!!.text = t.message
                        weatherData_Humidity!!.text = t.message
                        weatherData_Pressure!!.text = t.message
                    }

                })
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    getLocations()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getCurrentData(){

    }
    companion object{
        var BaseUrl = "https://api.openweathermap.org/"
        var AppId = "8676f9199326e823e584f9c635179f3a"
//        var lat = "14.6507"
//        var lon = "121.1029"
        var units = "metric"
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}