package com.content.adapter.review

import com.content.adapter.review.persistence.ReviewEntityRepository
import com.content.domain.review.port.ReviewQueryPort
import com.content.domain.review.Review
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class ReviewQueryAdapter(
    private val reviewEntityRepository: ReviewEntityRepository,
) : ReviewQueryPort {
    override fun getReviewsByReviewGroupId(reviewGroupId: Long): List<Review> {
        return reviewEntityRepository.findReviewEntitiesByReviewGroupId(reviewGroupId = reviewGroupId)
            .map { it.toDomainModel() }
    }

    override fun getReviewById(id: Long): Review {
        return reviewEntityRepository.findById(id)
            .orElseThrow { throw ContentException(ContentExceptionCode.NOT_FOUND_REVIEW) }
            .toDomainModel()
    }

    override fun getReviewsByCourseIds(courseIds: List<Long>): List<Review> {
        return reviewEntityRepository.findReviewEntitiesByCourseIdIn(courseIds)
            .map { it.toDomainModel() }
    }
}