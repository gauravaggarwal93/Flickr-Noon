package com.github.flickr.di.module

import com.github.flickr.BuildConfig
import com.github.flickr.data.source.network.APIService
import com.github.flickr.data.source.network.NetworkAPIs
import com.github.flickr.data.source.network.NetworkUtils
import com.github.flickr.data.source.network.APIHelper
import com.github.flickr.utils.AppConstants

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Modules related to network
 *
 * Created by gaurav.
 */

@Module
class NetworkModule {

    private var client: OkHttpClient.Builder? = null

    constructor()

    constructor(client: OkHttpClient.Builder) {
        this.client = client
    }

    @Provides
    @Singleton
    internal fun provideCall(): Retrofit {
        val retrofit: Retrofit
        if (client == null) {
            client = OkHttpClient.Builder()
                    .connectTimeout(AppConstants.NETWORK_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                    .writeTimeout(AppConstants.NETWORK_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                    .readTimeout(AppConstants.NETWORK_TIMEOUT_IN_SEC, TimeUnit.SECONDS)

            retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.FLICKR_BASE_URL)
                    .client(NetworkUtils.getHttpClient(client!!))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

        } else {
            retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.FLICKR_BASE_URL)
                    .client(NetworkUtils.httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        return retrofit
    }

    @Provides
    @Singleton
    internal fun providesNetworkService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    internal fun providesRetrofitHelper(apiHelper: APIHelper): NetworkAPIs {
        return apiHelper
    }

}
