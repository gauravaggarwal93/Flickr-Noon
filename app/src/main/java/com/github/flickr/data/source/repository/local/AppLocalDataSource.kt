package com.github.flickr.data.source.repository.local

import com.github.flickr.data.models.local.PhotoItem
import com.github.flickr.data.models.remote.PhotoResult
import com.github.flickr.data.source.db.AppDatabase
import com.github.flickr.data.source.db.PhotoItemDao
import com.github.flickr.data.source.repository.AppDataSource

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Flowable

/**
 * Concrete implementation of a data source as a db using room.
 */
@Singleton
class AppLocalDataSource @Inject
constructor(database: AppDatabase) : AppDataSource {

    private val photoItemDao: PhotoItemDao = database.imageItemDao()

    override fun getPhotoResult(key: String, query: String, page: Int, perPage: Int): Flowable<PhotoResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updatePhotoItemList(photoItems: List<PhotoItem>) {
        photoItemDao.insertMultipleItem(photoItems)
    }
}