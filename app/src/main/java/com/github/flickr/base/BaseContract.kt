package com.github.flickr.base

import android.support.annotation.StringRes

/**
 * The base contract
 * Consists of the Presenter and View interface
 * Helps in communication/establish contract between View and Presenter
 *
 * Created by gaurav
 */

interface BaseContract {

    interface View<T> {

        fun showProgressDialog()

        fun dismissProgressDialog()

        fun showToastMessage(message: String?)

        fun showToastMessage(@StringRes stringResourceId: Int)

        fun showSnackBarMessage(message: String?)

        fun showSnackBarMessage(@StringRes stringResourceId: Int)

        fun onError(message: String?)

        fun onError(@StringRes resId: Int)

    }

    interface Presenter<V> {

        fun onAttach(view: V)

        fun onDetach()

        fun handleApiError(throwable: Throwable?)

    }

}
