
package com.github.flickr.data.source.repository

import com.github.flickr.data.models.local.PhotoItem
import com.github.flickr.data.models.remote.PhotoResult

import io.reactivex.Flowable
/**
 * Created by gaurav
 */

interface AppDataSource {

    fun getPhotoResult(key: String,
                       query: String,
                       page: Int, perPage: Int): Flowable<PhotoResult>

    fun updatePhotoItemList(photoItems: List<PhotoItem>)

}
