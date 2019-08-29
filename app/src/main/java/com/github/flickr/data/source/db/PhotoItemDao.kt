
package com.github.flickr.data.source.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import com.github.flickr.data.models.local.PhotoItem

import io.reactivex.Flowable

/**
 * PhotoItem Data access object for connection to SQLite DB using Room
 *
 * Created by gaurav
 */

@Dao
interface PhotoItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleItem(photoItem: PhotoItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultipleItem(photoItemList: List<PhotoItem>)

    @Query("SELECT * FROM photos WHERE id = :itemId")
    fun fetchItemByItemId(itemId: Int): Flowable<PhotoItem>

    @Query("SELECT * FROM photos")
    fun fetchItems(): Flowable<List<PhotoItem>>

    @Update
    fun updateItem(photoItem: PhotoItem)

    @Delete
    fun deleteItem(photoItem: PhotoItem)

}