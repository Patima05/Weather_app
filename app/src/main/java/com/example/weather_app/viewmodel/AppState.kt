package com.example.weather_app.viewmodel

import com.example.weather_app.domain.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class SuccessList(val weatherData: List<Weather>) : AppState()
    data class Error (val error: Throwable) : AppState()
    object loading : AppState();
}