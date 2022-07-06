package com.example.weather_app.viewmodel

import android.os.SystemClock.sleep
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.model.Repository
import com.example.weather_app.model.RepositoryLocalImpl
import com.example.weather_app.model.RepositoryRemoteImpl
import com.example.weather_app.viewmodel.AppState
import java.lang.IllegalStateException

class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel(){

    lateinit var repository: Repository

    fun getLiveData(): MutableLiveData<AppState> {
        chooseRepository()
        return liveData
    }

    private fun chooseRepository() {
        repository = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }

    }

    fun sentRequest() {
        liveData.value = AppState.loading
        if ((0..3).random() == 1) {
            try {
                liveData.postValue(AppState.Error(throw IllegalStateException("Исключение: IllegalStateException")))
            }catch (e: IllegalStateException){
                liveData.value = AppState.Error(e)
            }
        } else {
            liveData.value = AppState.Success(repository.getWeather(37.617299900000035, 55.755826))
        }
    }
    private fun isConnection(): Boolean {
        return false;
    }
}