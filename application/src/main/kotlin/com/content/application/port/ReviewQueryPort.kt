package com.content.application.port

import com.content.domain.review.Review
import org.springframework.stereotype.Component

@Component
interface ReviewQueryPort {

    fun getReviewsByReviewGroupId(reviewGroupId: Long): List<Review>

    fun getReviewById(id: Long): Review

    fun getReviewsByCourseIds(courseIds: List<Long>): List<Review>
}