package com.github.flickr.data.models.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Describes the Photo Item data to be modeled for local storage and regular use
 *
 * Note:
 * Uses both the Room specific annotations for Local DB
 * Uses Gson annotations for Json serialization for Network requests
 *
 * Created by gaurav
 */

@Entity(tableName = "photos")
data class PhotoItem(
        @field:PrimaryKey
        var id: String,
        var owner: String,
        var secret: String,
        var server: String,
        var farm: Int,
        var title: String?,
        var ispublic: Short,
        var url_n: String?,
        var width_n: String?,
        var height_n: String?)