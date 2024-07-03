package com.content.adapter.review.persistence

import com.content.adapter.review.entity.ReviewGroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewGroupEntityRepository : JpaRepository<ReviewGroupEntity, Long> {
    fun findReviewGroupEntityByCourseGroupId(courseGroupId: Long): ReviewGroupEntity?
}
