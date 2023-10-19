package com.content.domain.review

interface ReviewReadUseCase {
    fun readReview(reviewId: Long): Review

    fun readReviews(reviewIds: List<Long>): List<Review>
}