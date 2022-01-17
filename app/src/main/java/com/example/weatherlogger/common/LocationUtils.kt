package com.example.weatherlogger.common

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.weatherlogger.interfaces.GetLatLng
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task

class LocationUtils (val latLng: GetLatLng,val context:Activity){


    var locationCallback: LocationCallback? = null
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    fun createLocationRequest(luncher: ActivityResultLauncher<IntentSenderRequest>) {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            getLastLocation()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()

                    luncher.launch(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {

                }
            }
        }
    }

    fun startLocationUpdates() {
         mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 500
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    Log.e("xxxxxxxxxx","location")
                    latLng.LatLngLocation(LatLng(location.latitude,location.longitude))
                }
            }
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        ).addOnSuccessListener {
            Log.e("xxxxxxxxxx","location")
        }

    }

    fun getLastLocation() {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        mFusedLocationProviderClient.lastLocation.addOnSuccessListener((context as Activity)) { location ->
            if (location != null) {

                startLocationUpdates()
                latLng.LatLngLocation(LatLng(location.latitude,location.longitude))
            } else {
                startLocationUpdates()
                getLastLocation()
            }
        }
    }


    fun CheckLocationPermissins():Boolean{
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

}