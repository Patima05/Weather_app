package com.example.weather_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app.databinding.FragmentWeatherListBinding
import com.example.weather_app.viewmodel.AppState
import com.example.weather_app.viewmodel.WeatherListViewModel

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    lateinit var binding: FragmentWeatherListBinding
    lateinit var viewModel: WeatherListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel :: class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState>{
            override fun onChanged(t: AppState) {
               renderData(t)
            }
        })

        viewModel.sentRequest()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {
                Toast.makeText(requireContext(), "Ошибка загрузки данных данных", Toast.LENGTH_LONG).show()
            }
            is AppState.loading -> {
                Toast.makeText(requireContext(), "Выполняется загрузка данных", Toast.LENGTH_LONG).show()
            }
            is AppState.Success -> {
                val result = appState.weatherData;
                binding.cityName.text = result.city.name.toString()
                binding.temperatureValue.text = result.temperature.toString()
                binding.cityCoordinates.text = "${result.city.lat} : ${result.city.lon}"
                binding.feelsLikeValue.text = result.feelsLike.toString()

            }
        }

    }


}