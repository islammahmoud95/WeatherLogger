package com.example.data.mapper

import com.google.gson.Gson

class APIResponseMapper {
    fun <T>ToData(response:Any,classType: Class<T>): T? {
        val gson: Gson =Gson()
        val jsonString = gson.toJson(response)
        val data=gson.fromJson(jsonString, classType)
        return data
    }
}