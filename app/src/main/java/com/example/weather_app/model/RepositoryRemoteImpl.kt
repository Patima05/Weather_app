package com.example.weather_app.model

import android.os.SystemClock
import com.example.weather_app.domain.Weather

class RepositoryRemoteImpl: Repository {
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            SystemClock.sleep(2000L)
        }.start()
        return Weather()
    }

    override fun getWeatherList(): List<Weather> {
        Thread {
            SystemClock.sleep(3000L)
        }.start()
        return listOf(Weather())
    }
}