package com.content.application.service

import com.content.domain.review.ReviewImageMetaWriteUseCase
import com.content.domain.review.ReviewWriteUseCase
import com.content.domain.review.port.ReviewImageMetaQueryPort
import com.content.domain.review.port.ReviewQueryPort
import org.springframework.stereotype.Component

@Component
class ReviewAndImageWriteService(
    private val reviewWriteUseCase: ReviewWriteUseCase,
    private val reviewImageMetaWriteUseCase: ReviewImageMetaWriteUseCase,
    private val reviewQueryPort: ReviewQueryPort,
    private val reviewImageMetaQueryPort: ReviewImageMetaQueryPort,
) {



}