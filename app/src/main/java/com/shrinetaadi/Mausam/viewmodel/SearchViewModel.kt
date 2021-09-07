package com.shrinetaadi.Mausam.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shrinetaadi.Mausam.model.WeatherLocation
import com.shrinetaadi.Mausam.retro.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: RetroRepository) : ViewModel() {
    private lateinit var weatherResult: MutableLiveData<List<WeatherLocation.City>>
    lateinit var queryText: MutableLiveData<String>
    lateinit var isLoading: MutableLiveData<Boolean>
    lateinit var error: MutableLiveData<Boolean>
    private var tempText = ""

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }


    fun getWeatherResponse(): MutableLiveData<List<WeatherLocation.City>> {
        if (!::weatherResult.isInitialized) {
            weatherResult = MutableLiveData()
            queryText = MutableLiveData()
            isLoading = MutableLiveData()
            isLoading.value = false
            error = MutableLiveData()
            error.value = false
        }
        return weatherResult
    }

    fun makeQuery(query: String) {
        if ((!isLoading.value!!)&&(query != tempText)) {
            tempText = query
            fetchWeatherResponse(query)
        }
    }

    private fun fetchWeatherResponse(query: String) {
        Log.i(TAG, "Query text: $query")
        isLoading.value = true
        error.value = false
        repository.makeSearchCall(query, weatherResult, isLoading, error)
    }


}
