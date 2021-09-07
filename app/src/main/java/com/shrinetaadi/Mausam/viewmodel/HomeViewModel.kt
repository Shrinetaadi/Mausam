package com.shrinetaadi.Mausam.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shrinetaadi.Mausam.activity.OtherThingsActivity
import com.shrinetaadi.Mausam.model.AirQuality
import com.shrinetaadi.Mausam.model.CityList
import com.shrinetaadi.Mausam.model.WeatherList
import com.shrinetaadi.Mausam.model.WeatherResponse
import com.shrinetaadi.Mausam.retro.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RetroRepository) : ViewModel() {
    private lateinit var liveDataList: MutableLiveData<WeatherResponse>
    private lateinit var liveCityList: MutableLiveData<CityList>
    private lateinit var liveAQIData: MutableLiveData<AirQuality>
    lateinit var isLoading: MutableLiveData<Boolean>
    lateinit var iD: MutableLiveData<Int>
    lateinit var nAme: MutableLiveData<String>
    lateinit var cOuntry: MutableLiveData<String>
    lateinit var error: MutableLiveData<Boolean>
    lateinit var lat: MutableLiveData<Double>
    lateinit var lon: MutableLiveData<Double>


    fun getWeatherResponse(latitude: Double, longitude: Double): MutableLiveData<WeatherResponse> {
        if (!::liveDataList.isInitialized) {
            liveDataList = MutableLiveData()
            isLoading = MutableLiveData()
            isLoading.value = false
            error = MutableLiveData()
            lat = MutableLiveData()
            lon = MutableLiveData()
            lat.value = latitude
            lon.value = longitude
            loadlistofData(lat.value!!, lon.value!!)
        }

        return liveDataList
    }



    fun getAQIQualityResponse(): MutableLiveData<AirQuality> {
        if (!::liveAQIData.isInitialized) {
            liveAQIData = MutableLiveData()
            loadAQIData(lat.value!!, lon.value!!)
        }

        return liveAQIData
    }

    private fun loadAQIData(lat: Double, lon: Double) {
        repository.aQIApi(lon, lat, liveAQIData, isLoading, error)

    }

    fun loadlistofData(lat: Double, lon: Double) {
        isLoading.value = true
        error.value = false
        repository.oneCallApi(lon, lat, liveDataList, isLoading, error)

    }


}