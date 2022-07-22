package com.example.weather_app.model

import android.os.SystemClock
import com.example.weather_app.domain.Weather

class RepositoryRemoteImpl: RepositoryOne{
    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            SystemClock.sleep(200L)
        }.start()
        return Weather()
    }
}