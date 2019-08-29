package com.github.flickr.ui.gallery

import com.github.flickr.R
import com.github.flickr.base.BasePresenter
import com.github.flickr.data.models.local.PhotoItem
import com.github.flickr.data.source.repository.AppRepository
import com.github.flickr.utils.FlickrUtils
import com.github.flickr.utils.rx.SchedulerProvider

import javax.inject.Inject

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Gallery Presenter where most of the logic stuff happens
 * Extends functionality of [BasePresenter]
 * Implements Screen specific Presenter tasks [GalleryContract.Presenter]
 *
 * Created by gaurav
 */
class GalleryPresenter @Inject
constructor(appRepository: AppRepository,
            schedulerProvider: SchedulerProvider,
            compositeDisposable: CompositeDisposable) :
        BasePresenter<GalleryContract.View>(appRepository, schedulerProvider, compositeDisposable),
        GalleryContract.Presenter {

    private var disposable: Disposable? = null

    //indicates whether it last page
    internal var isLastPage = false

    //indicates whether the items is loading elements or not
    internal var isLoading = false

    //holds the list of all the photos loaded
    internal var photoList: MutableList<PhotoItem> = mutableListOf()

    //holds the current page
    internal var page: Int = 1

    //holds the num element per page
    private val perPage: Int = FlickrUtils.DEFAULT_PAGE_SIZE

    private var query: String = FlickrUtils.DEFAULT_QUERY

    /**
     * Load the photos list from the data source and update the display for the first time
     * Uses Pagination indicated by [page] and limit [perPage]
     */
    override fun loadFirstPhotos(refresh: Boolean, key: String?, query: String) {

        //key is required for the call
        if (key.isNullOrEmpty()) {
            view?.showSnackBarMessage(R.string.default_error_message)
            return
        }

        isLoading = true
        this.query = query

        view?.showProgressDialog()

        if (refresh)
            dataSource.refreshItems()

        //remove the previous disposable from composite disposable, for multiple load items calls
        if (disposable != null)
            compositeDisposable.delete(disposable!!)

        disposable = dataSource.getPhotoItemList(key!!, query, page, perPage)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ photoItems: List<PhotoItem> ->
                    if (!isViewAttached)
                        return@subscribe

                    isLoading = false

                    view?.dismissProgressDialog()

                    if (photoItems.isNotEmpty()) {
                        photoList.clear()
                        photoList.addAll(photoItems)
                        view?.initItemList(photoList)
                    }else
                        view?.showEmptyListUI()
                }, { throwable: Throwable? ->
                    if (!isViewAttached)
                        return@subscribe

                    isLoading = false

                    view?.dismissProgressDialog()
                    handleApiError(throwable)
                })

        compositeDisposable.add(disposable!!)
    }

    /**
     * Load the photos list from the data source and update the display
     * Uses Pagination indicated by [page] and limit [perPage]
     */
    override fun loadNextPhotos(refresh: Boolean, key: String?, query: String) {

        //key is required for the call
        if (key.isNullOrEmpty()) {
            view?.showSnackBarMessage(R.string.default_error_message)
            return
        }

        //end point reached no need to get more data
        if (dataSource.getPaginationStatus()) {
            isLastPage = true
            return
        }

        this.query = query

        //increment the page number
        page++

        isLoading = true

//        view?.showBottomButton()

        if (refresh)
            dataSource.refreshItems()

        //remove the previous disposable from composite disposable, for multiple load items calls
        if (disposable != null)
            compositeDisposable.delete(disposable!!)

        disposable = dataSource.getPhotoItemList(key!!, query, page, perPage)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ photoItems: List<PhotoItem> ->
                    if (!isViewAttached)
                        return@subscribe

                    isLoading = false

                    view?.hideBottomButton()

                    if (photoItems.isNotEmpty()) {
                        photoList.addAll(photoItems)
                        view?.refreshItemList()
                    }

                }, { throwable: Throwable? ->
                    if (!isViewAttached)
                        return@subscribe

                    isLoading = false

                    view?.hideBottomButton()
                    handleApiError(throwable)
                })

        compositeDisposable.add(disposable!!)
    }
}
