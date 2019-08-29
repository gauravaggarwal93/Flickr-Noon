package com.github.flickr.di.component

import android.app.Application
import android.content.Context

import com.github.flickr.MainApplication
import com.github.flickr.data.source.repository.AppRepository
import com.github.flickr.di.ApplicationContext
import com.github.flickr.di.module.ApplicationModule
import com.github.flickr.di.module.DataModule
import com.github.flickr.di.module.NetworkModule

import javax.inject.Singleton

import dagger.Component

/**
 * Application component connecting modules that have application scope
 *
 * Created by gaurav
 */

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun getAppRepository(): AppRepository

    fun inject(app: MainApplication)

    @ApplicationContext
    fun context(): Context

    fun application(): Application

}
