package com.github.flickr.ui.gallery

import com.github.flickr.base.BaseContract
import com.github.flickr.data.models.local.PhotoItem

/**
 * The gallery contract, consists of Screen specific Presenter and View interface
 *
 * Created by gaurav
 */
interface GalleryContract {

    interface View : BaseContract.View<Presenter> {

        fun initItemList(photoItemList: List<PhotoItem>)

        fun refreshItemList()

        fun showEmptyListUI()

        fun showBottomButton()

        fun hideBottomButton()

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun loadFirstPhotos(refresh: Boolean,
                            key: String?,
                            query: String = "")

        fun loadNextPhotos(refresh: Boolean,
                           key: String?,
                           query: String = "")

    }
}
