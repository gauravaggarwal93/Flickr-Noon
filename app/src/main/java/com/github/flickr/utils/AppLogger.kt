
package com.github.flickr.utils

import android.util.Log

import com.github.flickr.BuildConfig

/**
 * An App logger to only log in Debug mode
 *
 * Created by gaurav
 */

object AppLogger {

    private var isDebugMode = true

    fun init() {
        if (!BuildConfig.DEBUG) {
            isDebugMode = false
        }
    }

    fun d(tag: String, msg: String) {
        if (isDebugMode)
            Log.d(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (isDebugMode)
            Log.e(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (isDebugMode)
            Log.w(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (isDebugMode)
            Log.i(tag, msg)
    }

    fun v(tag: String, msg: String) {
        if (isDebugMode)
            Log.v(tag, msg)
    }

}
