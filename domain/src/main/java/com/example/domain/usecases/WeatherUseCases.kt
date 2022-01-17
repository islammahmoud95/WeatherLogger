package com.example.domain.usecases

import com.example.domain.entities.request.RequestCurrentWeather
import com.example.domain.entities.weather.MainWeaTher
import com.example.domain.repositories.WeatherRepository

class WeatherUseCases (val repository: WeatherRepository) {
    suspend operator fun invoke(requestCurrentWeather: RequestCurrentWeather)
    = repository.CurrentWeather(requestCurrentWeather)

    suspend operator fun invoke(mainWeaTher: MainWeaTher)
    = repository.SaveWeather(mainWeaTher)

    suspend operator fun invoke()
    = repository.GetTempData()


}