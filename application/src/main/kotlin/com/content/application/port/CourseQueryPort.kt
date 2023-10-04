package com.content.application.port

import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface CourseQueryPort {
    fun getCourseGroupByGroupIdAndUserId(groupId: Long, userId: Long): CourseGroup

    fun getCoursesByGroupIdAndUserId(groupId: Long, userId: Long): List<Course>

    fun getCursesByUserIdAndVisitedStatus(userId: Long, visitedStatus: Boolean): List<Course>
}