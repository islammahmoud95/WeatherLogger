package com.example.data.db

import androidx.room.*
import com.example.data.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertTemp(weather: WeatherEntity)

    @Query("SELECT * FROM weather")
    fun getSavedTemp(): Flow<MutableList<WeatherEntity>>

    @Query("DELETE FROM weather")
    suspend fun deleteEntities()
}