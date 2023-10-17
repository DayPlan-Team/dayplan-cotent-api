package com.content.domain.course.port

import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface CourseGroupQueryPort {
    fun getCourseGroupByGroupIdAndUserId(groupId: Long, userId: Long): CourseGroup

    fun getCourseGroupByUserId(userId: Long): List<CourseGroup>

    fun getCourseGroupByIds(courseGroupIds: List<Long>): List<CourseGroup>
}