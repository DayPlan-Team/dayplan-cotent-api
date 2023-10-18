package com.content.domain.review.port

import com.content.domain.review.ReviewImageMeta
import org.springframework.stereotype.Component

@Component
interface ReviewImageMetaCommandPort {
    fun upsertReviewImageMeta(reviewImageMeta: ReviewImageMeta): ReviewImageMeta

    fun upsertReviewImageMetas(reviewImageMetas: List<ReviewImageMeta>)

    fun deleteReviewImageMeta(reviewImageMeta: ReviewImageMeta)

    fun deleteReviewImageMetas(reviewImageMetas: List<ReviewImageMeta>)
}