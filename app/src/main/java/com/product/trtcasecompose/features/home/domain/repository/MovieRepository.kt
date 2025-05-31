package com.product.trtcasecompose.features.home.domain.repository

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.home.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(): UiState<List<Movie>>
}