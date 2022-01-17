package com.example.weatherlogger.ui.base

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherlogger.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder



abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(){


    lateinit var transitionDown: Animation
    lateinit var translate: Animation
    lateinit var alpha: Animation
    lateinit var jump_from_up: Animation
    lateinit var jump_from_down: Animation
    lateinit var jump_to_down: Animation
    lateinit var push_right_in: Animation
    lateinit var push_left_in: Animation
    lateinit var push_left_out: Animation
    lateinit var popup_enter: Animation
    lateinit var popup_exit: Animation
    protected lateinit var activity: AppCompatActivity
    protected lateinit var progressDialog: Dialog
    protected lateinit var infoDialog: Dialog

    lateinit var mViewDataBinding: T
    private val TAG: String = BaseActivity::class.java.getSimpleName()

    // Used in checking for runtime permissions.
    val REQUEST_PERMISSIONS_REQUEST_CODE = 34


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title=""
        activity = this
        Init()
        performDataBinding()

       // ShowInfoDialog()
    }




    fun Init(){
//        alpha = AnimationUtils.loadAnimation(activity, R.anim.alpha)
//        jump_from_up = AnimationUtils.loadAnimation(this, R.anim.jump_from_up)
//        jump_from_down = AnimationUtils.loadAnimation(this, R.anim.jump_from_down)
//        jump_to_down = AnimationUtils.loadAnimation(this, R.anim.jump_to_down)
//        push_right_in = AnimationUtils.loadAnimation(this, R.anim.push_right_in)
//        push_left_in = AnimationUtils.loadAnimation(this, R.anim.push_left_in)
//        push_left_out = AnimationUtils.loadAnimation(this, R.anim.push_left_out)
//        popup_enter = AnimationUtils.loadAnimation(this, R.anim.popup_enter)
//        popup_exit = AnimationUtils.loadAnimation(this, R.anim.popup_exit)
//        translate = AnimationUtils.loadAnimation(this, R.anim.translate)
    }

    fun setupActionBar(view: Toolbar, indicatiorID:Int) {
        setSupportActionBar(view)
        supportActionBar?.setHomeAsUpIndicator(indicatiorID)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun openActivity(destinationActivity: Class<*>, bundle: Bundle? = null) {
        val mainIntent = Intent(activity, destinationActivity)
        bundle?.let { mainIntent.putExtras(it) }
        startActivity(mainIntent)
    }

    fun openActivityAndFinish(destinationActivity: Class<*>, bundle: Bundle? = null) {
        val mainIntent = Intent(activity, destinationActivity)
        bundle?.let { mainIntent.putExtras(it) }
        startActivity(mainIntent)
        finish()
    }

    fun openActivityAndClearAll(destinationActivity: Class<*>, bundle: Bundle? = null) {
        val i = Intent(activity, destinationActivity)
        bundle?.let { i.putExtras(it) }
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun provideItemDecoration(): DividerItemDecoration {
        return DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
    }

    fun ShowProgress (it:Boolean){
        if (it)
            showProgressDialog()
        else hideProgressDialog()

    }
    fun showProgressDialog() {
        hideProgressDialog()
        val alertDialogBuilder = AlertDialog.Builder(this).setCancelable(false)
        alertDialogBuilder.setView(R.layout.progress_dialog_loader)
        progressDialog = alertDialogBuilder.create()
        progressDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog.isShowing)
            progressDialog.dismiss()
    }


    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int
    ):  MaterialAlertDialogBuilder? {
        return MaterialAlertDialogBuilder(this)
            .setTitle(getString(titleResId))
            .setMessage(getString(messageResId))
            .setPositiveButton(resources.getString(posResText)) { dialog, which ->
                // Respond to positive button press
            }

    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int, negResText: Int,
        positiveClick: () -> Unit
    ): MaterialAlertDialogBuilder? {
        return MaterialAlertDialogBuilder(this)
            .setTitle(getString(titleResId))
            .setMessage(getString(messageResId))
            .setNegativeButton(resources.getString( negResText)) { dialog, which ->
            // Respond to negative button press
               }
            .setPositiveButton(resources.getString(posResText)) { dialog, which ->
                // Respond to positive button press
            }

    }


    fun showConfirmationMessage(
        messageResId: Int, posResText: Int
    ):  MaterialAlertDialogBuilder? {
        return MaterialAlertDialogBuilder(this)
            .setMessage(getString(messageResId))
            .setPositiveButton(resources.getString(posResText)) { dialog, which ->
                // Respond to positive button press
            }

    }

    fun showConfirmationMessage(title: String, message: String, posText: String):  MaterialAlertDialogBuilder? {
        return MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(posText) { dialog, which ->
                // Respond to positive button press
            }

    }


    open fun showConfirmationMessage(message: String, posText: Int): MaterialAlertDialogBuilder? {
        return MaterialAlertDialogBuilder(this)
            .setMessage(message)
            .setPositiveButton(posText) { dialog, which ->
                // Respond to positive button press
            }
    }

    fun showLongToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(resourceId: Int) {
        Toast.makeText(activity, resourceId, Toast.LENGTH_LONG).show()
    }

    fun showShortToast(resourceId: Int) {
        Toast.makeText(activity, resourceId, Toast.LENGTH_SHORT).show()
    }

    fun backClicked(view: View) {
        super.onBackPressed()
    }

    fun homeClicked(view: View) {
//        openActivity(HomeActivity::class.java)
    }


    fun openFragment(fragment: Fragment, bundle: Bundle? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        bundle?.let { fragment.arguments = it }
        transaction.replace(R.id.container, fragment, fragment.tag)
        transaction.commit()
    }

    /**
     * @return layout resource id
     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

    }

    @LayoutRes
    abstract fun getLayoutId(): Int


    fun createLocationRequest() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this,
                        2)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

}
