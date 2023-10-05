package com.content.application.service

import com.content.application.port.CourseGroupCommandPort
import com.content.application.port.CourseGroupQueryPort
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseGroupService(
    private val courseGroupQueryPort: CourseGroupQueryPort,
    private val courseGroupCommandPort: CourseGroupCommandPort,
) {
    @Transactional
    fun upsertCourseGroup(courseGroup: CourseGroup): CourseGroup {
        return courseGroupCommandPort.upsertCourseGroup(courseGroup)
    }

    fun getCourseGroup(userId: Long, groupId: Long): CourseGroup {
        return courseGroupQueryPort.getCourseGroupByGroupIdAndUserId(
            userId = userId,
            groupId = groupId,
        )
    }

    fun getCourseGroups(userId: Long): List<CourseGroup> {
        return courseGroupQueryPort.getCourseGroupByUserId(userId)
    }
}