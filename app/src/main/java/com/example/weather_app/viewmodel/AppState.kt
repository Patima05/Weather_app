package com.example.weather_app.viewmodel

sealed class AppState {
    data class Success(val weatherData: Any) : AppState()
    data class Error (val error: Throwable) : AppState()
    object loading : AppState();
}