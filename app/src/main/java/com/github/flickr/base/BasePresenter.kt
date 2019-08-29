
package com.github.flickr.base

import com.github.flickr.R
import com.github.flickr.data.source.repository.AppRepository
import com.github.flickr.data.source.network.NetworkError
import com.github.flickr.utils.rx.SchedulerProvider

import io.reactivex.disposables.CompositeDisposable

/**
 * Base Presenter class that abstracts some of the repetitive work of a Presenter
 * Implements the basic functions as described in [BaseContract.Presenter]
 *
 * Created by gaurav
 */

abstract class BasePresenter<V : BaseContract.View<*>>(
        protected val dataSource: AppRepository,
        protected val schedulerProvider: SchedulerProvider,
        protected val compositeDisposable: CompositeDisposable) :
        BaseContract.Presenter<V> {

    protected var view: V? = null

    protected val isViewAttached: Boolean
        get() = view != null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.clear()
        view = null
    }

    override fun handleApiError(throwable: Throwable?) {

        if (throwable == null) {
            view?.onError(R.string.default_error_message)
            return
        }

        val networkError = NetworkError(throwable)
        val errorMsg = networkError.appErrorMessage
        if (!errorMsg.isEmpty())
            view?.onError(errorMsg)
        else
            view?.onError(R.string.default_error_message)

    }

}
