package com.product.trtcasecompose.features.detail.data.remote.api

import com.product.trtcasecompose.features.detail.data.remote.dto.MovieDetailDto
import com.product.trtcasecompose.features.home.data.remote.dto.VideoListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailApiService {
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): VideoListDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailDto
}