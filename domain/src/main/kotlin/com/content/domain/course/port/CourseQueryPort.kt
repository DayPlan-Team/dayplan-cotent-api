package com.content.domain.course.port

import com.content.domain.course.Course
import org.springframework.stereotype.Component

@Component
interface CourseQueryPort {
    fun getCourseById(id: Long): Course

    fun getCoursesByGroupId(groupId: Long): List<Course>

    fun getCoursesByGroupIds(groupIds: List<Long>): List<Course>

    fun getCursesByUserIdAndVisitedStatus(
        userId: Long,
        visitedStatus: Boolean,
    ): List<Course>
}
