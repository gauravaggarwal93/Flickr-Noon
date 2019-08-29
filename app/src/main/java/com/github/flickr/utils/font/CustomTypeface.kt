
package com.github.flickr.utils.font

import android.content.res.AssetManager
import android.graphics.Typeface

import com.github.flickr.utils.AppLogger

import java.util.Hashtable

/**
 * Another helper function to cache and provide typeface
 *
 * Created by gaurav
 */

object CustomTypeface {
    private val TAG = "TypefaceHelper"

    private val cache = Hashtable<String, Typeface>()

    operator fun get(am: AssetManager, assetPath: String): Typeface? {
        if (!cache.containsKey(assetPath)) {
            try {
                val t = Typeface.createFromAsset(am,
                        assetPath)
                cache[assetPath] = t
            } catch (e: Exception) {
                AppLogger.e(TAG, "Could not get typeface '" + assetPath
                        + "' because " + e.message)
                return null
            }

        }
        return cache[assetPath]
    }
}