package com.product.trtcasecompose.core.util

object TestConstants {
    const val ERROR_MESSAGE = "Server error"

    val FAKE_MOVIES = listOf(
        com.product.trtcasecompose.features.home.domain.model.Movie(
            id = 1,
            title = "Batman",
            overview = "A dark knight.",
            posterUrl = "batman.jpg"
        ),
        com.product.trtcasecompose.features.home.domain.model.Movie(
            id = 2,
            title = "Superman",
            overview = "A man of steel.",
            posterUrl = "superman.jpg"
        )
    )
}
