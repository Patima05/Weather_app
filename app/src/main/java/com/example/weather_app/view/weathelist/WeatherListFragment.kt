package com.example.weather_app.view.weathelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentWeatherListBinding
import com.example.weather_app.domain.Weather
import com.example.weather_app.view.details.DetailsFragment
import com.example.weather_app.view.details.OnItemClick
import com.example.weather_app.viewmodel.AppState
import com.example.weather_app.viewmodel.WeatherListViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    var isRussian = true

    lateinit var binding: FragmentWeatherListBinding
    lateinit var viewModel: WeatherListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { t -> renderData(t) }

        binding.weatherListFragmentFAB.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherListForRussia()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            } else {
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
            }
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.root.showErrorMessage(R.string.data_error,R.string.try_again) { _ ->
                    if (isRussian){
                        viewModel.getWeatherListForWorld()
                    }else{
                        viewModel.getWeatherListForRussia()
                    }
                }
            }
            is AppState.loading -> {
                binding.root.showLoadingMessage(R.string.data_loading)
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

private fun View.showLoadingMessage(stringRef: Int) {
    Snackbar.make(this, stringRef, Snackbar.LENGTH_SHORT).show()
}

private fun View.showErrorMessage(stringRef: Int, stringBlockRef: Int, block:(v:View)->Unit) {
    Snackbar.make(this, stringRef, Snackbar.LENGTH_SHORT).setAction(stringBlockRef, block).show()
}
