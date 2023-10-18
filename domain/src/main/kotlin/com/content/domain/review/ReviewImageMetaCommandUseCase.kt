package com.content.domain.review

import org.springframework.stereotype.Component

@Component
interface ReviewImageMetaCommandUseCase {

    fun upsertReviewImageMeta(reviewImages: List<ReviewImage>, reviewImageMetas: List<ReviewImageMeta>)
}