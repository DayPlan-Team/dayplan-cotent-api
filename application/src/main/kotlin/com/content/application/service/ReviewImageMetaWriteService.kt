package com.content.application.service

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.ReviewImageMetaRequest
import com.content.domain.review.ReviewImageMetaWriteUseCase
import com.content.domain.review.ReviewImageStorage
import com.content.domain.review.isEqual
import com.content.domain.review.port.ReviewImageMetaCommandPort
import com.content.domain.review.port.ReviewImageMetaQueryPort
import com.content.domain.review.port.ReviewImageStoragePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewImageMetaWriteService(
    private val reviewImageMetaCommandPort: ReviewImageMetaCommandPort,
    private val reviewImageMetaQueryPort: ReviewImageMetaQueryPort,
    private val reviewImageStoragePort: ReviewImageStoragePort,
) : ReviewImageMetaWriteUseCase {
    override fun writeReviewImageMeta(
        reviewImages: List<ReviewImage>,
        reviewImageMetaRequests: List<ReviewImageMetaRequest>,
    ) {

        val reviewImageMetas = ReviewImageMeta.from(reviewImages, reviewImageMetaRequests)
            .sortedBy { it.sequence }

        val findReviewImageMetas =
            reviewImageMetaQueryPort.getReviewImageMetasByReviewId(reviewImageMetas.first().reviewId)
                .sortedBy { it.sequence }

        if (!reviewImageMetas.isEqual(findReviewImageMetas)) {
            if (findReviewImageMetas.isNotEmpty()) {
                reviewImageMetaCommandPort.deleteReviewImageMetas(findReviewImageMetas)
            }

            reviewImageMetaCommandPort.upsertReviewImageMetas(reviewImageMetas)

            reviewImageStoragePort.saveReviewImageAndGetImageUrl(
                reviewImages = reviewImages,
                reviewImageStorages = reviewImageMetas.map {
                    ReviewImageStorage(
                        name = it.rename,
                        imageUrl = it.imageUrl,
                    )
                }
            )
        }
    }
}