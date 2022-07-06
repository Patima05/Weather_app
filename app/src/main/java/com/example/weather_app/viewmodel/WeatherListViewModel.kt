package com.example.weather_app.viewmodel

import android.os.SystemClock.sleep
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.viewmodel.AppState

class WeatherListViewModel(val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel(){
    fun sentRequest() {
        liveData.value = AppState.loading
        Thread {
            sleep(2000L)
            liveData.postValue(AppState.Success(Any()))
        }.start()
    }
}