package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.view.weathelist.WeatherListFragment

class MainActivity : AppCompatActivity() {

        lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            if (savedInstanceState == null){
                supportFragmentManager.
                        beginTransaction().
                        replace(R.id.container, WeatherListFragment.newInstance()).
                        commit()

            }
    }
}