
package com.github.flickr.data.source.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.github.flickr.data.models.local.PhotoItem

/**
 * The Room Database that contains the Item table.
 */

@Database(entities = [PhotoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageItemDao(): PhotoItemDao

}