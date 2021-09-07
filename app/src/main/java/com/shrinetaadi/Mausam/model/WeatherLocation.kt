package com.shrinetaadi.Mausam.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherLocation(
    var city: List<City>
) : Parcelable {
    @Parcelize
    data class City(
        var name: String?=null,
        var lat: Double?=null,
        var lon: Double?=null,
        var country: String?=null
    ) : Parcelable
}