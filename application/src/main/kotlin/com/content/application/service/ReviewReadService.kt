package com.content.application.service

import com.content.domain.review.port.ReviewQueryPort
import com.content.domain.review.Review
import com.content.domain.review.ReviewReadUseCase
import org.springframework.stereotype.Service

@Service
class ReviewReadService(
    private val reviewQueryPort: ReviewQueryPort,
) : ReviewReadUseCase {
    override fun readReview(reviewId: Long): Review {
        return reviewQueryPort.getReviewById(reviewId)
    }

    override fun readReviews(reviewIds: List<Long>): List<Review> {
        return reviewQueryPort.getReviewsByCourseIds(reviewIds)
    }
}