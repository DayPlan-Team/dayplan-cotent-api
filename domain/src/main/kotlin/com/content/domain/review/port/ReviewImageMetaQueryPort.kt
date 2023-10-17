package com.content.domain.review.port

import com.content.domain.review.ReviewImageMeta
import org.springframework.stereotype.Component

@Component
interface ReviewImageMetaQueryPort {

    fun getReviewImageMetaById(reviewImageId: Long): ReviewImageMeta

    fun getReviewImageMetasByIds(reviewImageIds: List<Long>): List<ReviewImageMeta>

    fun getReviewImageMetasByReviewId(reviewId: Long): List<ReviewImageMeta>

}