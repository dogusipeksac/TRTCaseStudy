package com.product.trtcasecompose.features.detail.domain.repository

import com.product.trtcasecompose.core.common.state.UiState
import com.product.trtcasecompose.features.detail.domain.model.MovieDetail
import com.product.trtcasecompose.features.detail.domain.model.Video

interface MovieDetailRepository{
    suspend fun getMovieVideo(movieId: Int): UiState<List<Video>>
    suspend fun getMovieDetail(movieId: Int): UiState<MovieDetail>
}