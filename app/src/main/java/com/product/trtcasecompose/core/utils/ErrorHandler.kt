package com.product.trtcasecompose.core.utils

import retrofit2.HttpException
import java.io.IOException

object ErrorHandler {
    fun handleException(e: Throwable): String {
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    401 -> ErrorMessages.UNAUTHORIZED
                    403 -> ErrorMessages.FORBIDDEN
                    404 -> ErrorMessages.NOT_FOUND
                    else -> String.format(ErrorMessages.SERVER_ERROR, e.code())
                }
            }

            is IOException -> ErrorMessages.IO_ERROR

            else -> e.message ?: ErrorMessages.UNKNOWN
        }
    }
}
