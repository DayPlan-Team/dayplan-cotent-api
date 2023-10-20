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
    name = "review",
    indexes = [
        Index(name = "idx_review_courseId", columnList = "courseId"),
        Index(name = "idx_review_reviewGroupId", columnList = "reviewGroupId"),
    ]
)
data class ReviewEntity(

    @Column
    val courseId: Long,

    @Column
    val reviewGroupId: Long,

    @Column
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