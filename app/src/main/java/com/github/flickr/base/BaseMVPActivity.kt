
package com.github.flickr.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.github.flickr.MainApplication

import com.github.flickr.R
import com.github.flickr.di.component.ActivityComponent
import com.github.flickr.di.component.DaggerActivityComponent
import com.github.flickr.di.module.ActivityModule
import com.github.flickr.utils.createProgressDialog

/**
 * Acts a Base Activity class for all other activities which will act as View part of MVP
 * Implements the basic functions as described in [BaseContract.View]
 *
 * Created by gaurav
 */

abstract class BaseMVPActivity<T> : AppCompatActivity(), BaseContract.View<T> {

    private var progressDialog: Dialog? = null
    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as MainApplication).component)
                .build()

    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }

    /**
     * Custom Progress Dialog
     */
    override fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = createProgressDialog()
    }

    override fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun showToastMessage(message: String?) {
        if (!message.isNullOrEmpty())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToastMessage(@StringRes stringResourceId: Int) {
        showToastMessage(getString(stringResourceId))
    }

    override fun showSnackBarMessage(message: String?) {
        if (!message.isNullOrEmpty()) showSnackBar(message)
    }

    override fun showSnackBarMessage(@StringRes stringResourceId: Int) {
        showSnackBarMessage(getString(stringResourceId))
    }

    /**
     * Creates a SnackBar for message display
     */
    private fun showSnackBar(message: String?) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
                message as CharSequence, Snackbar.LENGTH_SHORT)
        val sbView = snackBar.view
        val textView = sbView
                .findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()
    }

    override fun onError(message: String?) {
        if (message != null) {
            showSnackBar(message)
        } else {
            showSnackBar(getString(R.string.default_error_message))
        }
    }

    override fun onError(@StringRes resId: Int) {
        onError(getString(resId))
    }

    override fun onStop() {
        super.onStop()
        dismissProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }

}
