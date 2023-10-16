package com.content.application.port

import com.content.domain.course.CourseGroup
import com.content.domain.review.ReviewGroup
import org.springframework.stereotype.Component

@Component
interface ReviewGroupCommandPort {

    fun createReviewGroup(courseGroup: CourseGroup): ReviewGroup

    fun updateReviewGroup(reviewGroup: ReviewGroup): ReviewGroup
}