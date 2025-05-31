package com.product.trtcasecompose.features.home.data.mapper

import com.product.trtcasecompose.core.config.Constants.BASE_IMAGE_URL
import com.product.trtcasecompose.features.home.data.local.entity.MovieEntity
import com.product.trtcasecompose.features.home.data.remote.dto.MovieDto
import com.product.trtcasecompose.features.home.domain.model.Movie

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl ="$BASE_IMAGE_URL${posterPath.orEmpty()}"
    )
}

fun MovieDto.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path.orEmpty()
    )
}

