package com.content.adapter.review

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageStorageData
import com.content.domain.review.port.ReviewImageStoragePort
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("default | local | test")
@Component
class ReviewImageStorageLocalAdapter : ReviewImageStoragePort {
    override fun saveReviewImage(reviewImageStorageDatas: List<ReviewImageStorageData>) {
        TODO("Not yet implemented")
    }

    override fun getReviewImage(imageUrl: String): ReviewImage? {
        TODO("Not yet implemented")
    }

    override fun getReviewImages(imageUrls: List<String>): List<ReviewImage> {
        TODO("Not yet implemented")
    }
}
