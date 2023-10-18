package com.content.application.service

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.ReviewImageMetaCommandUseCase
import com.content.domain.review.ReviewImageStorage
import com.content.domain.review.isEqual
import com.content.domain.review.port.ReviewImageMetaCommandPort
import com.content.domain.review.port.ReviewImageMetaQueryPort
import com.content.domain.review.port.ReviewImageStoragePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewImageMetaCommandService(
    private val reviewImageMetaCommandPort: ReviewImageMetaCommandPort,
    private val reviewImageMetaQueryPort: ReviewImageMetaQueryPort,
    private val reviewImageStoragePort: ReviewImageStoragePort,
) : ReviewImageMetaCommandUseCase {
    override fun upsertReviewImageMeta(
        reviewImages: List<ReviewImage>,
        reviewImageMetas: List<ReviewImageMeta>,
    ) {

        if (reviewImageMetas.isNotEmpty()) {
            val findReviewImageMetas = reviewImageMetaQueryPort
                .getReviewImageMetasByReviewId(reviewImageMetas.first().reviewId)
                .sortedBy { it.sequence }

            upsertIfNotEqualBefore(reviewImageMetas, findReviewImageMetas, reviewImages)
        }
    }

    private fun upsertIfNotEqualBefore(
        reviewImageMetas: List<ReviewImageMeta>,
        findReviewImageMetas: List<ReviewImageMeta>,
        reviewImages: List<ReviewImage>
    ) {
        if (!reviewImageMetas.isEqual(findReviewImageMetas)) {
            deleteReviewImageMetaIfNotEmpty(findReviewImageMetas)

            reviewImageMetaCommandPort.upsertReviewImageMetas(reviewImageMetas)
            saveReviewImageAndGetImageUrl(reviewImages = reviewImages, reviewImageMetas = reviewImageMetas)
        }
    }

    private fun deleteReviewImageMetaIfNotEmpty(reviewImageMetas: List<ReviewImageMeta>) {
        if (reviewImageMetas.isNotEmpty()) {
            reviewImageMetaCommandPort.deleteReviewImageMetas(reviewImageMetas)
        }
    }

    private fun saveReviewImageAndGetImageUrl(
        reviewImages: List<ReviewImage>,
        reviewImageMetas: List<ReviewImageMeta>
    ) {
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