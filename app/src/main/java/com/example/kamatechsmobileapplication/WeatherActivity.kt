package com.example.kamatechsmobileapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity: AppCompatActivity() {
    private var weatherData_Temp: TextView? = null
    private var weatherData_Temp_Min: TextView? = null
    private var weatherData_Temp_Max: TextView? = null
    private var weatherData_Place: TextView? = null
    private var weatherData_Wind: TextView? = null
    private var weatherData_Pressure: TextView? = null
    private var weatherData_Humidity: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)



        weatherData_Temp = findViewById(R.id.tvWeatherData_Temp)
        weatherData_Temp_Min = findViewById(R.id.tvWeatherData_Temp_Min)
        weatherData_Temp_Max = findViewById(R.id.tvWeatherData_Temp_Max)
        weatherData_Place = findViewById(R.id.tvWeatherData_Place)
        weatherData_Wind = findViewById(R.id.tvWeatherData_Wind)
        weatherData_Pressure = findViewById(R.id.tvWeatherData_Pressure)
        weatherData_Humidity = findViewById(R.id.tvWeatherData_Humidity)
        findViewById<View>(R.id.btnWeatherUpdate).setOnClickListener {
            getCurrentData()
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Weather"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    private fun getCurrentData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, AppId, units)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200){
                    val weatherResponse = response.body()!!

                    val temp = "" + weatherResponse.main!!.temp + "(°C)"
                    val temp_min = "Minimum: " + weatherResponse.main!!.temp_min + "(°C)"
                    val temp_max = "Maximum: " + weatherResponse.main!!.temp_max + "(°C)"
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
    companion object{
        var BaseUrl = "https://api.openweathermap.org/"
        var AppId = "8676f9199326e823e584f9c635179f3a"
        var lat = "14.6507"
        var lon = "121.1029"
        var units = "metric"
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}