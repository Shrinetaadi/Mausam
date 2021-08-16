package com.shrinetaadi.Mausam.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AirQuality(
    var coord: Coords,
    var list: List<DetailList>
) : Parcelable {
    @Parcelize
    data class Coords(
        var lat: Double,
        var log: Double
    ) : Parcelable

    @Parcelize
    data class DetailList(
        var dt: Long,
        var main: Main,
        var components: Components
    ) : Parcelable {

        @Parcelize
        data class Main(
            var aqi: Int
        ) : Parcelable

        @Parcelize
        data class Components(
            var co: Double,
            var no: Double,
            var no2: Double,
            var o3: Double,
            var so2: Double,
            var pm2_5: Double,
            var pm10: Double,
            var nh3: Double
        ) : Parcelable

    }


}
