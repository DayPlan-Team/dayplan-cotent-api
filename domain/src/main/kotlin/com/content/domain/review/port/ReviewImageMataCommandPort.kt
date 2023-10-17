package com.content.domain.review.port

import com.content.domain.review.ReviewImageMeta
import org.springframework.stereotype.Component

@Component
interface ReviewImageMataCommandPort {
    fun upsertReviewImageMeta(reviewImageMeta: ReviewImageMeta): ReviewImageMeta

    fun upsertReviewImageMetas(reviewImageMetas: List<ReviewImageMeta>): List<ReviewImageMeta>
}