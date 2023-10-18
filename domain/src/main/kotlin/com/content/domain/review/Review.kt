package com.content.domain.review

import java.time.LocalDateTime

data class Review(
    val reviewId: Long,
    val userId: Long,
    val reviewGroupId: Long,
    val courseId: Long,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val modifiedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun from(
            reviewId: Long,
            userId: Long,
            reviewGroupId: Long,
            courseId: Long,
            content: String
        ): Review {
            return Review(
                reviewId = reviewId,
                userId = userId,
                reviewGroupId = reviewGroupId,
                courseId = courseId,
                content = content,
            )
        }
    }
}