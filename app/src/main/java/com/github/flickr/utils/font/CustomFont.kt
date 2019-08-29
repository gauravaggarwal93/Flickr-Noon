
package com.github.flickr.utils.font

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

import com.github.flickr.R

/**
 * Helper function to set Custom fonts
 *
 * Created by gaurav
 */

object CustomFont {

    /**
     * Sets a font on a TextView based on the custom com.my.package:typeface attribute
     * If the custom font attribute isn't found in the attributes nothing happens
     * @param textView
     * @param context
     * @param attrs
     */
    fun setCustomFont(textView: TextView, context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont)
        val font = a.getString(R.styleable.CustomFont_typeface)
        setCustomFont(textView, font, context)
        a.recycle()
    }

    /**
     * Sets a font on a TextView
     * @param textView
     * @param font
     * @param context
     */
    fun setCustomFont(textView: TextView, tFont: String?, context: Context) {
        var font = tFont
        if (font == null) {
            font = "fonts/OpenSans-Light.ttf"
        }
        val tf = FontCache.get(font, context)
        if (tf != null) {
            textView.typeface = tf
        }
    }

}
