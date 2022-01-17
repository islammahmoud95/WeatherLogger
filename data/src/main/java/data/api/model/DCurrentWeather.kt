package com.example.data.api.model

import com.squareup.moshi.Json

data class DCurrentWeather (
    @field:Json(name = "coord")
    val coord : Coord?,
    @field:Json(name = "weather")
    val weather : List<Weather>?,
    @field:Json(name = "main")
    val main : MainWeaTher?,
    @field:Json(name = "visibility")
    val visibility : String?,
    @field:Json(name = "base")
    val base : String?,
    @field:Json(name = "wind")
    val wind : Wind?,
    @field:Json(name = "clouds")
    val clouds : Clouds?,
    @field:Json(name = "dt")
    val dt : String?,
    @field:Json(name = "timezone")
    val timezone : String?,
    @field:Json(name = "id")
    val id : Int?,
    @field:Json(name = "name")
    val name : String?,
    @field:Json(name = "cod")
    val cod : Int?,

)

data class Coord (
    @field:Json(name = "lon")
    val lon : Float?,
    @field:Json(name = "lat")
    val lat : Float?,

)
data class Weather (
    @field:Json(name = "id")
    val id : Int?,
    @field:Json(name = "main")
    val main : String?,
    @field:Json(name = "description")
    val description : String?,
    @field:Json(name = "icon")
    val icon : String?,
)

data class MainWeaTher (
    @field:Json(name = "temp")
    val temp : Float?,
    @field:Json(name = "feels_like")
    val feels_like : Float?,
    @field:Json(name = "temp_min")
    val temp_min : Float?,
    @field:Json(name = "temp_max")
    val temp_max : Float?,
    @field:Json(name = "pressure")
    val pressure : Float?,
    @field:Json(name = "humidity")
    val humidity : Float?,

)


data class Wind (
    @field:Json(name = "speed")
    val speed : Float?,
    @field:Json(name = "deg")
    val deg : Float?,
)

data class Clouds (
    @field:Json(name = "all")
    val all : Float?,
)


