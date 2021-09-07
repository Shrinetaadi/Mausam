package com.shrinetaadi.Mausam.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shrinetaadi.Mausam.activity.OtherThingsActivity
import com.shrinetaadi.Mausam.model.AirQuality
import com.shrinetaadi.Mausam.model.CityList
import com.shrinetaadi.Mausam.model.WeatherResponse
import com.shrinetaadi.Mausam.retro.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repository: RetroRepository) : ViewModel() {
    lateinit var isLoading: MutableLiveData<Boolean>
    lateinit var error: MutableLiveData<Boolean>
    lateinit var liveDataList: MutableLiveData<WeatherResponse>
    lateinit var liveCityList: MutableLiveData<CityList>
    lateinit var liveAQIData: MutableLiveData<AirQuality>
    lateinit var idNew: MutableLiveData<Int>
    var data: MutableLiveData<WeatherResponse> = MutableLiveData()
    var aqi: MutableLiveData<AirQuality> = MutableLiveData()

    companion object {
        private val TAG = AddViewModel::class.java.simpleName
    }


    fun getWeatherResponseCity(): MutableLiveData<CityList> {

        Log.i(OtherThingsActivity::class.java.simpleName, "E6")

        if (!::liveCityList.isInitialized || !::liveDataList.isInitialized || !::liveAQIData.isInitialized) {
            Log.i(OtherThingsActivity::class.java.simpleName, "E7")
            liveDataList = MutableLiveData()
            liveAQIData = MutableLiveData()
            liveCityList = MutableLiveData()
            isLoading = MutableLiveData()
            idNew = MutableLiveData()
            isLoading.value = false
            error = MutableLiveData()

        }
        Log.i(OtherThingsActivity::class.java.simpleName, "E8")


        return liveCityList
    }

    fun getResult(lat: Double, lon: Double, id: Int, name: String, country: String) {
        if ((!isLoading.value!!)) {
            loadAQIData(lat, lon)
            loadlistofData(lat, lon)

            val city = CityList(
                id,
                name,
                country,
                lat,
                lon,
                data.value?.daily?.get(0)?.temp?.min,
                data.value?.daily?.get(0)?.temp?.max,
                data.value?.current?.temp,
                aqi.value?.list?.get(0)?.components?.pm10
            )
            Log.i(
                OtherThingsActivity::class.java.simpleName,
                liveDataList.value?.current?.temp.toString() + "3DE"
            )

            liveCityList.postValue(city)

        }
    }


    fun loadAQIData(lat: Double, lon: Double) {
        repository.aQIApi(lon, lat, liveAQIData, isLoading, error)
        aqi.postValue(liveAQIData.value)
    }

    fun loadlistofData(lat: Double, lon: Double) {
        isLoading.value = true
        error.value = false
        repository.oneCallApi(lon, lat, liveDataList, isLoading, error)
        data.postValue(liveDataList.value)
    }


}
