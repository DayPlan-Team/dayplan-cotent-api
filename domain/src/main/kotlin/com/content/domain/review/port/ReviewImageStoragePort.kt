package com.content.domain.review.port

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageStorage
import org.springframework.stereotype.Component

@Component
interface ReviewImageStoragePort {

    fun saveReviewImageAndGetImageUrl(reviewImages: List<ReviewImage>, reviewImageStorages: List<ReviewImageStorage>)

    fun getReviewImage(imageUrl: String)

    fun getReviewImages(imageUrls: List<String>)
}