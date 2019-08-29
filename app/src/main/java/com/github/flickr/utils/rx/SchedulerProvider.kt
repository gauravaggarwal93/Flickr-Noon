
package com.github.flickr.utils.rx

import io.reactivex.Scheduler

/**
 * Created by gaurav.
 */

interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler

    fun trampoline(): Scheduler

}
