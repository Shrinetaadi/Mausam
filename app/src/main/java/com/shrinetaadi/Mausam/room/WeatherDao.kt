package com.shrinetaadi.Mausam.room

import androidx.room.*
import com.shrinetaadi.Mausam.model.WeatherList

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(weather: WeatherList)

    @Query("SELECT * FROM weathers ORDER BY id DESC")
    suspend fun allEntries(): List<WeatherList>

    @Delete
    suspend fun deleteEntry(weather: WeatherList)

    @Query("DELETE FROM weathers WHERE id =:id")
    suspend fun deleteSpecificEntry(id: Int)

    @Update
    suspend fun updateEntry(weather: WeatherList)

}