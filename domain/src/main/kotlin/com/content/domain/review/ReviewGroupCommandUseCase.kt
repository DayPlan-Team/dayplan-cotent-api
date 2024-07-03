package com.content.domain.review

import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface ReviewGroupCommandUseCase {
    fun createReviewGroupOrGet(courseGroup: CourseGroup): ReviewGroup

    fun updateReviewGroup(
        userId: Long,
        reviewGroupId: Long,
        reviewGroupUpdateRequest: ReviewGroupUpdateRequest,
    ): ReviewGroup
}
