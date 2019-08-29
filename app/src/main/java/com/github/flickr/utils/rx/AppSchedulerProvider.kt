
package com.github.flickr.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Helper class to provide [Schedulers] for RxJava related operations
 *
 * Created by gaurav.
 */

class AppSchedulerProvider : SchedulerProvider {

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()

    override fun trampoline(): Scheduler = Schedulers.trampoline()

}
