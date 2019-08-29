package com.github.flickr.di.component

import com.github.flickr.di.PerActivity
import com.github.flickr.di.module.ActivityModule
import com.github.flickr.ui.gallery.GalleryActivity
import com.github.flickr.ui.gallery.GalleryFragment

import dagger.Component

/**
 * Activity component connecting modules that have Activity scope
 *
 * Created by gaurav.
 */

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: GalleryActivity)

    fun inject(fragment: GalleryFragment)

}
