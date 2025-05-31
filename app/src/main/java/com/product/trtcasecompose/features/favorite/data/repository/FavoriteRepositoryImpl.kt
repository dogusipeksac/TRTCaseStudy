package com.product.trtcasecompose.features.favorite.data.repository

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.favorite.data.local.dao.FavoriteMovieDao
import com.product.trtcasecompose.features.favorite.data.local.entity.FavoriteMovieEntity
import com.product.trtcasecompose.features.favorite.data.local.mapper.toDomain
import com.product.trtcasecompose.features.favorite.data.local.mapper.toEntity
import com.product.trtcasecompose.features.favorite.domain.model.FavoriteMovie
import com.product.trtcasecompose.features.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

import com.product.trtcasecompose.core.utils.ErrorHandler

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<UiState<List<FavoriteMovie>>> {
        return dao.getAllFavorites()
            .map<List<FavoriteMovieEntity>, UiState<List<FavoriteMovie>>> { movieList ->
                UiState.Success(movieList.map { it.toDomain() })
            }
            .catch { e ->
                emit(UiState.Error(ErrorHandler.handleException(e)))
            }
    }
    override fun isFavorite(movieId: Int): Flow<UiState<Boolean>> {
        return dao.isFavorite(movieId)
            .map<Boolean, UiState<Boolean>> { isFav ->
                UiState.Success(isFav)
            }
            .catch { e ->
                emit(UiState.Error(ErrorHandler.handleException(e)))
            }
    }

    override suspend fun addFavorite(movie: FavoriteMovie): UiState<Unit> {
        return try {
            dao.insert(movie.toEntity())
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error(ErrorHandler.handleException(e))
        }
    }

    override suspend fun removeFavorite(movie: FavoriteMovie): UiState<Unit> {
        return try {
            dao.delete(movie.toEntity())
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error(ErrorHandler.handleException(e))
        }
    }
}
