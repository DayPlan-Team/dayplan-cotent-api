package com.content.api.public

import com.content.application.service.CourseGroupService
import com.content.application.service.PossibleReviewCourseFindService
import com.content.application.service.UserVerifyService
import com.content.domain.review.ReviewGroupCommandUseCase
import com.content.domain.review.ReviewGroupUpdateRequest
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/review")
class ReviewGroupUpsertController(
    private val userVerifyService: UserVerifyService,
    private val courseGroupService: CourseGroupService,
    private val possibleReviewCourseFindService: PossibleReviewCourseFindService,
    private val reviewGroupCommandUseCase: ReviewGroupCommandUseCase,
) {

    @PostMapping("/reviewgroups")
    fun createReviewGroup(
        @RequestHeader("UserId") userId: Long,
        @RequestBody reviewCreateGroupApiRequest: ReviewCreateGroupApiRequest,
    ): ResponseEntity<ReviewGroupResponse> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)

        val courseGroup = courseGroupService.getCourseGroup(user.userId, reviewCreateGroupApiRequest.courseGroupId)
        possibleReviewCourseFindService.verifyPossibleReviewCourseGroup(courseGroup)

        val reviewGroup = reviewGroupCommandUseCase.createReviewGroupOrGet(courseGroup)

        return ResponseEntity.ok().body(
            ReviewGroupResponse(reviewGroup.reviewGroupId)
        )
    }

    @PutMapping("/reviewgroups/{reviewGroupId}")
    fun updateReviewGroupId(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("reviewGroupId") reviewGroupId: Long,
        @RequestBody reviewGroupUpdateApiRequest: ReviewGroupUpdateApiRequest,
    ): ResponseEntity<ReviewGroupResponse> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)

        val reviewGroup = reviewGroupCommandUseCase.updateReviewGroup(
            userId = user.userId,
            reviewGroupId = reviewGroupId,
            reviewGroupUpdateRequest = ReviewGroupUpdateRequest(
                reviewGroupName = reviewGroupUpdateApiRequest.reviewGroupName,
            )
        )

        return ResponseEntity.ok().body(
            ReviewGroupResponse(reviewGroup.reviewGroupId)
        )
    }


    data class ReviewCreateGroupApiRequest(
        @JsonProperty("courseGroupId") val courseGroupId: Long,
    )

    data class ReviewGroupUpdateApiRequest(
        @JsonProperty("reviewGroupName") val reviewGroupName: String,
    )

    data class ReviewGroupResponse(
        @JsonProperty("reviewGroupId") val reviewGroupId: Long,
    )
}