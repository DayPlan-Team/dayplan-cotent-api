package com.content.application.service

import com.content.domain.review.ReviewImageMetaCommandUseCase
import com.content.domain.review.ReviewWriteUseCase
import com.content.domain.review.port.ReviewImageMetaQueryPort
import com.content.domain.review.port.ReviewQueryPort
import org.springframework.stereotype.Component

@Component
class ReviewAndImageService(
    private val reviewQueryPort: ReviewQueryPort,
    private val reviewImageMetaQueryPort: ReviewImageMetaQueryPort,
    private val reviewWriteUseCase: ReviewWriteUseCase,
    private val reviewImageMetaCommandUseCase: ReviewImageMetaCommandUseCase,
) {

    fun writeReviewAndImage() {

    }


}