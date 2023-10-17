package com.content.adapter.review.entity

import com.content.adapter.share.BaseEntity
import com.content.domain.review.ReviewImageMeta
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "review_image_meta")
data class ReviewImageMetaEntity(
    @Column
    val sequence: Int,

    @Column
    val imageUrl: String,

    @Column
    val reviewId: Long,

    @Column
    val originalName: String,

    @Column
    val rename: String,

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
            rename = rename,
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
                rename = reviewImageMeta.rename,
                id = reviewImageMeta.reviewImageId
            )
        }
    }
}
