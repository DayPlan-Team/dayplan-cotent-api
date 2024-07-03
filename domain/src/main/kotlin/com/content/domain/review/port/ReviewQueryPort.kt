package com.content.domain.review.port

import com.content.domain.review.Review
import org.springframework.stereotype.Component

@Component
interface ReviewQueryPort {
    fun getReviewsByReviewGroupId(reviewGroupId: Long): List<Review>

    fun getReviewById(id: Long): Review

    fun getReviewsByCourseIds(courseIds: List<Long>): List<Review>

    fun getReviewByCourseId(courseId: Long): Review?

    fun getReviewsByCourseGroupId(courseGroupId: Long): List<Review>
}
