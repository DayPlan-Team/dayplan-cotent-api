package com.content.adapter.course

import com.content.application.port.CourseSearchPort
import com.content.application.request.CourseSearchRequest
import com.content.application.response.DetailCourse
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Component

@Component
class CourseSearchAdapter : CourseSearchPort {
    override fun findCoursesByAddressCategory(courseSearchRequest: CourseSearchRequest): Slice<DetailCourse> {
        TODO("Not yet implemented")
    }
}
