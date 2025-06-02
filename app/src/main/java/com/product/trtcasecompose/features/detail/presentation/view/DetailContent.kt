package com.product.trtcasecompose.features.detail.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.core.config.Constants
import com.product.trtcasecompose.features.detail.domain.model.MovieDetail
import com.product.trtcasecompose.presentation.app.components.ErrorPopup

@Composable
fun DetailContent(
    movieState: UiState<MovieDetail>,
    videoState: UiState<String?>,
    onRetry: () -> Unit
) {
    when (movieState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val movie = movieState.data

            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    when (videoState) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is UiState.Success -> {
                            val videoKey = videoState.data
                            if (!videoKey.isNullOrEmpty()) {
                                YouTubePlayer(videoId = videoKey)
                            } else {
                                DetailPosterOrPlaceholder("${Constants.BASE_IMAGE_URL}${movie.posterUrl}", movie.title)
                            }
                        }

                        is UiState.Error -> {
                            DetailPosterOrPlaceholder("${Constants.BASE_IMAGE_URL}${movie.posterUrl}", movie.title)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (movie.title.isNotBlank()) movie.title else "--",
                    style = MaterialTheme.typography.h5
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (movie.overview.isNotBlank()) movie.overview else "--"
                )
            }
        }

        is UiState.Error -> {
            var showError by remember { mutableStateOf(true) }
            if (showError) {
                ErrorPopup(message = movieState.message, onDismiss = { showError = false },
                    onRetry = {
                        showError = false
                        onRetry()
                    })
            }
        }

    }
}
