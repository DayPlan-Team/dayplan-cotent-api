package com.content.api.public

import com.content.application.service.PossibleReviewCourseFindService
import com.content.application.service.UserVerifyService
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/coursegroup/possible-review")
class PossibleReviewCourseController(
    private val userVerifyService: UserVerifyService,
    private val possibleReviewCourseFindService: PossibleReviewCourseFindService,
) {

    @GetMapping
    fun getCourseGroupWithPossibleReview(@RequestHeader("UserId") userId: Long): ResponseEntity<PossibleReviewCourseGroups> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)

        val possibleReviewCourseGroups = possibleReviewCourseFindService.getPossibleReviewCourseGroup(userId = user.userId)
            .map {
                PossibleReviewCourseGroup(
                    courseGroupId = it.groupId,
                    courseGroupName = it.groupName,
                )
            }

        return ResponseEntity.ok(
            PossibleReviewCourseGroups(
                possibleReviewCourseGroups = possibleReviewCourseGroups
            )
        )
    }

    data class PossibleReviewCourseGroups(
        @JsonProperty("possibleReviewCourseCroups") val possibleReviewCourseGroups: List<PossibleReviewCourseGroup>
    )

    data class PossibleReviewCourseGroup(
        @JsonProperty("courseGroupId") val courseGroupId: Long,
        @JsonProperty("courseGroupName") val courseGroupName: String,
    )
}