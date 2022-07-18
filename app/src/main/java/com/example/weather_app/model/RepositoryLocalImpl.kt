package com.example.weather_app.model

import android.os.SystemClock
import com.example.weather_app.domain.Weather
import com.example.weather_app.domain.getRussianCities
import com.example.weather_app.domain.getWorldCities

class RepositoryLocalImpl: RepositoryOne, RepositoryMulti {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }

    override fun getListWeather(location: Location): List<Weather> {
        return when(location){
            Location.Russian -> getRussianCities()
            Location.World -> getWorldCities()
        }
    }
}