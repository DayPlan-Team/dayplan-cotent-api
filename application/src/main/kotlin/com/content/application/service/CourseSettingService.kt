package com.content.application.service

import com.content.application.port.CourseCommandPort
import com.content.application.port.CourseQueryPort
import com.content.application.request.CourseGroupProfileRequest
import com.content.application.request.CourseSettingRequest
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Service

@Service
class CourseSettingService(
    private val courseQueryPort: CourseQueryPort,
    private val courseCommandPort: CourseCommandPort,
) {
    fun setCourseAndGetGroupId(courseSettingRequest: CourseSettingRequest): Long {
        return if (courseSettingRequest.groupId == 0L) {
            val courseAndGroupResponse = courseCommandPort.createCourseGroupAndCourse(courseSettingRequest)
            courseAndGroupResponse.groupId
        } else {
            val courseGroup = courseQueryPort.getCourseGroupByGroupIdAndUserId(
                courseGroupId = courseSettingRequest.groupId,
                userId = courseSettingRequest.userId,
            )
            courseCommandPort.upsertCourse(courseSettingRequest)
            courseGroup.groupId
        }
    }

    fun updateCourseGroupProfile(courseGroupProfileRequest: CourseGroupProfileRequest) {
        val courseGroup = courseQueryPort.getCourseGroupByGroupIdAndUserId(
            courseGroupId = courseGroupProfileRequest.groupId,
            userId = courseGroupProfileRequest.userId,
        )

        courseCommandPort.upsertCourseGroup(
            CourseGroup(
                groupId = courseGroup.groupId,
                userId = courseGroup.userId,
                courseGroupName = courseGroupProfileRequest.courseGroupName,
            )
        )
    }
}