package com.product.trtcasecompose.features.home.data.remote.api


import com.product.trtcasecompose.features.home.data.remote.dto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): MovieListDto

}