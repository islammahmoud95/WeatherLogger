package com.example.weatherlogger.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("image")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.getContext())
        .load("http://openweathermap.org/img/wn/$imageUrl@2x.png").apply(RequestOptions().centerInside())
        .into(view)
}