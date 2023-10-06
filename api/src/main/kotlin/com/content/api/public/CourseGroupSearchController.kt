package com.content.api.public

import com.content.application.port.UserQueryPort
import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupSearchResponse
import com.content.application.service.CourseGroupSearchService
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
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
    private val userQueryPort: UserQueryPort,
    private val courseGroupSearchService: CourseGroupSearchService,
) {

    @GetMapping
    fun getCourseGroups(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("cityCode") cityCode: Long,
        @RequestParam("districtCode") districtCode: Long,
        @RequestParam("start") start: Int,
    ): ResponseEntity<CourseGroupSearchResponse> {
        userQueryPort.verifyAndGetUser(userId)

        val address = AddressUtil.verifyAddressCodeAndGet(
            cityCodeNumber = cityCode,
            districtCodeNumber = districtCode,
        )

        verifyQuery(start)

        val sliceResponse = courseGroupSearchService.searchCourseGroupsBy(
            CourseGroupAdministrativeSearchRequest(
                cityCode = address.cityCode,
                districtCode = address.districtCode,
                start = start,
            )
        )

        return ResponseEntity.ok(sliceResponse)
    }


    private fun verifyQuery(start: Int) {
        require(start >= 0) { throw ContentException(ContentExceptionCode.COURSE_SEARCH_BAD_REQUEST) }
        require(start <= 999) { throw ContentException(ContentExceptionCode.COURSE_SEARCH_BAD_REQUEST) }
    }


}