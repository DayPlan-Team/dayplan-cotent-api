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
            processReviewImageByReviewId(
                reviewImages = reviewImages,
                reviewImageMetas = reviewImageMetas,
            )
        }
    }

    private fun processReviewImageByReviewId(
        reviewImages: List<ReviewImage>,
        reviewImageMetas: List<ReviewImageMeta>,
    ) {
        when (reviewImageMetas.first().reviewId) {
            0L -> {
                saveReviewImageAndMetas(
                    reviewImages = reviewImages,
                    reviewImageMetas = reviewImageMetas,
                )
            }

            else -> {
                val findReviewImageMetas = reviewImageMetaQueryPort
                    .getReviewImageMetasByReviewId(reviewImageMetas.first().reviewId)
                    .sortedBy { it.sequence }

                upsertIfNotEqualBefore(reviewImageMetas, findReviewImageMetas, reviewImages)
            }
        }
    }

    private fun upsertIfNotEqualBefore(
        reviewImageMetas: List<ReviewImageMeta>,
        findReviewImageMetas: List<ReviewImageMeta>,
        reviewImages: List<ReviewImage>
    ) {
        if (!reviewImageMetas.isEqual(findReviewImageMetas)) {
            deleteReviewImageMetaIfNotEmpty(findReviewImageMetas)

            saveReviewImageAndMetas(
                reviewImages = reviewImages,
                reviewImageMetas = reviewImageMetas,
            )
        }
    }

    private fun deleteReviewImageMetaIfNotEmpty(reviewImageMetas: List<ReviewImageMeta>) {
        if (reviewImageMetas.isNotEmpty()) {
            reviewImageMetaCommandPort.deleteReviewImageMetas(reviewImageMetas)
        }
    }

    private fun saveReviewImageAndMetas(reviewImages: List<ReviewImage>, reviewImageMetas: List<ReviewImageMeta>) {
        reviewImageMetaCommandPort.upsertReviewImageMetas(reviewImageMetas)
        saveReviewImage(reviewImages = reviewImages, reviewImageMetas = reviewImageMetas)
    }

    private fun saveReviewImage(
        reviewImages: List<ReviewImage>,
        reviewImageMetas: List<ReviewImageMeta>
    ) {
        reviewImageStoragePort.saveReviewImageAndGetImageUrl(
            reviewImages = reviewImages,
            reviewImageStorages = reviewImageMetas.map {
                ReviewImageStorage(
                    name = it.imageName,
                    imageUrl = it.imageUrl,
                )
            }
        )
    }
}