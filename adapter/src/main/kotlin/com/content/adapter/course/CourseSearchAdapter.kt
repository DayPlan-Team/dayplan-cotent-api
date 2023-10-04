package com.content.adapter.course

import com.content.application.port.CourseSearchPort
import com.content.application.request.CourseSearchRequest
import com.content.application.response.CourseDetailResponse
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Component

@Component
class CourseSearchAdapter : CourseSearchPort {
    override fun findCoursesByAddressCategory(courseSearchRequest: CourseSearchRequest): Slice<CourseDetailResponse> {
        TODO("Not yet implemented")
    }
}