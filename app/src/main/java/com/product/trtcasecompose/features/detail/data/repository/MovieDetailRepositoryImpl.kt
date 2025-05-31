package com.product.trtcasecompose.features.detail.data.repository

import com.product.trtcasecompose.core.common.connectivity.util.NetworkChecker
import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.core.config.Constants
import com.product.trtcasecompose.core.utils.ErrorHandler
import com.product.trtcasecompose.core.utils.ErrorMessages
import com.product.trtcasecompose.features.detail.data.mapper.toDomain
import com.product.trtcasecompose.features.detail.data.remote.api.MovieDetailApiService
import com.product.trtcasecompose.features.detail.domain.model.MovieDetail
import com.product.trtcasecompose.features.detail.domain.model.Video
import com.product.trtcasecompose.features.detail.domain.repository.MovieDetailRepository
import com.product.trtcasecompose.features.home.data.local.dao.MovieDao
import com.product.trtcasecompose.features.home.data.mapper.toDomain
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val api: MovieDetailApiService,
    private val movieDao: MovieDao,
    private val networkChecker: NetworkChecker
) : MovieDetailRepository {

    override suspend fun getMovieDetail(movieId: Int): UiState<MovieDetail> {
        return if (!networkChecker.isConnected()) {
            getMovieDetailFromCache(movieId)
        } else {
            getMovieDetailFromApi(movieId)
        }
    }

    private suspend fun getMovieDetailFromCache(movieId: Int): UiState<MovieDetail> {
        val cached = movieDao.getMovieById(movieId)?.toDomain()
        return cached?.let {
            UiState.Success(
                MovieDetail(
                    id = it.id,
                    title = it.title,
                    overview = it.overview,
                    posterUrl = it.posterUrl
                )
            )
        } ?: UiState.Error(ErrorMessages.ERROR_NO_LOCAL_DATA)
    }

    private suspend fun getMovieDetailFromApi(movieId: Int): UiState<MovieDetail> {
        return try {
            val response = api.getMovieDetail(movieId, Constants.API_KEY)
            UiState.Success(response.toDomain())
        } catch (e: Exception) {
            UiState.Error(ErrorHandler.handleException(e))
        }
    }


    override suspend fun getMovieVideo(movieId: Int): UiState<List<Video>> {
        return try {
            val videos = api.getMovieVideo(movieId, Constants.API_KEY).results.map { it.toDomain() }
            UiState.Success(videos)
        } catch (e: Exception) {
            UiState.Error(ErrorHandler.handleException(e))
        }
    }
}
