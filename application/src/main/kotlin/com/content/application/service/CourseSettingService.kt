package com.content.application.service

import com.content.application.port.CourseCommandPort
import com.content.application.port.CourseQueryPort
import com.content.application.request.CourseGroupProfileRequest
import com.content.application.request.CourseSettingRequest
import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseSettingService(
    private val courseQueryPort: CourseQueryPort,
    private val courseCommandPort: CourseCommandPort,
) {

    @Transactional
    fun setCourseAndGetGroupId(courseSettingRequest: CourseSettingRequest): Long {
        return if (courseSettingRequest.groupId == 0L) {
            courseCommandPort.createCourseGroupAndCourse(courseSettingRequest)
        } else {
            val courseGroup = courseQueryPort.getCourseGroupByGroupIdAndUserId(
                groupId = courseSettingRequest.groupId,
                userId = courseSettingRequest.userId,
            )
            courseCommandPort.upsertCourse(courseSettingRequest)
            courseGroup.groupId
        }
    }

    fun getCoursesByGroup(userId: Long, groupId: Long): List<Course> {
        return courseQueryPort
            .getCoursesByGroupIdAndUserId(groupId = groupId, userId = userId)
            .sortedBy { it.step }
    }

    fun updateCourseGroupProfile(courseGroupProfileRequest: CourseGroupProfileRequest) {
        val courseGroup = courseQueryPort.getCourseGroupByGroupIdAndUserId(
            groupId = courseGroupProfileRequest.groupId,
            userId = courseGroupProfileRequest.userId,
        )

        courseCommandPort.upsertCourseGroup(
            CourseGroup(
                groupId = courseGroup.groupId,
                userId = courseGroup.userId,
                groupName = courseGroupProfileRequest.courseGroupName,
            )
        )
    }
}