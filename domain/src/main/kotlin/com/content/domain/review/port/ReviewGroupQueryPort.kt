package com.content.domain.review.port

import com.content.domain.review.ReviewGroup
import org.springframework.stereotype.Component

@Component
interface ReviewGroupQueryPort {

    fun getReviewGroupByCourseGroupId(courseGroupId: Long): ReviewGroup?

    fun getReviewGroupById(reviewGroupId: Long): ReviewGroup
}