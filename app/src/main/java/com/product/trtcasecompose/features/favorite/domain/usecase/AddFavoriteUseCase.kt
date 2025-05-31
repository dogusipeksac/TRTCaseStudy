package com.product.trtcasecompose.features.favorite.domain.usecase

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.favorite.domain.model.FavoriteMovie
import com.product.trtcasecompose.features.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(movie: FavoriteMovie): UiState<Unit> =
        repository.addFavorite(movie)
}