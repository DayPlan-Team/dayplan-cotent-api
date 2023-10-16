package com.content.domain.review

data class Review(
    val reviewGroupId: Long,
    val courseId: Long,
    val reviewId: Long,
    val title: String,
)