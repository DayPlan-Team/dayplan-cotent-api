package com.content.domain.course.port

import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface CourseGroupQueryPort {
    fun getCourseGroupByUserId(userId: Long): List<CourseGroup>

    fun getCourseGroupByIds(courseGroupIds: List<Long>): List<CourseGroup>

    fun getCourseGroupById(courseGroupId: Long): CourseGroup
}
