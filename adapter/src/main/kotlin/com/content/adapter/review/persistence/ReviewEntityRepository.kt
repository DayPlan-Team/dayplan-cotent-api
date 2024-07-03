package com.content.adapter.review.persistence

import com.content.adapter.review.entity.ReviewEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewEntityRepository : JpaRepository<ReviewEntity, Long> {
    fun findReviewEntitiesByReviewGroupId(reviewGroupId: Long): List<ReviewEntity>

    fun findReviewEntitiesByCourseIdIn(courseIds: List<Long>): List<ReviewEntity>

    fun findReviewEntityByCourseId(courseId: Long): ReviewEntity?
}
