package com.content.domain.review.port

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageStorageData
import org.springframework.stereotype.Component

@Component
interface ReviewImageStoragePort {
    fun saveReviewImage(reviewImageStorageDatas: List<ReviewImageStorageData>)

    fun getReviewImage(imageUrl: String): ReviewImage?

    fun getReviewImages(imageUrls: List<String>): List<ReviewImage>
}
