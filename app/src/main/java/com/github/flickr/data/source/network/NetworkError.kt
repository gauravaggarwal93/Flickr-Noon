
package com.github.flickr.data.source.network

import org.json.JSONObject

import java.io.IOException

import retrofit2.HttpException

import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_CONFLICT
import java.net.HttpURLConnection.HTTP_GONE
import java.net.HttpURLConnection.HTTP_NOT_ACCEPTABLE
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

/**
 * Helps converting network exception to appropriate error type
 *
 * Created by gaurav
 */

class NetworkError : Throwable {
    private val error: Throwable?

    val isRefreshTokenFailure: Boolean
        get() = error is HttpException && error.code() == HTTP_GONE

    val isAcceptanceFailure: Boolean
        get() = error is HttpException && error.code() == HTTP_NOT_ACCEPTABLE

    val isAuthFailure: Boolean
        get() = error is HttpException && error.code() == HTTP_UNAUTHORIZED

    val isNotFound: Boolean
        get() = error is HttpException && error.code() == HTTP_NOT_FOUND

    val isBadRequest: Boolean
        get() = error is HttpException && error.code() == HTTP_BAD_REQUEST

    val isConflictRequest: Boolean
        get() = error is HttpException && error.code() == HTTP_CONFLICT

    val isResponseNull: Boolean
        get() = error is HttpException && error.response() == null

    val appErrorMessage: String
        get() {
            if (this.error is IOException) return NETWORK_ERROR_MESSAGE
            if (this.error is HttpException) {
                val body = error.response().errorBody()
                return if (body != null) {
                    try {
                        val jObjError = JSONObject(body.string())
                        jObjError.getString("message")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        DEFAULT_ERROR_MESSAGE
                    }

                } else
                    DEFAULT_ERROR_MESSAGE

            } else {
                return DEFAULT_ERROR_MESSAGE
            }
        }

    constructor(e: Throwable) : super(e) {
        this.error = e
    }

    constructor() {
        error = null
    }

    fun getErrMessage(): String? {
        return error?.message
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as NetworkError?

        return if (error != null) error == that!!.error else that!!.error == null

    }

    override fun hashCode(): Int {
        return error?.hashCode() ?: 0
    }

    companion object {
        val DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again."
        val NETWORK_ERROR_MESSAGE = "No Internet Connection!"
        private val ERROR_MESSAGE_HEADER = "NetworkError-Message"
        private val BAD_REQUEST_MESSAGE = "This operation is not allowed!"
    }
}
