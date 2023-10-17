package com.content.domain.review

data class ReviewImageMetaRequest(
    val sequence: Int,
    val reviewId: Long,
    val originalName: String,
    val reviewImageId: Long,
    val imageUrl: String,
)
