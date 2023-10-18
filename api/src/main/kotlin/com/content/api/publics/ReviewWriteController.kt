package com.content.api.publics

import com.content.application.service.ReviewAndReviewImageService
import com.content.application.service.UserVerifyService
import com.content.domain.review.CourseWithPossibleReview
import com.content.domain.review.Review
import com.content.domain.review.ReviewImage
import com.content.domain.review.ReviewImageMeta
import com.content.domain.review.ReviewWriteUseCase
import com.content.domain.review.port.ReviewImageStoragePort
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/content/reviewgroups/{reviewGroupId}/reviews")
class ReviewWriteController(
    private val userVerifyService: UserVerifyService,
    private val reviewWriteUseCase: ReviewWriteUseCase,
    private val reviewAndReviewImageService: ReviewAndReviewImageService,
    private val reviewImageStoragePort: ReviewImageStoragePort,
) {

    @GetMapping
    fun getAllPossibleReviewsToWrite(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("reviewGroupId") reviewGroupId: Long,
    ): ResponseEntity<CourseWithPossibleApiReviews> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)

        val courseWithPossibleReviews =
            reviewWriteUseCase.getAllPossibleReviewToWrite(userId = user.userId, reviewGroupId = reviewGroupId)

        return ResponseEntity.ok(
            CourseWithPossibleApiReviews(
                courseWithPossibleReviews.map {
                    CourseWithPossibleApiReview.of(it)
                },
            )
        )
    }

    @PostMapping
    fun writeReview(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("reviewGroupId") reviewGroupId: Long,
        @ModelAttribute reviewWriteApiRequest: ReviewWriteApiRequest,
    ): ResponseEntity<Unit> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)

        val review = createReview(reviewWriteApiRequest, reviewGroupId)

        val reviewImages = createReviewImages(reviewWriteApiRequest.reviewImages)

        val reviewImageMetas = createReviewImageMeta(
            reviewImages = reviewImages,
            reviewWriteApiRequest = reviewWriteApiRequest,
        )

        val reviewImageStorageDatas = reviewAndReviewImageService.writeReviewAndGetReviewImageStorageData(
            user = user,
            review = review,
            reviewImages = reviewImages,
            reviewImageMetas = reviewImageMetas,
        )

        if (reviewImageStorageDatas.isNotEmpty()) {
            reviewImageStoragePort.saveReviewImage(reviewImageStorageDatas)
        }

        return ResponseEntity.ok().build()
    }

    private fun createReviewImages(reviewImages: List<MultipartFile>): List<ReviewImage> {
        return reviewImages.map {
            ReviewImage(
                image = it.bytes,
            )
        }
    }

    private fun createReviewImageMeta(
        reviewImages: List<ReviewImage>,
        reviewWriteApiRequest: ReviewWriteApiRequest
    ): List<ReviewImageMeta> {
        return reviewImages.mapIndexed { index, reviewImage ->
            ReviewImageMeta(
                sequence = index + 1,
                reviewId = reviewWriteApiRequest.reviewId,
                originalName = reviewWriteApiRequest.originalNames[index],
                reviewImageHashCode = reviewImage.hashCode(),
                reviewImageId = reviewWriteApiRequest.reviewImageIds[index],
            )
        }
    }

    private fun createReview(reviewWriteApiRequest: ReviewWriteApiRequest, reviewGroupId: Long): Review {
        return Review.from(
            reviewId = reviewWriteApiRequest.reviewId,
            reviewGroupId = reviewGroupId,
            courseId = reviewWriteApiRequest.courseId,
            content = reviewWriteApiRequest.content,
        )
    }

    data class CourseWithPossibleApiReviews(
        @JsonProperty("courses") val courses: List<CourseWithPossibleApiReview>,
    )

    data class CourseWithPossibleApiReview(
        @JsonProperty("courseId") val courseId: Long,
        @JsonProperty("reviewId") val reviewId: Long,
        @JsonProperty("createdAt") val createdAt: String,
        @JsonProperty("modifiedAt") val modifiedAt: String,
    ) {
        companion object {
            fun of(courseWithPossibleReview: CourseWithPossibleReview): CourseWithPossibleApiReview {
                return CourseWithPossibleApiReview(
                    courseId = courseWithPossibleReview.courseId,
                    reviewId = courseWithPossibleReview.reviewId,
                    createdAt = courseWithPossibleReview.createdAt,
                    modifiedAt = courseWithPossibleReview.modifiedAt,
                )
            }
        }
    }

    data class ReviewWriteApiRequest(
        @JsonProperty("reviewId") val reviewId: Long,
        @JsonProperty("courseId") val courseId: Long,
        @JsonProperty("content") val content: String,
        @JsonProperty("originalNames") val originalNames: List<String>,
        @JsonProperty("reviewImageIds") val reviewImageIds: List<Long>,
        val reviewImages: List<MultipartFile>,
    ) {
        init {
            require(reviewImages.size == originalNames.size && originalNames.size == reviewImageIds.size) {
                throw ContentException(ContentExceptionCode.BAD_REQUEST_REVIEW)
            }

            require(reviewImages.sumOf { it.size } <= 10 * 1024 * 1024) {
                throw ContentException(ContentExceptionCode.BAD_REQUEST_REVIEW_IMAGE_SIZE)
            }
        }
    }


}