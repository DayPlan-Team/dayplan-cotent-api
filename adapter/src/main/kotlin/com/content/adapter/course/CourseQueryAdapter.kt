package com.content.adapter.course

import com.content.adapter.course.persistence.CourseEntityRepository
import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.application.port.CourseQueryPort
import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class CourseQueryAdapter(
    private val courseEntityRepository: CourseEntityRepository,
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
) : CourseQueryPort {
    override fun getCourseGroupByGroupIdAndUserId(groupId: Long, userId: Long): CourseGroup {
        val courseGroupEntity = courseGroupEntityRepository.findById(groupId)
            .orElseThrow { throw ContentException(ContentExceptionCode.CONTENT_GROUP_INVALID) }

        if (courseGroupEntity.userId == userId) {
            return courseGroupEntity.toCourseGroup()
        }
        throw ContentException(ContentExceptionCode.CONTENT_GROUP_USER_ID_NOT_MATCH)
    }

    override fun getCoursesByGroupIdAndUserId(groupId: Long, userId: Long): List<Course> {
        return courseEntityRepository.findCourseEntitiesByGroupIdAndUserId(groupId, userId)
            .map {
                it.toCourse()
            }
    }

    override fun getCursesByUserIdAndVisitedStatus(userId: Long, visitedStatus: Boolean): List<Course> {
        return courseEntityRepository.findCourseEntitiesByUserIdAndVisitedStatus(userId, visitedStatus)
            .map {
                it.toCourse()
            }
    }
}