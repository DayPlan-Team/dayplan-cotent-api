package com.content.domain.review

import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import java.util.UUID

data class ReviewImageMeta(
    val sequence: Int,
    val reviewId: Long,
    val originalName: String,
    val reviewImageHashCode: Int,
    val reviewImageId: Long,
    val rename: String = "${RENAME_DEFAULT}_${UUID.randomUUID()}_${originalName.parseExtension()}",
    val imageUrl: String = "/$reviewId/$sequence/$rename",
) {
    companion object {
        const val RENAME_DEFAULT = "image"

        fun from(reviewImage: ReviewImage, reviewImageMetaRequest: ReviewImageMetaRequest): ReviewImageMeta {
            return ReviewImageMeta(
                sequence = reviewImageMetaRequest.sequence,
                reviewId = reviewImageMetaRequest.reviewId,
                originalName = reviewImageMetaRequest.originalName,
                reviewImageHashCode = reviewImage.hashCode(),
                reviewImageId = reviewImageMetaRequest.reviewImageId,
            )
        }

        fun from(
            reviewImages: List<ReviewImage>,
            reviewImageMetaRequests: List<ReviewImageMetaRequest>
        ): List<ReviewImageMeta> {

            require(reviewImages.size == reviewImageMetaRequests.size) {
                throw ContentException(ContentExceptionCode.BAD_REQUEST_REVIEW_IMAGE)
            }

            require(reviewImageMetaRequests.isNotEmpty() && reviewImageMetaRequests.all { it.reviewId == reviewImageMetaRequests.first().reviewId }) {
                throw ContentException(ContentExceptionCode.BAD_REQUEST_REVIEW_IMAGE)
            }

            return reviewImages.zip(reviewImageMetaRequests).map { (reviewImage, request) ->
                ReviewImageMeta(
                    sequence = request.sequence,
                    reviewId = request.reviewId,
                    originalName = request.originalName,
                    reviewImageHashCode = reviewImage.hashCode(),
                    reviewImageId = request.reviewImageId,
                )
            }
        }
    }
}