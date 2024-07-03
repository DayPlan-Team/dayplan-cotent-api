package com.content.adapter.review

import com.content.adapter.review.entity.ReviewGroupEntity
import com.content.adapter.review.persistence.ReviewGroupEntityRepository
import com.content.domain.course.CourseGroup
import com.content.domain.review.ReviewGroup
import com.content.domain.review.port.ReviewGroupCommandPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ReviewGroupCommandAdapter(
    private val reviewGroupEntityRepository: ReviewGroupEntityRepository,
) : ReviewGroupCommandPort {
    override fun createReviewGroup(courseGroup: CourseGroup): ReviewGroup {
        return reviewGroupEntityRepository.save(ReviewGroupEntity.from(courseGroup))
            .toDomainModel()
    }

    override fun updateReviewGroup(reviewGroup: ReviewGroup): ReviewGroup {
        return reviewGroupEntityRepository.save(ReviewGroupEntity.from(reviewGroup)).toDomainModel()
    }
}
