package com.example.data.api.model

import com.squareup.moshi.Json

data class DRequestCurrentWeather (
    @field:Json(name = "appid")
    val appid : String?,
    @field:Json(name = "lat")
    val lat : Float?,
    @field:Json(name = "lon")
    val lon : Float?,
    @field:Json(name = "id")
    val id : Int?
)


