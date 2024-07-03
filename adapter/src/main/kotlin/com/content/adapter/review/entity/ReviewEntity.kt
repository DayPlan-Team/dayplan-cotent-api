package com.content.adapter.review.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.review.Review
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "reviews",
    indexes = [
        Index(name = "idx__reviews_course_id", columnList = "course_id"),
        Index(name = "idx__reviews_review_group_id", columnList = "review_group_id"),
    ],
)
data class ReviewEntity(
    @Column(name = "course_id", columnDefinition = "bigint", nullable = false)
    val courseId: Long,
    @Column(name = "review_group_id", columnDefinition = "bigint", nullable = false)
    val reviewGroupId: Long,
    @Column(name = "content", columnDefinition = "text", nullable = false)
    val content: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    fun toDomainModel(): Review {
        return Review(
            reviewGroupId = reviewGroupId,
            courseId = courseId,
            reviewId = id,
            content = content,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
    }

    companion object {
        fun from(review: Review): ReviewEntity {
            return ReviewEntity(
                reviewGroupId = review.reviewId,
                courseId = review.courseId,
                content = review.content,
                id = review.reviewId,
            )
        }
    }
}
