package com.product.trtcasecompose.features.home.domain.usecase

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.home.domain.model.Movie
import com.product.trtcasecompose.features.home.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): UiState<List<Movie>> {
        return repository.getMovies()
    }
}