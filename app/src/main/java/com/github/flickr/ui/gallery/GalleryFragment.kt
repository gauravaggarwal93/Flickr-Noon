package com.github.flickr.ui.gallery

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.flickr.R
import com.github.flickr.base.BaseMVPFragment
import com.github.flickr.data.models.local.PhotoItem
import com.github.flickr.ui.adapter.GalleryItemListAdapter
import com.github.flickr.utils.*
import kotlinx.android.synthetic.main.activity_gallery.*

import kotlinx.android.synthetic.main.fragment_gallery.*

import javax.inject.Inject

/**
 * Gallery Fragment where images are displayed
 * Extends functionality of [BaseMVPFragment]
 * Implements Screen specific ui tasks [GalleryContract.View]
 *
 * Created by gaurav
 */
class GalleryFragment : BaseMVPFragment<GalleryContract.Presenter>(), GalleryContract.View {

    private lateinit var inflatedView: View

    @Inject
    lateinit var presenter: GalleryContract.Presenter

    private var itemAdapter: GalleryItemListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflatedView = inflater.inflate(R.layout.fragment_gallery, container, false)
        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val component = activityComponent
        component.inject(this)
        presenter.onAttach(this)

        presenter.loadFirstPhotos(false, FlickrUtils.API_KEY, FlickrUtils.DEFAULT_QUERY)

        view_more_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                presenter.loadNextPhotos(false, FlickrUtils.API_KEY, FlickrUtils.DEFAULT_QUERY)
            }
        })
    }

    /**
     * Sets the recycler view with Scroll Listener for pagination support
     */
    private fun setupRecyclerView() {

        val gridLayoutManager = GridLayoutManager(context, 2)
        item_recycler_view.layoutManager = gridLayoutManager

        //listen to scrolling, and calculate page number to load new items
        //custom support for pagination, improves performance
        val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()
                if (!(presenter as GalleryPresenter).isLoading && !(presenter as GalleryPresenter).isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        showBottomButton()
                    }
                }
            }
        }

        //set the scroll listener
        item_recycler_view.addOnScrollListener(recyclerViewOnScrollListener)
    }


    /**
     * Initializes the list for the first time with photos data
     */
    override fun initItemList(photoItemList: List<PhotoItem>) {
        item_recycler_view.toVisible()
        empty_list_text.toGone()

        itemAdapter = GalleryItemListAdapter(context!!, photoItemList, object : GalleryItemListAdapter.ClickListener {
            override fun onClick(view: View?, position: Int) {
            }
        })
        item_recycler_view.adapter = itemAdapter
    }

    /**
     * Updates the list with photos data
     */
    override fun refreshItemList() {
        itemAdapter?.notifyDataSetChanged()
    }

    /**
     *  Shows empty photos list messages
     */
    override fun showEmptyListUI() {
        hideBottomButton()
        item_recycler_view.toGone()
        empty_list_text.toVisible()
    }

    /**
     *  Shows bottom loading when fetching new elements
     */
    override fun showBottomButton() {
        view_more_btn.toVisible()
    }

    /**
     * Hide the bottom loading
     */
    override fun hideBottomButton() {
        view_more_btn.toInvisible()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    companion object {
        fun newInstance(): GalleryFragment {
            return GalleryFragment()
        }
    }
}
