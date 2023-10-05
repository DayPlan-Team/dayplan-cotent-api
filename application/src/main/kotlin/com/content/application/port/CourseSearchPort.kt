package com.content.application.port

import com.content.application.request.CourseSearchRequest
import com.content.application.response.DetailCourse
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Component

@Component
interface CourseSearchPort {
    fun findCoursesByAddressCategory(courseSearchRequest: CourseSearchRequest): Slice<DetailCourse>
}