package com.example.weatherlogger.di

import com.example.data.api.NetworkModule
import org.junit.Assert.*

import org.junit.Test

class ServiceLocatorTest {

    @Test
    fun getNetworkModule() {
        NetworkModule()
    }

    @Test
    fun getWeatherRepository() {
    }

    @Test
    fun setWeatherRepository() {
    }

    @Test
    fun createApiEndPoint() {
    }

    @Test
    fun createDataBase() {
    }

    @Test
    fun provideWeatherRepo() {
    }
}