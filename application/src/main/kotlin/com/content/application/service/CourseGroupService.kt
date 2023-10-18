package com.content.application.service

import com.content.domain.course.port.CourseGroupCommandPort
import com.content.domain.course.port.CourseGroupQueryPort
import com.content.domain.course.CourseGroup
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
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
        val courseGroup = courseGroupQueryPort.getCourseGroupById(groupId)
        require(courseGroup.userId == userId) { throw ContentException(ContentExceptionCode.USER_INVALID) }

        return courseGroup
    }

    fun getCourseGroups(userId: Long): List<CourseGroup> {
        return courseGroupQueryPort.getCourseGroupByUserId(userId)
    }
}