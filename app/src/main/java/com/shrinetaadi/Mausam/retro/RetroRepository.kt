package com.shrinetaadi.Mausam.retro

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shrinetaadi.Mausam.fragment.HomeFrag
import com.shrinetaadi.Mausam.model.AirQuality
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
                Log.i(HomeFrag::class.java.simpleName, "Error", t)
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
                Log.i(HomeFrag::class.java.simpleName, "Error", t)
                liveAQIList.postValue(null)
            }
        }

        )

    }

}