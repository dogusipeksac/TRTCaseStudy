package com.product.trtcasecompose.features.home.data.repository

import com.product.trtcasecompose.core.common.connectivity.util.NetworkChecker
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.core.config.Constants
import com.product.trtcasecompose.core.utils.ErrorMessages
import com.product.trtcasecompose.features.home.data.local.dao.MovieDao
import com.product.trtcasecompose.features.home.data.mapper.toDomain
import com.product.trtcasecompose.features.home.data.mapper.toEntity
import com.product.trtcasecompose.features.home.data.remote.api.MovieApiService
import com.product.trtcasecompose.features.home.domain.model.Movie
import com.product.trtcasecompose.features.home.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiService,
    private val movieDao: MovieDao,
    private val networkChecker: NetworkChecker
) : MovieRepository {

    override suspend fun getMovies(): UiState<List<Movie>> {
        return try {
            val localData = movieDao.getAllMovies().map { it.toDomain() }

            if (!networkChecker.isConnected()) {
                // internet yok → sadece local veriye bakarız
                return if (localData.isNotEmpty()) {
                    UiState.Success(localData)
                } else {
                    // Hem internet yok hem local boşsa özel mesaj
                    UiState.Error(ErrorMessages.ERROR_NO_INTERNET)
                }
            }

            // internet varsa API'den verileri al
            val response = api.getMovies(Constants.API_KEY)
            val entities = response.results.map { it.toEntity() }

            movieDao.clearAll()
            movieDao.insertAll(entities)

            return UiState.Success(entities.map { it.toDomain() })

        } catch (e: Exception) {
            val fallback = movieDao.getAllMovies().map { it.toDomain() }

            return if (fallback.isNotEmpty()) {
                UiState.Success(fallback)
            } else {
                // Hem API başarısız hem local boş
                UiState.Error(ErrorMessages.ERROR_DATA_LOAD_FAILED)
            }
        }
    }

}
