
package com.github.flickr.data.models.remote

import com.github.flickr.data.models.local.PhotoItem

import java.util.ArrayList

/**
 * Holds the list of photo items from the sever
 *
 * Created by gaurav
 */
data class PhotoResult(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val total: String,
        val photo: ArrayList<PhotoItem>)

data class ResponsePhotoItemHolder(
        val stat: String,
        val photos: PhotoResult)