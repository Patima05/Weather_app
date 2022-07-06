package com.example.weather_app.model

import com.example.weather_app.domain.Weather

interface Repository {
    fun getWeather(lat: Double, lon: Double): Weather
    fun getWeatherList(): List<Weather>
}