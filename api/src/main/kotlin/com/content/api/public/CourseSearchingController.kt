package com.content.api.public

import com.content.application.service.CourseSearchingService
import com.content.application.port.UserQueryPort
import com.content.application.request.CourseSearchRequest
import com.content.application.response.CourseDetailResponse
import com.content.util.share.AddressCategory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(("/content/course/search"))
class CourseSearchingController(
    private val userQueryPort: UserQueryPort,
    private val courseSearchingService: CourseSearchingService,
) {

    @GetMapping
    fun searchCourse(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("address_category") addressCategory: AddressCategory,
        pageable: Pageable,
    ): ResponseEntity<Slice<CourseDetailResponse>> {
        validatePage(pageable.pageNumber.toLong())

        userQueryPort.findById(userId)
        val sliceData = courseSearchingService.searchCourses(
            CourseSearchRequest(
                addressCategory = addressCategory,
                pageable = pageable,
            ),
        )

        return ResponseEntity.ok(sliceData)
    }

    private fun validatePage(page: Long) {
        require(page >= 0) { throw IllegalArgumentException() }
    }
}