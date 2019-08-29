package com.github.flickr.ui.custom

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

import com.github.flickr.utils.font.CustomFont

/**
 * A Custom text view
 *
 * Created by gaurav
 */
class CustomTextView : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        CustomFont.setCustomFont(this, context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        CustomFont.setCustomFont(this, context, attrs)
    }
}
