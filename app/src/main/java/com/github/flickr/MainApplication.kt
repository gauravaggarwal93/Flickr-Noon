
package com.github.flickr

import android.app.Application

import com.github.flickr.data.source.repository.AppRepository
import com.github.flickr.di.component.ApplicationComponent
import com.github.flickr.di.component.DaggerApplicationComponent
import com.github.flickr.di.module.ApplicationModule
import com.github.flickr.di.module.DataModule
import com.github.flickr.di.module.NetworkModule
import com.github.flickr.utils.AppLogger

import javax.inject.Inject

/**
 * Entry place when application start
 * Good place to initialize stuff that has an Application Scope
 *
 * Created by gaurav
 */

class MainApplication : Application() {

    @Inject
    lateinit var mRepository: AppRepository

    // Needed to replace the component with a test specific one
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule())
                .networkModule(NetworkModule())
                .build()

        component.inject(this)

        instance = this

        (instance as MainApplication).initializeInstance()

    }

    // Here we do one-off initialisation which should apply to all activities
    // in the application.
    protected fun initializeInstance() {
        //globally initialize the App Logger
        AppLogger.init()
    }

    companion object {

        // Anywhere in the application where an instance is required, this method
        // can be used to retrieve it.
        lateinit var instance: Application
    }

}
