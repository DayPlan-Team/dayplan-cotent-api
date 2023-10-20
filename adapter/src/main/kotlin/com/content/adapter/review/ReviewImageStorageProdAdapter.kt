package com.content.adapter.review

import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMetaCommandUseCase
import com.content.domain.review.ReviewImageStorageData
import com.content.domain.review.port.ReviewImageStoragePort
import com.content.util.share.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Profile("dev | prod")
@Component
class ReviewImageStorageProdAdapter(
    private val s3Client: S3Client,
    private val reviewImageMetaCommandUseCase: ReviewImageMetaCommandUseCase,
) : ReviewImageStoragePort {

    @Value("\${s3.key}")
    private lateinit var s3KeyPrefix: String

    @Value("\${s3.bucket-name}")
    private lateinit var bucketName: String

    override fun saveReviewImage(reviewImageStorageDatas: List<ReviewImageStorageData>) {
        try {
            reviewImageStorageDatas.map {

                val fullS3Key = s3KeyPrefix + it.reviewImageMeta.imageUrl

                val putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullS3Key)
                    .build()

                val requestBody = RequestBody.fromBytes(it.reviewImage.image)
                s3Client.putObject(putObjectRequest, requestBody)
            }
        } catch (e: Exception) {
            reviewImageMetaCommandUseCase.deleteReviewImageMeta(
                reviewImageMetas = reviewImageStorageDatas.map { it.reviewImageMeta }
            )
        }
    }

    override fun getReviewImage(imageUrl: String): ReviewImage? {
        try {
            val fullS3Key = s3KeyPrefix + imageUrl

            val getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fullS3Key)
                .build()

            val response = s3Client.getObject(getObjectRequest)
            return ReviewImage(
                response.readAllBytes()
            )
        } catch (e: Exception) {
            log.error(e.message)
            return null
        }
    }

    override fun getReviewImages(imageUrls: List<String>): List<ReviewImage> {
        return imageUrls.map { imageUrl ->
            try {
                val fullS3Key = s3KeyPrefix + imageUrl
                val getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullS3Key)
                    .build()
                val response = s3Client.getObject(getObjectRequest)
                ReviewImage(response.readAllBytes())

            } catch (e: Exception) {
                return emptyList()
            }
        }
    }

    companion object : Logger()
}