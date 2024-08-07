package com.content.adapter.review

import com.content.adapter.review.persistence.ReviewEntityRepository
import com.content.adapter.review.persistence.ReviewGroupEntityRepository
import com.content.domain.review.Review
import com.content.domain.review.port.ReviewQueryPort
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class ReviewQueryAdapter(
    private val reviewEntityRepository: ReviewEntityRepository,
    private val reviewGroupEntityRepository: ReviewGroupEntityRepository,
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

    override fun getReviewByCourseId(courseId: Long): Review? {
        return reviewEntityRepository.findReviewEntityByCourseId(courseId)?.toDomainModel()
    }

    override fun getReviewsByCourseGroupId(courseGroupId: Long): List<Review> {
        return reviewGroupEntityRepository
            .findReviewGroupEntityByCourseGroupId(courseGroupId)?.let { reviewGroupEntity ->
                reviewEntityRepository
                    .findReviewEntitiesByReviewGroupId(reviewGroupEntity.id)
                    .map { reviewEntity ->
                        reviewEntity.toDomainModel()
                    }
            } ?: emptyList()
    }
}
