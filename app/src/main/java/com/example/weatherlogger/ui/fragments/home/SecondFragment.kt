package com.example.weatherlogger.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.weather.Weather
import com.example.weatherlogger.R
import com.example.weatherlogger.app.ApplicationApp
import com.example.weatherlogger.databinding.FragmentSecondBinding
import com.example.weatherlogger.interfaces.ClickItem
import com.example.weatherlogger.ui.activity.main.MainViewModel
import com.example.weatherlogger.ui.adapters.WeatherAdapter
import com.example.weatherlogger.ui.base.BaseFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : BaseFragment<FragmentSecondBinding>(), ClickItem {
    override fun getLayoutId(): Int =R.layout.fragment_second
    val viewModel: MainViewModel by viewModels {
        MainViewModel.AttachedViewModelFactory(
            ((activity?.application) as ApplicationApp).weatherUseCases)
    }

    val arguments: SecondFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.weatherList.layoutManager=LinearLayoutManager(activity)
        viewModel.requestCurrentWeather.id=arguments.cityId
        viewModel.getWeather()
        InitObserver()
    }

    fun InitObserver(){

        viewModel.loading.observe(viewLifecycleOwner, {
            ShowProgress(it)

        })
        viewModel.viewModelChanged.observe(viewLifecycleOwner, {
            mViewDataBinding.viewModel=viewModel

        })

        viewModel.currentWeatherResponse.observe(viewLifecycleOwner, {
            viewModel.currentWeather=it
            viewModel.viewModelChanged.postValue(true)
            val weatherList=ArrayList<Weather>()
            it.weather?.let { it1 -> weatherList.addAll(it1) }
            if (weatherList.size>0)
                mViewDataBinding.weatherList.adapter=
                    activity?.let { it1 -> WeatherAdapter(it1,weatherList,this) }

        })
        viewModel.errorResponse.observe(viewLifecycleOwner, {
            showConfirmationMessage("",it,"OK")
        })

    }

    override fun Item(type: Int, pos: Int, id: Int) {
        TODO("Not yet implemented")
    }

}