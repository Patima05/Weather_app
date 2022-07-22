package com.example.weather_app.model

import com.example.weather_app.domain.Weather

fun interface RepositoryOne {
    fun getWeather(lat: Double, lon: Double): Weather
}

fun interface RepositoryMulti {
    fun getListWeather(location: Location): List<Weather>
}

sealed class Location {
    object Russian: Location()
    object World: Location()
}