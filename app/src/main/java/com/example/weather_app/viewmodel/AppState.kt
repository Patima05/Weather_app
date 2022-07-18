package com.example.weather_app.viewmodel

import com.example.weather_app.domain.Weather

sealed class AppState {
    data class SuccessOne(val weatherData: Weather) : AppState()
    data class SuccessMulti(val weatherListData: List<Weather>) : AppState()
    data class Error (val error: Throwable) : AppState()
    object loading : AppState();
}