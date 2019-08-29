
package com.github.flickr.data.source.network

import okhttp3.OkHttpClient

/**
 * Utilities for Network related stuffs
 *
 * Created by gaurav
 */
object NetworkUtils {

    // Adds token as a header to the OkHttpClient making the request.
    val httpClient: OkHttpClient
        get() = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build()

                    chain.proceed(request)
                }
                .build()

    // Adds token as a header to the OkHttpClient making the request.
    fun getHttpClient(httpClient: OkHttpClient.Builder): OkHttpClient {
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build()

            chain.proceed(request)
        }
        return httpClient.build()
    }

}
