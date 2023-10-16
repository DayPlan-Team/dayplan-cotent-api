package com.content.adapter.review

import com.content.adapter.review.persistence.ReviewGroupEntityRepository
import com.content.application.port.ReviewGroupQueryPort
import com.content.domain.review.ReviewGroup
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class ReviewGroupQueryAdapter(
    private val reviewGroupEntityRepository: ReviewGroupEntityRepository,
) : ReviewGroupQueryPort {
    override fun getReviewGroupByCourseGroupId(courseGroupId: Long): ReviewGroup? {
        return reviewGroupEntityRepository.findReviewGroupEntityByCourseGroupId(courseGroupId = courseGroupId)
            ?.toDomainModel()
    }

    override fun getReviewGroupById(reviewGroupId: Long): ReviewGroup {
        return reviewGroupEntityRepository.findById(reviewGroupId)
            .orElseThrow { throw ContentException(ContentExceptionCode.NOT_FOUND_REVIEW_GROUP) }
            .toDomainModel()
    }
}