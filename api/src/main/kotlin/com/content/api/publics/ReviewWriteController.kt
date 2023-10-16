package com.content.api.publics

import com.content.application.service.UserVerifyService
import com.content.domain.review.CourseWithPossibleReview
import com.content.domain.review.ReviewWriteUseCase
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/reviewgroups/{reviewGroupId}/reviews")
class ReviewWriteController(
    private val userVerifyService: UserVerifyService,
    private val reviewWriteUseCase: ReviewWriteUseCase,
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

    data class CourseWithPossibleApiReviews(
        @JsonProperty("courses") val courses: List<CourseWithPossibleApiReview>,
    )

    data class CourseWithPossibleApiReview(
        @JsonProperty("courseId") val courseId: Long,
        @JsonProperty("reviewId") val reviewId: Long,
        @JsonProperty("reviewTitle") val reviewTitle: String,
        @JsonProperty("createdAt") val createdAt: String,
        @JsonProperty("modifiedAt") val modifiedAt: String,
    ) {
        companion object {
            fun of(courseWithPossibleReview: CourseWithPossibleReview): CourseWithPossibleApiReview {
                return CourseWithPossibleApiReview(
                    courseId = courseWithPossibleReview.courseId,
                    reviewId = courseWithPossibleReview.reviewId,
                    reviewTitle = courseWithPossibleReview.reviewTitle,
                    createdAt = courseWithPossibleReview.createdAt,
                    modifiedAt = courseWithPossibleReview.modifiedAt,
                )
            }
        }
    }

}