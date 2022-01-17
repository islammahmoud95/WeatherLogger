package com.example.data.mapper

import com.example.data.api.model.DRequestCurrentWeather
import com.example.data.entities.WeatherEntity
import com.example.domain.entities.request.RequestCurrentWeather
import com.example.domain.entities.weather.MainWeaTher
import com.google.gson.Gson

class WeatherEntityMapper {
    fun ToData(response: MainWeaTher): WeatherEntity {
        val data=WeatherEntity(
          id = response.id,
          name = response.name,
          date = response.date,
          temp = response.temp,
            temp_max = response.temp_max,
            temp_min = response.temp_min,
            feels_like = response.feels_like,
            pressure = response.pressure,
            humidity = response.humidity
        )
        return data
    }
}