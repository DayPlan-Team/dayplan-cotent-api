package com.content.api.public

import com.content.application.service.CourseSearchService
import com.content.application.port.UserQueryPort
import com.content.application.request.CourseSearchRequest
import com.content.application.response.DetailCourse
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
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
class CourseSearchController(
    private val userQueryPort: UserQueryPort,
    private val courseSearchService: CourseSearchService,
) {

    @GetMapping
    fun searchCourse(
        @RequestHeader("UserId") userId: Long,
        @RequestParam("addresscategory") addressCategory: AddressCategory,
        pageable: Pageable,
    ): ResponseEntity<Slice<DetailCourse>> {
        validatePage(pageable.pageNumber.toLong())

        userQueryPort.verifyAndGetUser(userId)
        val sliceData = courseSearchService.searchCourses(
            CourseSearchRequest(
                addressCategory = addressCategory,
                pageable = pageable,
            ),
        )

        return ResponseEntity.ok(sliceData)
    }

    private fun validatePage(page: Long) {
        require(page >= 0) { throw ContentException(ContentExceptionCode.COURSE_SEARCH_BAD_REQUEST) }
    }
}