package com.content.adapter.review.persistence

import com.content.adapter.review.entity.ReviewImageMetaEntity
import com.content.adapter.share.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewImageEntityRepository : JpaRepository<ReviewImageMetaEntity, Long> {
    fun findReviewImageMetaEntitiesByIdIn(ids: List<Long>): List<ReviewImageMetaEntity>

    fun findReviewImageMetaEntitiesByIdInAndStatus(ids: List<Long>, status: Status): List<ReviewImageMetaEntity>

    fun findReviewImageMetaEntitiesByReviewId(reviewId: Long): List<ReviewImageMetaEntity>

    fun findReviewImageMetaEntitiesByReviewIdAndStatus(reviewId: Long, status: Status): List<ReviewImageMetaEntity>
}