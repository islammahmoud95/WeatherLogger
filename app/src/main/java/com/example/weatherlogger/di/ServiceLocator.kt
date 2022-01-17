package com.iscoapps.eskansalman.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.data.api.APIEndPoint
import com.example.data.api.NetworkModule
import com.example.data.db.WeatherDatabase
import com.example.data.mapper.APIResponseMapper
import com.example.data.mapper.RequestCurrentWeatherMapper
import com.example.data.mapper.WeatherEntityMapper
import com.example.data.repistories.WeatherRepositoryImp
import com.example.domain.repositories.WeatherRepository
import com.example.weatherlogger.common.BASEURL


object ServiceLocator {



    val networkModule by lazy {
          NetworkModule()
    }

    @Volatile
    var weatherRepository: WeatherRepository? = null

    fun CreateApiEndPoint(): APIEndPoint {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return networkModule!!.createApi(BASEURL)
        }
    }



    fun CreateDataBase(context: Context):WeatherDatabase{
        return WeatherDatabase.getDatabase(context)
    }
    fun provideWeatherRepo(apiEndPoint: APIEndPoint,database: WeatherDatabase): WeatherRepositoryImp {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return (weatherRepository ?: CreateWeatherRepository(apiEndPoint,database)) as WeatherRepositoryImp
        }
    }

    private fun CreateWeatherRepository(apiEndPoint: APIEndPoint,database: WeatherDatabase): WeatherRepositoryImp {
       //
        val authRepo =
            WeatherRepositoryImp(
                apiEndPoint,
                APIResponseMapper(),
                RequestCurrentWeatherMapper(),
                database.weatherDao(),
                WeatherEntityMapper()
            )
        weatherRepository = authRepo
        return authRepo
    }




}