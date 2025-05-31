package com.product.trtcasecompose.features.favorite.data.local.mapper

import com.product.trtcasecompose.features.favorite.data.local.entity.FavoriteMovieEntity
import com.product.trtcasecompose.features.favorite.domain.model.FavoriteMovie
import com.product.trtcasecompose.features.home.domain.model.Movie

fun FavoriteMovieEntity.toDomain(): FavoriteMovie =
    FavoriteMovie(id, title, posterUrl, overview)

fun FavoriteMovie.toEntity(): FavoriteMovieEntity =
    FavoriteMovieEntity(id, title, posterUrl, overview)

fun FavoriteMovie.toMovie(): Movie =
    Movie(id = id, title = title, overview = overview, posterUrl = posterUrl)

fun Movie.toFavorite(): FavoriteMovie =
    FavoriteMovie(id = id, title = title, posterUrl = posterUrl, overview = overview)
