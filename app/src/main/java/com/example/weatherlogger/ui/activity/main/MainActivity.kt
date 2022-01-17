package com.example.weatherlogger.ui.activity.main

import android.content.IntentSender
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.weatherlogger.R
import com.example.weatherlogger.app.ApplicationApp
import com.example.weatherlogger.databinding.ActivityMainBinding
import com.example.weatherlogger.ui.base.BaseActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.Task

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int =R.layout.activity_main
    val viewModel: MainViewModel by viewModels {
        MainViewModel.AttachedViewModelFactory(
            ((application) as ApplicationApp).weatherUseCases)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(mViewDataBinding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }




}