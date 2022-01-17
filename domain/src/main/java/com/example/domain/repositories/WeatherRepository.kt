package com.example.domain.repositories

import com.example.domain.entities.weather.CurrentWeather
import com.example.domain.common.Result
import com.example.domain.entities.request.RequestCurrentWeather
import com.example.domain.entities.weather.MainWeaTher

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun CurrentWeather( requestCurrentWeather: RequestCurrentWeather) : Result<CurrentWeather>

    suspend fun SaveWeather(mainWeaTher: MainWeaTher)

    suspend fun GetTempData() : Flow<MutableList<MainWeaTher>>





}