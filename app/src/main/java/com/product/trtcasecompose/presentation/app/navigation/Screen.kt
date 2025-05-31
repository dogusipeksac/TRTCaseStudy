package com.product.trtcasecompose.presentation.app.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Detail : Screen("detail/{movieId}") {
        const val ARG_MOVIE_ID = "movieId"
        const val fullRoute = "detail/{$ARG_MOVIE_ID}"
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}
