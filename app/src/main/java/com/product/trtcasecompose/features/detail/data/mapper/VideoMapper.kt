package com.product.trtcasecompose.features.detail.data.mapper

import com.product.trtcasecompose.features.home.data.remote.dto.VideoDto
import com.product.trtcasecompose.features.detail.domain.model.Video

fun VideoDto.toDomain(): Video {
    return Video(id, key, name, site, type)
}