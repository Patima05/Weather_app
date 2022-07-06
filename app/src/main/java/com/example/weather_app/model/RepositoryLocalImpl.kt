package com.example.weather_app.model

import android.os.SystemClock
import com.example.weather_app.domain.Weather
import com.example.weather_app.viewmodel.AppState

class RepositoryLocalImpl: Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            SystemClock.sleep(200L)
        }.start()
        return Weather()
    }

    override fun getWeatherList(): List<Weather> {
        Thread {
            SystemClock.sleep(200L)
        }.start()
        return listOf(Weather())
    }
}