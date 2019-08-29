
package com.github.flickr.data.source.repository

import android.support.annotation.VisibleForTesting

import com.github.flickr.data.models.local.PhotoItem
import com.github.flickr.data.models.remote.PhotoResult
import com.github.flickr.data.source.prefs.PreferencesHelper
import com.github.flickr.di.Local
import com.github.flickr.di.Remote

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Flowable

/**
 * The central point to communicate to different data sources like DB, SERVER, SHARED PREFS
 *
 * Created by gaurav
 */

//FIXME: Local DB is not used currently.
//FIXME: Flicker API may give less number of results for a particular page than the per_page parameter, added a temp fix
//FIXME: Flicker API may give redundant results in different pages
//FIXME: Use a Fixed MAX_SIZE for the mCachedPhotoList to avoid OUT_OF_MEMORY error, keep most recent and active items in it
@Singleton
class AppDataRepository @Inject
constructor(@param:Remote private val remoteAppDataSource: AppDataSource,
            @param:Local private val localAppDataSource: AppDataSource,
            private val preferenceHelper: PreferencesHelper) : AppRepository {

    @VisibleForTesting
    internal var cachedPhotoItemList: MutableList<PhotoItem> = mutableListOf()

    /**
     * Holds the current page number
     * if 0: indicates no elements loaded
     * if 1 : loaded first page
     * else : partially loaded
     */
    @VisibleForTesting
    internal var currentPageNumber: Int = 0

    /**
     * Holds the max page available
     */
    @VisibleForTesting
    internal var maxPageNumber: Int = 0

    /**
     * A temporary variable, used to indicate fake end point for pagination
     * This is required as the current API call doesn't indicate an endpoint
     * as Pagination is unsupported and also to not load new elements after endpoint
     */
    var paginationEndPoint: Boolean = false

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    internal var cacheIsDirty = false

    //get the items from the server
    private fun getItemFromServerDB(key: String,
                                    query: String,
                                    page: Int, perPage: Int): Flowable<PhotoResult> {
        return remoteAppDataSource
                .getPhotoResult(key, query, page, perPage)
                .doOnNext { photoResult ->
                    val items = photoResult.photo
                    //mLocalAppDataSource.updatePhotoItemList(items)
                    if (page <= 1)
                        cachedPhotoItemList.clear()
                    if (photoResult.page >= photoResult.pages)
                        paginationEndPoint = true
                    currentPageNumber = photoResult.page
                    maxPageNumber = photoResult.pages
                    cachedPhotoItemList.addAll(items)
                    cacheIsDirty = false
                }
    }

    /**
     * Gets Photo items from data source
     */
    override fun getPhotoResult(key: String,
                                query: String,
                                page: Int, perPage: Int): Flowable<PhotoResult> {
        // NOTE: Add other sources based on conditions
        /* ******************************************
        Eg:
           if(mCacheDirty)
            getItemFromServerDB(key, query, page, perPage)
           else
            getItemFromLocalDB(key, query, page, perPage)
         ***************************************************/
        return getItemFromServerDB(key, query, page, perPage)
    }

    /**
     * Gets the list of photos for current page
     */
    override fun getPhotoItemList(key: String, query: String, page: Int, perPage: Int): Flowable<List<PhotoItem>> {

        //if end of pagination is reached, return empty list
        if(paginationEndPoint)
            return Flowable.just(listOf())

        //request page already available
        if (maxPageNumber != 0 && (page !in (currentPageNumber + 1)..(maxPageNumber))) {

            //calculate the offset from the page and perPage
            val offset = (page - 1) * perPage

            //if items are available in the cache, directly use it, send batch by batch
            if (cachedPhotoItemList.isNotEmpty()
                    && !cacheIsDirty
                    && cachedPhotoItemList.size > offset) {

                //calculate the batch end index
                val endIndex =
                        if ((offset + perPage) < cachedPhotoItemList.size) {
                            (offset + perPage)
                        } else {
                            cachedPhotoItemList.size
                        }

                return Flowable.just(cachedPhotoItemList.subList(offset, endIndex))
            }
        }

        return getPhotoResult(key, query, page, perPage).map { t: PhotoResult -> t.photo }
    }

    /**
     * Gets all the cached elements
     */
    override fun getCachedPhotoItems(): Flowable<List<PhotoItem>> {
        return Flowable.just(cachedPhotoItemList)
    }

    /**
     * Updates local db with photo items
     */
    override fun updatePhotoItemList(photoItems: List<PhotoItem>) {
        //mLocalAppDataSource.updatePhotoItemList(photoItems)
    }

    /**
     * Gets the status for pagination
     */
    override fun getPaginationStatus(): Boolean {
        return paginationEndPoint
    }

    /**
     * Gets current page number
     */
    override fun getPageNumber(): Int {
        return currentPageNumber
    }

    /**
     * Get maximum page available
     */
    override fun getMaxPageNumber(): Int {
        return maxPageNumber
    }

    /**
     * Makes the cache dirty
     */
    override fun refreshItems() {
        cacheIsDirty = true
    }
}
