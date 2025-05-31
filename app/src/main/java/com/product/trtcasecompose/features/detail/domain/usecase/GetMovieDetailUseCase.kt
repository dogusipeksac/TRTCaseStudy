package com.product.trtcasecompose.features.detail.domain.usecase

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.detail.domain.model.MovieDetail
import com.product.trtcasecompose.features.detail.domain.repository.MovieDetailRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieDetailRepository
) {
    suspend operator fun invoke(movieId: Int): UiState<MovieDetail> {
        return repository.getMovieDetail(movieId)
    }
}