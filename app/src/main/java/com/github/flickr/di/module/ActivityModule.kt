package com.github.flickr.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity

import com.github.flickr.data.source.repository.AppRepository
import com.github.flickr.di.ActivityContext
import com.github.flickr.ui.gallery.GalleryContract
import com.github.flickr.ui.gallery.GalleryPresenter
import com.github.flickr.utils.rx.AppSchedulerProvider
import com.github.flickr.utils.rx.SchedulerProvider

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Modules related to activity
 *
 * Created by gaurav.
 */

@Module
class ActivityModule(private val mActivity: AppCompatActivity) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
    }

    @Provides
    internal fun provideActivity(): AppCompatActivity {
        return mActivity
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    internal fun provideGalleryPresenter(appRepository: AppRepository,
                                      schedulerProvider: SchedulerProvider,
                                      compositeDisposable: CompositeDisposable): GalleryContract.Presenter {
        return GalleryPresenter(appRepository, schedulerProvider, compositeDisposable)
    }

}
