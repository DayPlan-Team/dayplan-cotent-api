package com.content.application.service

import com.content.application.port.CourseSearchPort
import com.content.application.request.CourseSearchRequest
import com.content.application.response.DetailCourse
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class CourseSearchService(
    private val courseSearchPort: CourseSearchPort,
) {
    fun searchCourses(courseSearchRequest: CourseSearchRequest): Slice<DetailCourse> {
        return courseSearchPort.findCoursesByAddressCategory(courseSearchRequest)
    }

}