package com.content.domain.review

import com.content.domain.review.Review

interface ReviewReadUseCase {
    fun readReview(reviewId: Long): Review

    fun readReviews(reviewIds: List<Long>): List<Review>
}