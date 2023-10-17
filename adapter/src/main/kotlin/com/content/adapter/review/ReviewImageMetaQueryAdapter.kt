package com.content.adapter.review

import com.content.adapter.review.persistence.ReviewImageEntityRepository
import com.content.domain.review.port.ReviewImageMetaQueryPort
import com.content.domain.review.ReviewImageMeta
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class ReviewImageMetaQueryAdapter(
    private val reviewImageMetaEntityRepository: ReviewImageEntityRepository,
) : ReviewImageMetaQueryPort {
    override fun getReviewImageMetaById(reviewImageId: Long): ReviewImageMeta {
        return reviewImageMetaEntityRepository.findById(reviewImageId)
            .orElseThrow { throw ContentException(ContentExceptionCode.NOT_FOUND_REVIEW_IMAGE) }
            .toDomainModel()
    }

    override fun getReviewImageMetasByIds(reviewImageIds: List<Long>): List<ReviewImageMeta> {
        return reviewImageMetaEntityRepository.findReviewImageMetaEntitiesByIdIn(reviewImageIds)
            .map { it.toDomainModel() }
    }

    override fun getReviewImageMetasByReviewId(reviewId: Long): List<ReviewImageMeta> {
        return reviewImageMetaEntityRepository.findReviewImageMetaEntitiesByReviewId(reviewId = reviewId)
            .map { it.toDomainModel() }
    }
}