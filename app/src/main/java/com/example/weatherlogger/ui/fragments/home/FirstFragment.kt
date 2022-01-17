package com.example.weatherlogger.ui.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.res.AssetManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.model.Cities
import com.example.domain.entities.weather.CurrentWeather
import com.example.domain.entities.weather.MainWeaTher
import com.example.weatherlogger.R
import com.example.weatherlogger.app.ApplicationApp
import com.example.weatherlogger.common.LocationUtils
import com.example.weatherlogger.databinding.FragmentFirstBinding
import com.example.weatherlogger.interfaces.ClickItem
import com.example.weatherlogger.interfaces.GetLatLng
import com.example.weatherlogger.ui.activity.main.MainViewModel
import com.example.weatherlogger.ui.adapters.MainWeatherAdapter
import com.example.weatherlogger.ui.base.BaseFragment
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import java.util.List;
import kotlin.math.log
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_first.view.*
import java.lang.reflect.Type


class FirstFragment : BaseFragment<FragmentFirstBinding>(), ClickItem, GetLatLng {
    override fun getLayoutId(): Int =R.layout.fragment_first
    val viewModel: MainViewModel by viewModels {
        MainViewModel.AttachedViewModelFactory(
            ((activity?.application) as ApplicationApp).weatherUseCases)
    }
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val mainWeaTher = ArrayList<MainWeaTher>()
    val adapter by lazy {
        activity?.let { MainWeatherAdapter(it,mainWeaTher,this) }
    }

    val locationUtils by lazy {
        activity?.let { LocationUtils(this, it) }
    }

    val  resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {

            }  else {
                showLongToast("we can't determine your location")
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
                permissions ->

            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    showLongToast("ACCESS_COARSE_LOCATION")
                }



            }
        }

    var latLng:LatLng?=null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewDataBinding.list.layoutManager=LinearLayoutManager(activity)
        mViewDataBinding.list.adapter=adapter
//        val jsoString:String=activity?.applicationContext?.assets?.open("city.json")
//            ?.bufferedReader().use { it?.readText().toString() }
        val type: Type = object : TypeToken<ArrayList<CurrentWeather?>?>() {}.type

        val cities: ArrayList<CurrentWeather> =(Gson()).fromJson(viewModel.arrayStr, type)
//                as ArrayList<CurrentWeather>

        val cityStr=ArrayList<String>()
        cities.forEach{
            it.name?.let { it1 -> cityStr.add(it1) }
        }
        val cityAdapter = activity?.let { ArrayAdapter(it, R.layout.list_item, cityStr) }
        (mViewDataBinding.cityEditText.editText as? AutoCompleteTextView)?.setAdapter(cityAdapter)

        mViewDataBinding.cityTxt.setOnItemClickListener { adapterView, view, i, l ->
            if (cityStr.contains(mViewDataBinding.cityTxt.text.toString())) {
                val index = cityStr.indexOf(mViewDataBinding.cityTxt.text.toString())
                viewModel.requestCurrentWeather.lat = cities[index].coord?.lat
                viewModel.requestCurrentWeather.lon = cities[index].coord?.lon
                viewModel.getWeather()
            }
        }

        InitObserver()


        mViewDataBinding.buttonFirst.setOnClickListener {
            if (locationUtils?.CheckLocationPermissins() == false)
                requestPermissionsSafely(permissions,PERMISSIN_REQUEST_CODE,requestPermissionLauncher)

            locationUtils?.createLocationRequest(resolutionForResult)

        }
    }

    fun InitObserver(){

        viewModel.loading.observe(viewLifecycleOwner, {
            ShowProgress(it)

        })
        viewModel.viewModelChanged.observe(viewLifecycleOwner, {
            mViewDataBinding.viewModel=viewModel

        })

        viewModel.mainWeatherResponse.observe(viewLifecycleOwner, {
            val inserted=mainWeaTher.size
            mainWeaTher.clear()
            mainWeaTher.addAll(it)
            adapter?.notifyDataSetChanged()

        })
        viewModel.errorResponse.observe(viewLifecycleOwner, {
            showConfirmationMessage("",it,"OK")
        })

    }

    override fun onResume() {
        super.onResume()
        locationUtils?.startLocationUpdates()
        viewModel.getMainWeather()

    }

    override fun Item(type: Int, pos: Int, id: Int) {
        findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(
            id
        ))
    }



    override fun LatLngLocation(latLng: LatLng?) {
        Log.e("xxxxxxx",latLng?.latitude.toString())
        this.latLng=latLng
        if (latLng!=null){
            viewModel.requestCurrentWeather.lat= latLng!!.latitude.toFloat()
            viewModel.requestCurrentWeather.lon= latLng!!.longitude.toFloat()
            viewModel.getWeather()

        }
    }


}