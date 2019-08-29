package com.github.flickr.di.module

import android.app.Application
import android.content.Context

import com.github.flickr.di.ApplicationContext

import dagger.Module
import dagger.Provides

/**
 * Modules related to application
 *
 * Created by gaurav.
 */

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

}
