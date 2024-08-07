package com.content.api.publics

import com.content.application.service.CourseGroupService
import com.content.application.service.UserVerifyService
import com.content.domain.course.CourseGroup
import com.content.util.address.AddressUtil
import com.content.util.share.DateTimeCustomFormatter
import com.content.util.share.Logger
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/coursegroup")
class CourseGroupController(
    private val userVerifyService: UserVerifyService,
    private val courseGroupService: CourseGroupService,
) {
    @PostMapping
    fun upsertCourseGroup(
        @RequestHeader("UserId") userId: Long,
        @RequestBody courseGroupRequest: CourseGroupApiRequest,
    ): ResponseEntity<CourseGroupApiResponse> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)

        val address =
            AddressUtil.verifyAddressCodeAndGet(
                cityCodeNumber = courseGroupRequest.cityCode,
                districtCodeNumber = courseGroupRequest.districtCode,
            )

        val response =
            courseGroupService.upsertCourseGroup(
                CourseGroup(
                    groupId = courseGroupRequest.groupId ?: 0L,
                    groupName =
                        courseGroupRequest.groupName
                            ?: (CourseGroup.DEFAULT_NAME + "_" + DateTimeCustomFormatter.nowToDefaultFormat()),
                    userId = user.userId,
                    cityCode = address.cityCode,
                    districtCode = address.districtCode,
                ),
            ).let {
                CourseGroupApiResponse(
                    groupId = it.groupId,
                    groupName = it.groupName,
                    cityCode = it.cityCode.code,
                    cityName = it.cityCode.koreanName,
                    districtCode = it.districtCode.code,
                    districtName = it.districtCode.koreanName,
                )
            }

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{courseGroup}")
    fun getCourseGroup(
        @RequestHeader("UserId") userId: Long,
        @PathVariable("courseGroup") courseGroupId: Long,
    ): ResponseEntity<CourseGroupApiResponse> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)
        val response =
            courseGroupService.getCourseGroup(
                userId = user.userId,
                groupId = courseGroupId,
            ).let {
                CourseGroupApiResponse(
                    groupId = it.groupId,
                    groupName = it.groupName,
                    cityCode = it.cityCode.code,
                    cityName = it.cityCode.koreanName,
                    districtCode = it.districtCode.code,
                    districtName = it.districtCode.koreanName,
                )
            }

        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getCourseGroups(
        @RequestHeader("UserId") userId: Long,
    ): ResponseEntity<CourseGroupsApiResponse> {
        val user = userVerifyService.verifyNormalUserAndGet(userId)
        val courseGroups =
            courseGroupService.getCourseGroups(user.userId)
                .map {
                    CourseGroupApiResponse(
                        groupId = it.groupId,
                        groupName = it.groupName,
                        cityCode = it.cityCode.code,
                        cityName = it.cityCode.koreanName,
                        districtCode = it.districtCode.code,
                        districtName = it.districtCode.koreanName,
                    )
                }

        return ResponseEntity.ok(
            CourseGroupsApiResponse(
                courseGroups = courseGroups,
            ),
        )
    }

    data class CourseGroupApiRequest(
        @JsonProperty("groupId") val groupId: Long?,
        @JsonProperty("groupName") val groupName: String?,
        @JsonProperty("cityCode") val cityCode: Long,
        @JsonProperty("districtCode") val districtCode: Long,
    )

    data class CourseGroupApiResponse(
        @JsonProperty("groupId") val groupId: Long,
        @JsonProperty("groupName") val groupName: String,
        @JsonProperty("cityCode") val cityCode: Long,
        @JsonProperty("cityName") val cityName: String,
        @JsonProperty("districtCode") val districtCode: Long,
        @JsonProperty("districtName") val districtName: String,
    )

    data class CourseGroupsApiResponse(
        @JsonProperty("courseGroups") val courseGroups: List<CourseGroupApiResponse>,
    )

    companion object : Logger()
}
