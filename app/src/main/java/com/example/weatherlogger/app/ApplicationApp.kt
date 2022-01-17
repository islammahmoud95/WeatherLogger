package com.example.weatherlogger.app

import android.app.Application
import com.example.data.api.APIEndPoint
import com.example.data.db.WeatherDatabase
import com.example.domain.repositories.WeatherRepository
import com.example.domain.usecases.WeatherUseCases
import com.iscoapps.eskansalman.di.ServiceLocator
import dagger.hilt.android.HiltAndroidApp


class ApplicationApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }


    val weatherDatabase : WeatherDatabase get() = ServiceLocator.CreateDataBase(this)
    val apiEndPoint : APIEndPoint get() = ServiceLocator.CreateApiEndPoint()
    val weatherRepository : WeatherRepository get()= ServiceLocator.provideWeatherRepo(apiEndPoint,weatherDatabase)
    val weatherUseCases : WeatherUseCases get()= WeatherUseCases(weatherRepository)


}