package com.product.trtcasecompose.presentation.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.product.trtcasecompose.features.detail.presentation.view.DetailScreen
import com.product.trtcasecompose.features.favorite.presentation.view.FavoritesScreen
import com.product.trtcasecompose.features.home.presentation.view.HomeScreen
import com.product.trtcasecompose.presentation.app.components.AppConnectivityListener
import com.product.trtcasecompose.presentation.app.components.BottomBar
import com.product.trtcasecompose.presentation.app.components.MainBackground

@Composable
fun AppNavGraph(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != null && !currentRoute.startsWith(Screen.Detail.route)) {
                BottomBar(currentRoute = currentRoute) { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route)
                        launchSingleTop = true
                    }
                }
            }
        }

    ) { innerPadding ->
        MainBackground {
            AppConnectivityListener()
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(Screen.Home.route) {
                    HomeScreen(
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Detail.createRoute(movie.id))
                        }
                    )
                }

                composable(Screen.Favorites.route) {
                    FavoritesScreen(onMovieClick = { movie ->
                        navController.navigate(Screen.Detail.createRoute(movie.id))
                    })
                }

                composable(
                    route = Screen.Detail.fullRoute,
                    arguments = listOf(navArgument(Screen.Detail.ARG_MOVIE_ID) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getInt(Screen.Detail.ARG_MOVIE_ID)
                        ?: return@composable
                    DetailScreen(movieId = movieId, onBack = { navController.popBackStack() })
                }
            }
        }
    }
}
