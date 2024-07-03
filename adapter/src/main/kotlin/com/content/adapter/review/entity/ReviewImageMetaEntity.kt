package com.content.adapter.review.entity

import com.content.adapter.share.BaseEntity
import com.content.adapter.share.Status
import com.content.domain.review.ReviewImageMeta
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "review_image_mets",
    indexes = [
        Index(name = "idx__review_image_metas_review_id", columnList = "review_id"),
    ],
)
data class ReviewImageMetaEntity(
    @Column(name = "sequence", columnDefinition = "int", nullable = false)
    val sequence: Int,
    @Column(name = "image_url", columnDefinition = "text", nullable = false)
    val imageUrl: String,
    @Column(name = "review_id", columnDefinition = "bigint", nullable = false)
    val reviewId: Long,
    @Column(name = "original_name", columnDefinition = "varchar(255)", nullable = false)
    val originalName: String,
    @Column(name = "image_name", columnDefinition = "varchar(255)", nullable = false)
    val imageName: String,
    @Column(name = "review_image_hash_code", columnDefinition = "int", nullable = false)
    val reviewImageHashCode: Int,
    @Column(name = "status", columnDefinition = "varchar(32)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    val status: Status,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity() {
    fun toDomainModel(): ReviewImageMeta {
        return ReviewImageMeta(
            sequence = sequence,
            imageUrl = imageUrl,
            reviewId = reviewId,
            originalName = originalName,
            imageName = imageName,
            reviewImageHashCode = reviewImageHashCode,
            reviewImageId = id,
        )
    }

    companion object {
        fun from(reviewImageMeta: ReviewImageMeta): ReviewImageMetaEntity {
            return ReviewImageMetaEntity(
                sequence = reviewImageMeta.sequence,
                imageUrl = reviewImageMeta.imageUrl,
                reviewId = reviewImageMeta.reviewId,
                originalName = reviewImageMeta.originalName,
                imageName = reviewImageMeta.imageName,
                reviewImageHashCode = reviewImageMeta.reviewImageHashCode,
                status = Status.NORMAL,
                id = reviewImageMeta.reviewImageId,
            )
        }

        fun fromToDelete(reviewImageMeta: ReviewImageMeta): ReviewImageMetaEntity {
            return ReviewImageMetaEntity(
                sequence = reviewImageMeta.sequence,
                imageUrl = reviewImageMeta.imageUrl,
                reviewId = reviewImageMeta.reviewId,
                originalName = reviewImageMeta.originalName,
                imageName = reviewImageMeta.imageName,
                reviewImageHashCode = reviewImageMeta.reviewImageHashCode,
                status = Status.NORMAL,
                id = reviewImageMeta.reviewImageId,
            )
        }
    }
}
