package com.github.flickr.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.flickr.MainApplication

import com.github.flickr.di.component.ActivityComponent
import com.github.flickr.di.component.DaggerActivityComponent
import com.github.flickr.di.module.ActivityModule

/**
 * Base activity without any MVP component
 * Used to declare Activities that are dumb and only act as holder to other fragments
 *
 *
 * Created by gaurav
 */

abstract class BaseActivity : AppCompatActivity() {

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

}
