package com.example.domain.entities.weather

import java.io.Serializable

data class CurrentWeather (
    val coord : Coord?=null,
    val weather : List<Weather>?=null,
    val main : MainWeaTher?=null,
    val visibility : String?=null,
    val base : String?=null,
    val wind : Wind?=null,
    val clouds : Clouds?=null,
    val dt : String?=null,
    val timezone : String?=null,
    val id : Int?=null,
    val name : String?=null,
    val cod : Int?=null,

    ) :Serializable

data class Coord (
    val lon : Float?,
    val lat : Float?,

    ):Serializable
data class Weather (
    val id : Int?,
    val main : String?,
    val description : String?,
    val icon : String?,
):Serializable

data class MainWeaTher (
    var id : Int?=0,
    var date : String?="",
    var name : String?="",
    val temp : Float?,
    val feels_like : Float?,
    val temp_min : Float?,
    val temp_max : Float?,
    val pressure : Float?,
    val humidity : Float?,

    ):Serializable


data class Wind (
    val speed : Float?,
    val deg : Float?,
):Serializable

data class Clouds (
    val all : Float?,
):Serializable


