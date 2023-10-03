package com.content.api.public

import com.content.application.service.CourseSettingService
import com.content.application.port.UserQueryPort
import com.content.application.request.CourseSettingRequest
import com.content.domain.share.PlaceCategory
import com.content.domain.location.Location
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(("/content/course"))
class CourseSettingController(
    private val courseSettingService: CourseSettingService,
    private val userQueryPort: UserQueryPort,
) {
    @PostMapping
    fun setCourse(
        @RequestHeader("UserId") userId: Long,
        @RequestBody request: CourseSettingApiRequest,
    ) {
        val user = userQueryPort.findById(userId)
        courseSettingService.setCourseAndGetGroupId(
            CourseSettingRequest(
                groupId = request.groupId,
                userId = user.userId,
                step = request.step,
                placeCategory = request.placeCategory,
                placeName = request.placeName,
                address = request.address,
                roadAddress = request.roadAddress,
                location = request.location,
            ),
        )
    }

    data class CourseSettingApiRequest(
        @JsonProperty("groupId") val groupId: Long = 0,
        @JsonProperty("step") val step: Int,
        @JsonProperty("placeCategory") val placeCategory: PlaceCategory,
        @JsonProperty("placeName") val placeName: String,
        @JsonProperty("address") val address: String,
        @JsonProperty("roadAddress") val roadAddress: String,
        @JsonProperty("location") val location: Location,
    )
}
