package com.content.domain.review

import org.springframework.stereotype.Component

@Component
interface ReviewWriteUseCase {
    fun getAllPossibleReviewToWrite(
        userId: Long,
        reviewGroupId: Long,
    ): List<CourseWithPossibleReview>

    fun writeReview(review: Review): Review
}
