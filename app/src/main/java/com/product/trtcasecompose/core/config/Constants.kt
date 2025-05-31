package com.product.trtcasecompose.core.config

import com.product.trtcasecompose.BuildConfig

object Constants {
    //burasını normalde local.properties'e içinde tutarım. şu anda sizin için gradle.properties'e  koydum
    val API_KEY: String
        get() = BuildConfig.TMDB_API_KEY

    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
}