package com.shrinetaadi.Mausam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weathers")
class WeatherList : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "country")
    var country: String? = null

    @ColumnInfo(name = "lat")
    var lat: Double? = null

    @ColumnInfo(name = "lon")
    var lon: Double? = null


}