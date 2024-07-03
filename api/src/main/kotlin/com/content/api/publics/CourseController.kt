package com.content.api.publics

import com.content.application.service.CourseService
import com.content.application.service.UserVerifyService
import com.content.domain.course.CourseStage
import com.content.domain.course.CourseUpsertRequest
import com.content.domain.share.PlaceCategory
import com.content.util.share.Logger
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/course")
class CourseController(
    private val userVerifyService: UserVerifyService,
    private val courseService: CourseService,
) {
    @PostMapping
    fun upsertCourse(
        @RequestHeader("UserId") userId: Long,
        @RequestBody request: CourseUpsertApiRequest,
    ): ResponseEntity<Unit> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)

        courseService.upsertCourse(
            CourseUpsertRequest(
                userId = user.userId,
                courseId = request.courseId ?: 0L,
                groupId = request.groupId,
                step = request.step,
                placeCategory = request.placeCategory,
                placeId = request.placeId ?: 0L,
            ),
        )

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getCoursesInSameGroup(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("groupId") groupId: Long,
    ): ResponseEntity<CourseApiResponse> {
        userVerifyService.verifyNormalUserAndGet(userId)

        val courses =
            courseService.getDetailCoursesByGroup(
                groupId = groupId,
            )

        log.info("placeResponseSize = ${courses.size}")

        return ResponseEntity.ok(
            CourseApiResponse(
                groupId = groupId,
                courses =
                    courses.map {
                        CourseItem(
                            courseId = it.courseId,
                            placeId = it.placeId,
                            step = it.step,
                            placeCategory = it.placeCategory,
                            placeName = it.placeName,
                            address = it.address,
                            courseStage = it.courseStage,
                            roadAddress = it.roadAddress,
                        )
                    },
            ),
        )
    }

    data class CourseUpsertApiRequest(
        @JsonProperty("courseId") val courseId: Long?,
        @JsonProperty("groupId") val groupId: Long = 0,
        @JsonProperty("step") val step: Int,
        @JsonProperty("placeCategory") val placeCategory: PlaceCategory,
        @JsonProperty("placeId") val placeId: Long?,
    )

    data class CourseApiResponse(
        @JsonProperty("groupId") val groupId: Long,
        @JsonProperty("courses") val courses: List<CourseItem>,
    )

    data class CourseItem(
        @JsonProperty("courseId") val courseId: Long,
        @JsonProperty("placeId") val placeId: Long,
        @JsonProperty("step") val step: Int,
        @JsonProperty("placeCategory") val placeCategory: PlaceCategory,
        @JsonProperty("courseStage") val courseStage: CourseStage,
        @JsonProperty("placeName") val placeName: String,
        @JsonProperty("address") val address: String,
        @JsonProperty("roadAddress") val roadAddress: String,
    )

    companion object : Logger()
}
