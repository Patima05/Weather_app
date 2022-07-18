package com.example.weather_app.viewmodel

import android.os.SystemClock.sleep
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.model.*
import com.example.weather_app.viewmodel.AppState
import java.lang.IllegalStateException

class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel(){

    lateinit var repositoryOne: RepositoryOne
    lateinit var repositoryMulti: RepositoryMulti

    fun getLiveData(): MutableLiveData<AppState> {
        chooseRepository()
        return liveData
    }

    private fun chooseRepository() {
        repositoryOne = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
        repositoryMulti = RepositoryLocalImpl()
    }

    fun getWeatherListForRussia(){
        sentRequest(Location.Russian)
    }

    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location) {
        liveData.value = AppState.loading
        if ((0..3).random() == 1) {
            try {
                liveData.postValue(AppState.Error(IllegalStateException("Исключение: IllegalStateException")))
            }catch (e: IllegalStateException){
                liveData.value = AppState.Error(e)
            }
        } else {
            liveData.value = AppState.SuccessMulti(repositoryMulti.getListWeather(location))
        }
    }
    private fun isConnection(): Boolean {
        return false;
    }
}