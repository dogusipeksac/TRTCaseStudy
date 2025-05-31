package com.product.trtcasecompose.features.home.data.remote.dto

data class VideoListDto(
    val results: List<VideoDto>
)

data class VideoDto(
    val id: String,
    val key: String, // For YouTube: watch?v=<key>
    val name: String,
    val site: String,
    val type: String
)
