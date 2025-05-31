package com.product.trtcasecompose.features.favorite.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.product.trtcasecompose.R
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.favorite.data.local.mapper.toMovie
import com.product.trtcasecompose.features.favorite.presentation.viewmodel.FavoritesViewModel
import com.product.trtcasecompose.features.home.domain.model.Movie
import com.product.trtcasecompose.presentation.app.components.ErrorPopup
import com.product.trtcasecompose.presentation.app.components.MovieItem

@Composable
fun FavoritesScreen(
    onMovieClick: (Movie) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.favoriteMovies.collectAsState()
    when (state) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val favorites = (state as UiState.Success).data
            if (favorites.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.no_favorites))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favorites) { fav ->
                        MovieItem(
                            movie = fav.toMovie(),
                            isFavorite = true,
                            onClick = { onMovieClick(fav.toMovie()) },
                            onFavoriteClick = {
                                viewModel.removeFromFavorites(fav)
                            }
                        )
                    }
                }
            }
        }

        is UiState.Error -> {
            val message = (state as UiState.Error).message
            var showError by remember { mutableStateOf(true) }
            ErrorPopup(
                message = message,
                onDismiss = { showError = false }
            )
        }
    }

}
