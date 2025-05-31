package com.product.trtcasecompose.core.utils

object ErrorMessages {
    const val UNAUTHORIZED = "Unauthorized access! Please check the token."
    const val FORBIDDEN = "Access denied! You do not have permission to access this resource."
    const val NOT_FOUND = "Content not found."
    const val SERVER_ERROR = "Server error: %d"
    const val IO_ERROR = "Unable to reach the server. Please check your internet connection."
    const val UNKNOWN = "An unknown error occurred."
    const val ERROR_NO_LOCAL_DATA = "No local data found."
    const val ERROR_NO_INTERNET =
        "No internet connection. And no local data was found. Please enable your internet and try again."
    const val ERROR_DATA_LOAD_FAILED =
        "Data could not be loaded. Please check your internet connection and try again."
}
