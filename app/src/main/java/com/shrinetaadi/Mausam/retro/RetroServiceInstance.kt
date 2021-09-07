package com.shrinetaadi.Mausam.retro

import com.shrinetaadi.Mausam.model.AirQuality
import com.shrinetaadi.Mausam.model.WeatherLocation
import com.shrinetaadi.Mausam.model.WeatherResponse
import com.shrinetaadi.Mausam.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInstance {
    @GET("data/2.5/onecall?")
    fun getWeatherResponse(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("exclude") exclude: String = "minutely",
        @Query("units") units: String = "metric",
        @Query("appid") apiId: String = Constants.API_ID
    ): Call<WeatherResponse>

    @GET("data/2.5/air_pollution?")
    fun getAirQualityResponse(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("appid") apiId: String = Constants.API_ID
    ): Call<AirQuality>

    @GET("geo/1.0/direct?")
    fun getLocation(
        @Query("q") q: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiId: String = Constants.API_ID
    ): Call<List<WeatherLocation.City>>


}