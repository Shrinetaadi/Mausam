package com.shrinetaadi.Mausam.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shrinetaadi.Mausam.model.WeatherList

@Database(entities = [WeatherList::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    companion object {
        var weatherDatabase: WeatherDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): WeatherDatabase {
            if (weatherDatabase == null) {
                weatherDatabase = Room.databaseBuilder(
                    context, WeatherDatabase::class.java, "weatherlist.db"
                ).build()
            }
            return weatherDatabase!!
        }
    }

    abstract fun weatherDao(): WeatherDao
}