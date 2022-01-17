package com.example.weatherlogger.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.weather.MainWeaTher
import com.example.weatherlogger.R
import com.example.weatherlogger.databinding.AdapterMainWeatherBinding
import com.example.weatherlogger.interfaces.ClickItem


class MainWeatherAdapter (var context: Context, var results: ArrayList<MainWeaTher>, val clickItem: ClickItem) :
RecyclerView.Adapter<MainWeatherAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.getContext())
        val binding :AdapterMainWeatherBinding=
            DataBindingUtil.inflate(layoutInflater, R.layout.adapter_main_weather, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result=results[position]
        holder.binding.result=result
        holder.binding.btn.setOnClickListener{
            result.id?.let { it1 -> clickItem.Item(1,position, it1) }
        }


    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder( val binding: AdapterMainWeatherBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
    }






}