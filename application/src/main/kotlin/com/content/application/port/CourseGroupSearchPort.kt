package com.content.application.port

import com.content.application.request.CourseGroupAdministrativeSearchRequest
import com.content.application.response.CourseGroupListSearchResponse
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface CourseGroupSearchPort {

    fun findCourseGroupBy(request: CourseGroupAdministrativeSearchRequest): CourseGroupListSearchResponse

    fun findCourseGroupByGroupIds(courseGroupIds: List<Long>): List<CourseGroup>
}