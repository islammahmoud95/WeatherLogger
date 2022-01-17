package com.example.weatherlogger.ui.base

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson


abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected var activity: BaseActivity<*>? = null
    lateinit var mViewDataBinding: T

    val PERMISSIN_REQUEST_CODE=34
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            this.activity = context
        }
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        return mViewDataBinding.root
    }


    fun openActivityAndClearAll(destinationActivity: Class<*>) {
        activity?.openActivityAndClearAll(destinationActivity)
    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int
    ):  MaterialAlertDialogBuilder? {
        return activity?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(titleResId))
                .setMessage(getString(messageResId))
                .setPositiveButton(resources.getString(posResText)) { dialog, which ->
                    // Respond to positive button press
                }
        }

    }

    fun showConfirmationMessage(
        titleResId: Int, messageResId: Int, posResText: Int, negResText: Int,
        positiveClick: () -> Unit
    ): MaterialAlertDialogBuilder? {
        return activity?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(titleResId))
                .setMessage(getString(messageResId))
                .setNegativeButton(resources.getString( negResText)) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(resources.getString(posResText)) { dialog, which ->
                    // Respond to positive button press
                }
        }

    }


    fun showConfirmationMessage(
        messageResId: Int, posResText: Int
    ):  MaterialAlertDialogBuilder? {
        return activity?.let {
            MaterialAlertDialogBuilder(it)
                .setMessage(getString(messageResId))
                .setPositiveButton(resources.getString(posResText)) { dialog, which ->
                    // Respond to positive button press
                }
        }

    }

    fun showConfirmationMessage(title: String, message: String, posText: String):  MaterialAlertDialogBuilder? {
        return activity?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(posText) { dialog, which ->
                    // Respond to positive button press
                }
        }

    }

    fun showConfirmationMessage(message: String, posText: String): MaterialAlertDialogBuilder? {
        return activity?.let {
            MaterialAlertDialogBuilder(it)
                .setMessage(message)
                .setPositiveButton(posText) { dialog, which ->
                    // Respond to positive button press
                }
        }

    }

    open fun showConfirmationMessage(message: String, posText: Int): MaterialAlertDialogBuilder? {
        return activity?.let {
            MaterialAlertDialogBuilder(it)
                .setMessage(message)
                .setPositiveButton(posText) { dialog, which ->
                    // Respond to positive button press
                }
        }
    }

    fun ShowProgress (it:Boolean){
        if (it)
            showProgressDialog()
        else hideProgressDialog()

    }
    fun showProgressDialog() {
        activity?.showProgressDialog()
    }

    fun hideProgressDialog() {
        activity?.hideProgressDialog()
    }

    fun provideItemDecoration(): DividerItemDecoration {
        return activity?.provideItemDecoration()!!
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean? {
        return activity?.hasPermission(permission)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int,
                                 luncher:ActivityResultLauncher<Array<String>>
    ) {

        luncher.launch(permissions)
    }

    fun hideKeyboard() {
        activity?.hideKeyboard()
    }

    fun showLongToast(message: String) {
        activity?.showLongToast(message)
    }

    fun showShortToast(message: String) {
        activity?.showShortToast(message)
    }

    fun showLongToast(resourceId: Int) {
        activity?.showLongToast(resourceId)
    }

    fun showShortToast(resourceId: Int) {
        activity?.showShortToast(resourceId)
    }

    fun backClicked(view: View) {
        activity?.backClicked(view)
    }

    fun homeClicked(view: View) {
        activity?.homeClicked(view)
    }

    fun openActivity(destinationActivity: Class<*>, bundle: Bundle? = null) {
        activity?.openActivity(destinationActivity, bundle)
    }

    fun openActivityAndFinish(destinationActivity: Class<*>) {
        activity?.openActivityAndFinish(destinationActivity)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int


    fun getLocationSittingRe(){
        val getContent = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            // Handle the returned Uri
        }

    }


}
