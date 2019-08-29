
package com.github.flickr.data.source.prefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Utilities for Shared Preferences
 *
 * Created by gaurav
 */

object PrefsUtils {

    //clears all the shared prefs
    fun clearSharedPrefs(sp: SharedPreferences) {
        sp.edit().clear().apply()
    }

    //GENERIC PREFS UTIL
    fun getLongSharedPref(context: Context, key: String): Long {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        return settings.getLong(key, 0)
    }

    fun getLongSharedPref(sp: SharedPreferences, key: String): Long {
        return sp.getLong(key, 0)
    }

    fun setLongSharedPref(context: Context, key: String, value: Long) {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = settings.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun setLongSharedPref(sp: SharedPreferences, key: String, value: Long) {
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getIntSharedPref(sp: SharedPreferences, key: String): Int {
        return sp.getInt(key, 0)
    }

    fun setIntSharedPref(sp: SharedPreferences, key: String, value: Int) {
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getBooleanSharedPref(sp: SharedPreferences, key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun setBooleanSharedPref(sp: SharedPreferences, key: String, value: Boolean) {
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStringSharedPref(sp: SharedPreferences, key: String): String {
        return sp.getString(key, "")
    }

    fun setStringSharedPref(sp: SharedPreferences, key: String, value: String) {
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }
}
