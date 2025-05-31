package com.product.trtcasecompose.features.favorite.domain.repository

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.favorite.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<UiState<List<FavoriteMovie>>>
    fun isFavorite(movieId: Int): Flow<UiState<Boolean>>
    suspend fun addFavorite(movie: FavoriteMovie): UiState<Unit>
    suspend fun removeFavorite(movie: FavoriteMovie): UiState<Unit>
}