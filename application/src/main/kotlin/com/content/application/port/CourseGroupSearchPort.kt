package com.content.application.port

import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupSearchResponse
import org.springframework.stereotype.Component

@Component
interface CourseGroupSearchPort {

    fun findCourseGroupBy(request: CourseGroupAdministrativeSearchRequest): CourseGroupSearchResponse
}