package com.content.application.port

import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface CourseQueryPort {
    fun getCourseGroupByGroupIdAndUserId(courseGroupId: Long, userId: Long): CourseGroup

    fun getCoursesByGroupIdAndUserId(courseGroupId: Long, userId: Long): List<Course>

    fun getCursesByUserIdAndNotVisited(userId: Long): List<Course>
}