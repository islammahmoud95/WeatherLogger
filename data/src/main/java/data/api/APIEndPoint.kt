package com.example.data.api

import com.example.data.api.model.DCurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndPoint {

    @GET("weather")
    suspend fun getWeather(
        @Query("appid") appID:String,
        @Query("id") id:Int?,
        @Query("lat") lat:Float?,
        @Query("lon") lon:Float?
    ) : Response<DCurrentWeather>
}