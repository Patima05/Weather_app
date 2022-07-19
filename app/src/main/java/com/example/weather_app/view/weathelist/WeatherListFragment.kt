package com.example.weather_app.view.weathelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentWeatherListBinding
import com.example.weather_app.domain.Weather
import com.example.weather_app.view.details.DetailsFragment
import com.example.weather_app.view.details.OnItemClick
import com.example.weather_app.viewmodel.AppState
import com.example.weather_app.viewmodel.WeatherListViewModel

class WeatherListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    var isRussian = true

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

        binding.weatherListFragmentFAB.setOnClickListener{
            isRussian = !isRussian
            if(isRussian){
                viewModel.getWeatherListForRussia()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }else{
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
            }
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                Toast.makeText(
                    requireContext(),
                    "Ошибка загрузки данных данных",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is AppState.loading -> {
                Toast.makeText(requireContext(), "Выполняется загрузка данных", Toast.LENGTH_SHORT)
                    .show()
            }
            is AppState.SuccessOne -> {
                val result = appState.weatherData
            }
            is AppState.SuccessMulti -> {
                binding.fragmentRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherListData, this)
            }

        }
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }


}