package com.shrinetaadi.Mausam.retro

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shrinetaadi.Mausam.activity.MainActivity
import com.shrinetaadi.Mausam.activity.OtherThingsActivity
import com.shrinetaadi.Mausam.model.AirQuality
import com.shrinetaadi.Mausam.model.WeatherLocation
import com.shrinetaadi.Mausam.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetroRepository @Inject constructor(private val retroInstance: RetroServiceInstance) {

    fun oneCallApi(
        lon: Double,
        lat: Double,
        liveDatList: MutableLiveData<WeatherResponse>,
        isLoading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val call: Call<WeatherResponse> = retroInstance.getWeatherResponse(lon, lat)
        call?.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    liveDatList.postValue(response.body())
                } else {
                    error.value = true
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                print("ERROR")
                isLoading.value = false
                error.value = true
                Log.i(MainActivity::class.java.simpleName, "Error", t)
                liveDatList.postValue(null)
            }

        })


    }

    fun aQIApi(
        lon: Double,
        lat: Double,
        liveAQIList: MutableLiveData<AirQuality>,
        isLoading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val call: Call<AirQuality> = retroInstance.getAirQualityResponse(lon, lat)
        call?.enqueue(object : Callback<AirQuality> {
            override fun onResponse(call: Call<AirQuality>, response: Response<AirQuality>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    liveAQIList.postValue(response.body())
                } else {
                    error.value = true
                }
            }

            override fun onFailure(call: Call<AirQuality>, t: Throwable) {
                print("ERROR")
                isLoading.value = false
                error.value = true
                Log.i(MainActivity::class.java.simpleName, "Error", t)
                liveAQIList.postValue(null)
            }
        }

        )

    }

    fun makeSearchCall(
        query: String,
        weatherResult: MutableLiveData<List<WeatherLocation.City>>,
        loading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val call: Call<List<WeatherLocation.City>> = retroInstance.getLocation(query)
        call?.enqueue(object : Callback<List<WeatherLocation.City>> {
            override fun onResponse(
                call: Call<List<WeatherLocation.City>>,
                response: Response<List<WeatherLocation.City>>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    weatherResult.postValue(response.body())
                    Log.i(OtherThingsActivity::class.java.simpleName, "Error0")
                } else {
                    error.value = true
                    Log.i(OtherThingsActivity::class.java.simpleName, "Error1")
                    weatherResult.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<WeatherLocation.City>>, t: Throwable) {
                print("ERROR")
                loading.value = false
                error.value = true
                Log.i(OtherThingsActivity::class.java.simpleName, "Error", t)
                weatherResult.postValue(null)
            }
        }

        )

    }

}