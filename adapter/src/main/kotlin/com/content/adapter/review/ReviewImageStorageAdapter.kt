package com.content.adapter.review

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageStorage
import com.content.domain.review.port.ReviewImageStoragePort
import org.springframework.stereotype.Component

@Component
class ReviewImageStorageAdapter : ReviewImageStoragePort {
    override fun saveReviewImageAndGetImageUrl(
        reviewImages: List<ReviewImage>,
        reviewImageStorages: List<ReviewImageStorage>
    ) {
        TODO("Not yet implemented")
    }

    override fun getReviewImage(imageUrl: String) {
        TODO("Not yet implemented")
    }

    override fun getReviewImages(imageUrls: List<String>) {
        TODO("Not yet implemented")
    }
}