package com.example.weatherlogger.common

import java.text.SimpleDateFormat
import java.util.*
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object TimeClass{

fun formattedDay(): String {
    val formatterMonth = SimpleDateFormat("MMMM", Locale.getDefault())
    val formatterDayName = SimpleDateFormat("EEE", Locale.getDefault())
    val formatterDay = SimpleDateFormat("dd", Locale.getDefault())
    val formatterYear = SimpleDateFormat("yyyy", Locale.getDefault())
    return formatterDayName.format(Date()) + ", " +
            formatterDay.format(Date()) + " " +
            formatterMonth.format(Date()) + " " +
            formatterYear.format(Date())

}




fun CreateDateWithName(date:Date):String{

    val simpleDataFormate: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a",Locale("en"))
    var time=simpleDataFormate.format(date)

    return time
}
fun CreateTime(expireAt:String):String{
    var time=expireAt
    val simpleDataFormate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss",
        Locale("en"))
    val simpleDataFormate2: SimpleDateFormat = SimpleDateFormat("hh:mm a",Locale("en"))
    var date: Date = Date()
    date=simpleDataFormate.parse(time)
    time=simpleDataFormate2.format(date)
    return time
}

fun getDateWithFormate(expireAt:Date):String{
    var time=""
    val simpleDataFormate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd",
        Locale("en"))
    val simpleDataFormate2: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale("en"))
    var date: Date = Date()
    time=simpleDataFormate2.format(expireAt)
    return time
}


fun getDate(expireAt:String):Date{
    val simpleDataFormate2: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale("en"))
    var date: Date = Date()
    date=simpleDataFormate2.parse(expireAt)
    return date
}


fun CreateDateWithTime(expireAt:String):String{
    var time=expireAt
    val simpleDataFormate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss",
        Locale("en"))
    val simpleDataFormate2: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a",Locale("en"))
    var date: Date = Date()
    date=simpleDataFormate.parse(time)
    time=simpleDataFormate2.format(date)
    return time
}


}


