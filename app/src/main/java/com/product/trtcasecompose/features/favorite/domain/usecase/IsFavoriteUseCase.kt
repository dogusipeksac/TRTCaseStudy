package com.product.trtcasecompose.features.favorite.domain.usecase

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(movieId: Int): Flow<UiState<Boolean>> =
        repository.isFavorite(movieId)
}