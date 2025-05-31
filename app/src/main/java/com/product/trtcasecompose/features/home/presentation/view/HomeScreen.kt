package com.product.trtcasecompose.features.home.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.product.trtcasecompose.core.common.connectivity.ConnectivityObserver
import com.product.trtcasecompose.core.common.connectivity.viewmodel.ConnectivityViewModel
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.favorite.data.local.mapper.toFavorite
import com.product.trtcasecompose.features.favorite.presentation.viewmodel.FavoritesViewModel
import com.product.trtcasecompose.features.home.domain.model.Movie
import com.product.trtcasecompose.features.home.presentation.viewmodel.HomeViewModel
import com.product.trtcasecompose.presentation.app.components.ErrorPopup
import com.product.trtcasecompose.presentation.app.components.MovieItem

@Composable
fun HomeScreen(
    onMovieClick: (Movie) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    connectivityViewModel : ConnectivityViewModel = hiltViewModel ()
) {
    val state by viewModel.moviesState.collectAsState()
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()

    when (state) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            val errorMessage = (state as UiState.Error).message
            val connectivityStatus by connectivityViewModel.connectivityStatus.collectAsState()
            var showError by remember { mutableStateOf(true) }

            if (showError) {
                ErrorPopup(
                    message = errorMessage,
                    onDismiss = { showError = false },
                    onRetry = {
                        if (connectivityStatus == ConnectivityObserver.Status.Available) {
                            showError = false
                            viewModel.fetchMovies()
                        } else {
                            showError = true // tekrar göster
                        }
                    }
                )
            }
        }


        is UiState.Success -> {
            val movies = (state as UiState.Success).data
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movies, key = { it.id }) { movie ->
                    val isFavorite = movie.id in favoriteIds
                    MovieItem(
                        movie = movie,
                        isFavorite = isFavorite,
                        onClick = { onMovieClick(movie) },
                        onFavoriteClick = {
                            if (isFavorite) {
                                favoritesViewModel.removeFromFavorites(movie.toFavorite())
                            } else {
                                favoritesViewModel.addToFavorites(movie.toFavorite())
                            }
                        }
                    )
                }
            }
        }
    }
}
