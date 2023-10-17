package com.content.adapter.review.persistence

import com.content.adapter.review.entity.ReviewImageMetaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewImageEntityRepository : JpaRepository<ReviewImageMetaEntity, Long> {
    fun findReviewImageMetaEntitiesByIdIn(ids: List<Long>): List<ReviewImageMetaEntity>

    fun findReviewImageMetaEntitiesByReviewId(reviewId: Long): List<ReviewImageMetaEntity>
}