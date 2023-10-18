package com.content.domain.review

import java.time.LocalDateTime

data class Review(
    val reviewId: Long,
    val reviewGroupId: Long,
    val courseId: Long,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val modifiedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun from(
            reviewId: Long,
            reviewGroupId: Long,
            courseId: Long,
            content: String
        ): Review {
            return Review(
                reviewId = reviewId,
                reviewGroupId = reviewGroupId,
                courseId = courseId,
                content = content,
            )
        }
    }
}