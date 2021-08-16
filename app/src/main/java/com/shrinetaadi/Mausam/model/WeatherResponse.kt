package com.shrinetaadi.Mausam.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WeatherResponse(
    var lat: String,
    var lon: String,
    var current: Current,
    var hourly: List<Hourly>,
    var daily: List<Daily>
) : Parcelable {
    @Parcelize
    data class Current(
        var dt: Long,
        var sunrise: Long,
        var sunset: Long,
        var temp: Double,
        var feels_like: Double,
        var pressure: Int,
        var humidity: Int,
        var uvi: Double,
        var clouds: Int,
        var wind_speed: Double,
        var wind_deg: Int,
        var weather: List<Weather>
    ) : Parcelable {
        @Parcelize
        data class Weather(
            var id: Int,
            var main: String,
            var description: String,
            var icon: String
        ) : Parcelable

    }


    @Parcelize
    data class Hourly(
        var dt: Long,
        var temp: Double,
        var wind_speed: Double,
        var wind_deg: Double,
        var weather: List<Current.Weather>

    ) : Parcelable

    @Parcelize
    data class Daily(
        var dt: Long,
        var temp: TempDetail,
        var wind_speed: Double,
        var wind_deg: Double,
        var pop: Double,
        var weather: List<Current.Weather>
    ) : Parcelable {
        @Parcelize
        data class TempDetail(
            var min: Double,
            var max: Double
        ) : Parcelable
    }

}