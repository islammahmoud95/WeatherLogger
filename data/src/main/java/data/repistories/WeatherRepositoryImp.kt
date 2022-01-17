package com.example.data.repistories

import com.example.domain.entities.weather.CurrentWeather
import com.example.domain.repositories.WeatherRepository
import com.example.data.api.APIEndPoint
import com.example.data.db.WeatherDao
import com.example.data.mapper.APIResponseMapper
import com.example.data.mapper.RequestCurrentWeatherMapper
import com.example.data.mapper.WeatherEntityMapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.domain.common.Result
import com.example.domain.entities.request.RequestCurrentWeather
import com.example.domain.entities.weather.MainWeaTher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map


class WeatherRepositoryImp (val apiEndPoint: APIEndPoint,
                            val mapper: APIResponseMapper,
                            val requestCurrentWeatherMapper: RequestCurrentWeatherMapper,
                            val weatherDao: WeatherDao,
                            val weatherEntityMapper: WeatherEntityMapper
                         )
             : WeatherRepository{

    override suspend fun CurrentWeather(
        requestCurrentWeather: RequestCurrentWeather
    ): Result<CurrentWeather>
            =  withContext(Dispatchers.IO) {

        try {
            val response = apiEndPoint.getWeather(
                requestCurrentWeather.appid.toString(),
                requestCurrentWeather.id,
                requestCurrentWeather.lat,
                requestCurrentWeather.lon
            )
            if (response.isSuccessful) {
                //     Log.e("xxxxxxxxx",response.body()!!.code.toString())
                return@withContext Result.Success(mapper.ToData(response.body()!!,CurrentWeather::class.java) as CurrentWeather)
            } else {
                return@withContext Result.Error(Exception(response.message()))
            }
        }catch (e:Exception){
            return@withContext Result.Error(e)
        }
    }

    override suspend fun SaveWeather(mainWeaTher: MainWeaTher) = withContext(Dispatchers.IO) {
        weatherDao.InsertTemp(weatherEntityMapper.ToData(mainWeaTher))
    }

    override suspend fun GetTempData(): Flow<MutableList<MainWeaTher>> {
       val mainWeather= weatherDao.getSavedTemp()
        return mainWeather.map { list ->
            list.map { element ->
                mapper.ToData(element!!,MainWeaTher::class.java)
            } as MutableList<MainWeaTher>
        }
    }

}

