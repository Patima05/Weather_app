package com.example.weather_app.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 25,
    val feelsLike: Int = 25
)

class City(
    val name: String,
    val lat: Double,
    val lon: Double
)

fun getDefaultCity() = City ("Москва",37.617299900000035,55.755826)