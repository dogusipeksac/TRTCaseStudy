package com.product.trtcasecompose.features.detail.data.mapper

import com.product.trtcasecompose.features.detail.data.remote.dto.MovieDetailDto
import com.product.trtcasecompose.features.detail.domain.model.MovieDetail

fun MovieDetailDto.toDomain(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterUrl = poster_path.orEmpty()
    )
}
