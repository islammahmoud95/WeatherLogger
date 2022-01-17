package com.example.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val id: Int?=0,
    val name: String?,
    val date: String?,
    val temp: Float?,
    val feels_like: Float?,
    val temp_min: Float?,
    val temp_max: Float?,
    val pressure: Float?,
    val humidity: Float?,

)