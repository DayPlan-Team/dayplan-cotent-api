package com.content.domain.review

import java.time.LocalDateTime

data class Review(
    val reviewId: Long,
    val userId: Long,
    val reviewGroupId: Long,
    val courseId: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)