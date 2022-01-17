package com.example.weatherlogger.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.weather.MainWeaTher
import com.example.domain.entities.weather.Weather
import com.example.weatherlogger.R
import com.example.weatherlogger.databinding.AdapterMainWeatherBinding
import com.example.weatherlogger.databinding.AdapterWeatherBinding
import com.example.weatherlogger.interfaces.ClickItem


class WeatherAdapter (var context: Context, var results: ArrayList<Weather>, val clickItem: ClickItem) :
RecyclerView.Adapter<WeatherAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.getContext())
        val binding : AdapterWeatherBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.adapter_weather, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result=results[position]
        holder.binding.result=result


    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder( val binding: AdapterWeatherBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
    }






}