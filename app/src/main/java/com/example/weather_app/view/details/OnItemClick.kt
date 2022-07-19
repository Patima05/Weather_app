package com.example.weather_app.view.details

import com.example.weather_app.domain.Weather

interface OnItemClick {
    fun onItemClick(weather: Weather)
}