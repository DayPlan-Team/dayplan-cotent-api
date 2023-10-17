package com.content.domain.review

import org.springframework.stereotype.Component

@Component
interface ReviewImageMetaWriteUseCase {

    fun writeReviewImageMeta(reviewImages: List<ReviewImage>, reviewImageMetaRequests: List<ReviewImageMetaRequest>)
}