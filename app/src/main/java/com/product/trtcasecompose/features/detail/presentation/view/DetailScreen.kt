package com.product.trtcasecompose.features.detail.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.product.trtcasecompose.R
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.detail.presentation.viewmodel.DetailViewModel
import com.product.trtcasecompose.features.favorite.domain.model.FavoriteMovie
import com.product.trtcasecompose.features.favorite.presentation.viewmodel.FavoritesViewModel

@Composable
fun DetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val movieState by viewModel.detailState.collectAsState()
    val videoKeyState by viewModel.videoKey.collectAsState()
    val isFavoriteState by favoritesViewModel.isFavorite.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
        viewModel.loadMovieTrailer(movieId)

        favoritesViewModel.checkIsFavorite(movieId)
    }

    Scaffold(topBar = {
        DetailTopBar(title = stringResource(R.string.detail_title),
            isFavoriteState = isFavoriteState,
            onBack = onBack,
            onToggleFavorite = {
                (movieState as? UiState.Success)?.data?.let { movie ->
                    val favMovie =
                        FavoriteMovie(movie.id, movie.title, movie.posterUrl, movie.overview)
                    if ((isFavoriteState as? UiState.Success)?.data == true) {
                        favoritesViewModel.removeFromFavorites(favMovie)
                    } else {
                        favoritesViewModel.addToFavorites(favMovie)
                    }
                }
            })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DetailContent(
                movieState = movieState, videoState = videoKeyState,
                onRetry = {
                    viewModel.loadMovieDetail(movieId)
                    viewModel.loadMovieTrailer(movieId)
                    favoritesViewModel.checkIsFavorite(movieId)
                })

        }
    }
}
