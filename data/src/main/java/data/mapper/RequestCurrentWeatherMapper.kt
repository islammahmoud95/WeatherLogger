package com.example.data.mapper

import com.example.data.api.model.DRequestCurrentWeather
import com.example.domain.entities.request.RequestCurrentWeather
import com.google.gson.Gson

class RequestCurrentWeatherMapper {
    fun ToData(response: RequestCurrentWeather): DRequestCurrentWeather {
        val data=DRequestCurrentWeather(
            response.appid,
            response.lat,
            response.lon,
            response.id
        )
        return data
    }
}