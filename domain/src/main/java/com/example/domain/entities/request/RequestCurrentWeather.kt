package com.example.domain.entities.request

data class RequestCurrentWeather(
    val appid : String?,
    var lat : Float?=null,
    var lon : Float?=null,
    var id : Int?=null
)
