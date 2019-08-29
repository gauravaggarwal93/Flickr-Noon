package com.github.flickr.data.source.prefs

import android.content.Context
import android.content.SharedPreferences

import com.github.flickr.di.ApplicationContext
import com.github.flickr.di.PreferenceInfo

import javax.inject.Inject
import javax.inject.Singleton

/**
 * An Helper class for Shared Preferences
 *
 * Created by gaurav.
 */

@Singleton
class AppPreferencesHelper @Inject
constructor(@ApplicationContext context: Context,
            @PreferenceInfo prefFileName: String) : PreferencesHelper {

    private val prefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getBoolean(key: String): Boolean {
        return PrefsUtils.getBooleanSharedPref(prefs, key)
    }

    override fun getLong(key: String): Long {
        return PrefsUtils.getLongSharedPref(prefs, key)
    }

    override fun getInt(key: String): Int {
        return PrefsUtils.getIntSharedPref(prefs, key)
    }

    override fun getString(key: String): String {
        return PrefsUtils.getStringSharedPref(prefs, key)
    }

    override fun setBoolean(key: String, value: Boolean) {
        PrefsUtils.setBooleanSharedPref(prefs, key, value)
    }

    override fun setLong(key: String, value: Long) {
        PrefsUtils.setLongSharedPref(prefs, key, value)
    }

    override fun setInt(key: String, value: Int) {
        PrefsUtils.setIntSharedPref(prefs, key, value)
    }

    override fun getString(key: String, value: String) {
        PrefsUtils.setStringSharedPref(prefs, key, value)
    }

}
