
package com.github.flickr.data.source.network

import com.github.flickr.data.models.remote.ResponsePhotoItemHolder
import com.github.flickr.utils.FlickrUtils

import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API services to connect to server
 *
 * Created by gaurav
 */

interface APIService {

    @GET("rest?format=json&nojsoncallback=1&method=" + FlickrUtils.METHOD_SEARCH + "&extras=url_" + FlickrUtils.SMALL_360 +
                "&safe_search=1")
    fun getImageItemList(@Query("api_key") key: String,
                         @Query("text") query: String,
                         @Query("page") page: Int,
                         @Query("per_page") perPage: Int): Flowable<Response<ResponsePhotoItemHolder>>

}
