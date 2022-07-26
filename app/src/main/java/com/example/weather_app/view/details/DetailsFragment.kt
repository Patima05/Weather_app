package com.example.weather_app.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather_app.databinding.FragmentDetailsBinding
import com.example.weather_app.domain.Weather
import com.example.weather_app.model.dto.WeatherDTO
import com.example.weather_app.utils.OnResponse
import com.example.weather_app.utils.WeatherLoader

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)

        weather?.let { weatherLocal ->

            WeatherLoader.requestFirstVariant(
                weatherLocal.city.lat,
                weatherLocal.city.lon,
                object : OnResponse {
                    override fun onResponse(weather: WeatherDTO) {
                        bindWeatherLocalWithWeatherDTO(weatherLocal, weather)
                    }
                }
            )

            WeatherLoader.requestSecondVariant(
                weatherLocal.city.lat,
                weatherLocal.city.lon
            ) { weatherDTO ->
                bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
            }
        }
    }
    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: Weather,
        weatherDTO: WeatherDTO
    ) {
        requireActivity().runOnUiThread {
            renderData(weatherLocal.apply {
                weatherLocal.feelsLike.let { weatherDTO.fact.feels_like }
                weatherLocal.temperature.let { weatherDTO.fact.temp }
            })
        }
    }

    private fun renderData(weather: Weather) {
        with(binding) {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "unique"
        fun newInstance(weather: Weather): DetailsFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            val fr = DetailsFragment()
            fr.arguments = bundle
            return fr
        }
    }
}