package com.example.weather_app.utils
import com.example.weather_app.model.dto.WeatherDTO

interface OnResponse {
    fun onResponse(weather: WeatherDTO)
}
