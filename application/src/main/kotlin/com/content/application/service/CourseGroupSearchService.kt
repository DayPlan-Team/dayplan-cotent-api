package com.content.application.service

import com.content.application.port.CourseGroupSearchPort
import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupSearchResponse
import org.springframework.stereotype.Service

@Service
class CourseGroupSearchService(
    private val courseGroupSearchPort: CourseGroupSearchPort,
) {
    fun searchCourseGroupsBy(courseGroupAdministrativeSearchRequest: CourseGroupAdministrativeSearchRequest): CourseGroupSearchResponse {
        return courseGroupSearchPort.findCourseGroupBy(courseGroupAdministrativeSearchRequest)
    }

}