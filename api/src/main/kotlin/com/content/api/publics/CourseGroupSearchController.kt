package com.content.api.publics

import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupListSearchResponse
import com.content.application.response.CourseGroupWithUserNicknameResponse
import com.content.application.service.CourseGroupSearchService
import com.content.application.service.UserVerifyService
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import com.content.util.share.Logger
import com.user.util.address.AddressUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/content/coursegroup/search")
class CourseGroupSearchController(
    private val userVerifyService: UserVerifyService,
    private val courseGroupSearchService: CourseGroupSearchService,
) {

    @GetMapping("/district")
    fun getCourseGroups(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("cityCode") cityCode: Long,
        @RequestParam("districtCode") districtCode: Long,
        @RequestParam("start") start: Int,
    ): ResponseEntity<CourseGroupListSearchResponse> {
        userVerifyService.verifyNormalUserAndGet(userId)

        val address = AddressUtil.verifyAddressCodeAndGet(
            cityCodeNumber = cityCode,
            districtCodeNumber = districtCode,
        )

        verifyQuery(start)

        val courseGroupSearchResponse = courseGroupSearchService.searchCourseGroupsWithCourseBy(
            CourseGroupAdministrativeSearchRequest(
                cityCode = address.cityCode,
                districtCode = address.districtCode,
                start = start,
            )
        )

        return ResponseEntity.ok(courseGroupSearchResponse)
    }

    @GetMapping("/nickname")
    fun getCourseGroups(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("courseGroupIds") courseGroupIds: List<Long>,
    ): ResponseEntity<List<CourseGroupWithUserNicknameResponse>> {
        userVerifyService.verifyNormalUserAndGet(userId)

        val courseGroupSearchResponse = courseGroupSearchService.searchCourseGroupWithNickNameBy(courseGroupIds)

        log.info("courseGroupSearchResponse = $courseGroupSearchResponse")

        return ResponseEntity.ok(courseGroupSearchResponse)
    }

    private fun verifyQuery(start: Int) {
        require(start >= 0) { throw ContentException(ContentExceptionCode.COURSE_SEARCH_BAD_REQUEST) }
        require(start <= 999) { throw ContentException(ContentExceptionCode.COURSE_SEARCH_BAD_REQUEST) }
    }

    companion object : Logger()


}