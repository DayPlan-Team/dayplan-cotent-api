package com.content.api.public

import com.content.application.service.CourseSettingService
import com.content.application.port.UserQueryPort
import com.content.application.request.CourseSettingRequest
import com.content.domain.location.Location
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
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
class CourseSettingController(
    private val userQueryPort: UserQueryPort,
    private val courseSettingService: CourseSettingService,
) {
    @PostMapping
    fun setCourseAndGetCourseGroupId(
        @RequestHeader("UserId") userId: Long,
        @RequestBody request: CourseSettingApiRequest,
    ): ResponseEntity<CourseGroupResponse> {
        log.info("request = ${request}")
        val user = userQueryPort.verifyAndGetUser(userId)
        val courseGroupId = courseSettingService.setCourseAndGetGroupId(
            CourseSettingRequest(
                groupId = request.groupId,
                userId = user.userId,
                step = request.step,
                placeId = request.placeId,
                location = request.location,
            ),
        )

        return ResponseEntity.ok(
            CourseGroupResponse(
                courseGroupId = courseGroupId,
            )
        )
    }

    @GetMapping
    fun getCoursesInSameGroup(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("groupId") groupId: Long,
    ): ResponseEntity<CourseApiResponse> {
        val user = userQueryPort.verifyAndGetUser(userId)
        val courses = courseSettingService.getCoursesByGroup(
            groupId = groupId,
            userId = user.userId,
        )

        if (courses.isEmpty()) throw ContentException(ContentExceptionCode.CONTENT_GROUP_EMPTY)

        return ResponseEntity.ok(
            CourseApiResponse(
                groupId = courses[0].groupId,
                courses = courses.map {
                    CourseItem(
                        courseId = it.courseId,
                        placeId = it.placeId,
                        step = it.step,
                    )
                },
            ),
        )
    }

    data class CourseGroupResponse(
        @JsonProperty("courseGroupId") val courseGroupId: Long,
    )


    data class CourseSettingApiRequest(
        @JsonProperty("groupId") val groupId: Long = 0,
        @JsonProperty("step") val step: Int,
        @JsonProperty("placeId") val placeId: Long,
        @JsonProperty("location") val location: Location,
    )

    data class CourseApiResponse(
        @JsonProperty("groupId") val groupId: Long,
        @JsonProperty("courses") val courses: List<CourseItem>,
    )

    data class CourseItem(
        @JsonProperty("courseId") val courseId: Long,
        @JsonProperty("placeId") val placeId: Long,
        @JsonProperty("step") val step: Int,
    )

    companion object : Logger()
}
