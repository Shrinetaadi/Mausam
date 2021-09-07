package com.shrinetaadi.Mausam.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityList(
    var id: Int? = null,
    var name: String? = null,
    var country: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    var min: Double? = null,
    var max: Double? = null,
    var temp: Double? = null,
    var aqi: Double? = null
) : Parcelable