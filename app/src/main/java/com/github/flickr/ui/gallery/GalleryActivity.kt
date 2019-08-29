package com.github.flickr.ui.gallery

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.github.flickr.R
import com.github.flickr.base.BaseActivity
import com.github.flickr.utils.applyFontForToolbarTitle
import com.github.flickr.utils.findFragmentById
import com.github.flickr.utils.setFragment

/**
 * The Gallery activity, holds the [GalleryFragment]
 *
 * Created by gaurav
 */
class GalleryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        setupToolbar()

        val galleryFragment = findFragmentById<GalleryFragment>(R.id.content_frame)
        if (galleryFragment == null) {
            setFragment(GalleryFragment.newInstance(), R.id.content_frame)
        }

    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
        supportActionBar?.title = getText(R.string.toolbar_title)

        toolbar.applyFontForToolbarTitle()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
