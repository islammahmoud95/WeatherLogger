package com.example.weatherlogger.ui.activity.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.WeatherUseCases
import com.example.weatherlogger.common.*
import kotlinx.coroutines.launch
import com.example.domain.common.Result
import com.example.domain.entities.request.RequestCurrentWeather
import com.example.domain.entities.weather.CurrentWeather
import com.example.domain.entities.weather.MainWeaTher
import kotlinx.coroutines.flow.collect
import java.util.*


class MainViewModel (val useCases: WeatherUseCases) : ViewModel(){

    var first :Boolean=true
    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading
    private val _viewModelChanged = MutableLiveData<Boolean>()
    val viewModelChanged = _viewModelChanged

    private val _currentWeatherResponse = MutableLiveData<CurrentWeather>()
    val currentWeatherResponse = _currentWeatherResponse

    private val _mainWeatherResponse = MutableLiveData<List<MainWeaTher>>()
    val mainWeatherResponse = _mainWeatherResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse = _errorResponse

    val requestCurrentWeather=RequestCurrentWeather(appid = APPID)

    var currentWeather=CurrentWeather()


    fun getWeather() {
        _loading.postValue(first)
        first=false
        viewModelScope.launch {
                val response = useCases.invoke(requestCurrentWeather)

            when (response) {
                is Result.Success -> {
                    _loading.postValue(false)
                    _currentWeatherResponse.postValue(response.data!!)
                    _viewModelChanged.postValue(true)
                    val mainW=response.data.main
                    val cal=Calendar.getInstance()
                    mainW?.date=TimeClass.CreateDateWithName(cal.time)
                    mainW?.name=response.data.name
                    mainW?.id=response.data.id
                    response.data.main?.let { useCases.invoke(it) }
                }
                is Result.Error -> {
                    _errorResponse.postValue(response.exception.message)
                    _loading.postValue(false)

                }
            }
        }
    }

    fun getMainWeather(){
        viewModelScope.launch {
            val mainWeather=useCases.invoke()
            mainWeather.collect {
                _mainWeatherResponse.postValue(it)
                Log.e("xxxxx",it.size.toString())
            }
        }
    }
    class AttachedViewModelFactory(
        val useCases: WeatherUseCases
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                useCases
            ) as T
        }
    }


    val arrayStr="[ {" +
            "        \"id\": 360630," +
            "        \"name\": \"Cairo\"," +
            "        \"state\": \"\"," +
            "        \"country\": \"EG\"," +
            "        \"coord\": {" +
            "            \"lon\": 31.24967," +
            "            \"lat\": 30.06263" +
            "        }" +
            "    }," +
            "    {" +
            "        \"id\": 360631," +
            "        \"name\": \"Cairo Governorate\"," +
            "        \"state\": \"\"," +
            "        \"country\": \"EG\"," +
            "        \"coord\": {" +
            "            \"lon\": 31.65," +
            "            \"lat\": 30.049999" +
            "        }" +
            "    }," +
            "    {" +
            "        \"id\": 360686," +
            "        \"name\": \"Minia\"," +
            "        \"state\": \"\"," +
            "        \"country\": \"EG\"," +
            "        \"coord\": {" +
            "            \"lon\": 28.0952188," +
            "            \"lat\": 30.7157014" +
            "        }" +
            "    }," +
            "     {" +
            "        \"id\": 361058," +
            "        \"name\": \"Alexandria\"," +
            "        \"state\": \"\"," +
            "        \"country\": \"EG\"," +
            "        \"coord\": {" +
            "            \"lon\": 29.955271," +
            "            \"lat\": 31.215639" +
            "        }" +
            "    }," +
            "     {" +
            "        \"id\": 359792," +
            "        \"name\": \"Aswan\"," +
            "        \"state\": \"\"," +
            "        \"country\": \"EG\"," +
            "        \"coord\": {" +
            "            \"lon\": 32.90704," +
            "            \"lat\": 24.09343" +
            "        }" +
            "    }," +
            "     {" +
            "        \"id\": 359173," +
            "        \"name\": \"Banī Suwayf\"," +
            "        \"state\": \"\"," +
            "        \"country\": \"EG\"," +
            "        \"coord\": {" +
            "            \"lon\": 31.097851," +
            "            \"lat\": 29.074409" +
            "        }" +
            "    }]"
}