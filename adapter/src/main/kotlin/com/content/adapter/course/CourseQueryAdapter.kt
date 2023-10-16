package com.content.adapter.course

import com.content.adapter.course.persistence.CourseEntityRepository
import com.content.application.port.CourseQueryPort
import com.content.domain.course.Course
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class CourseQueryAdapter(
    private val courseEntityRepository: CourseEntityRepository,
) : CourseQueryPort {
    override fun getCourseById(id: Long): Course {
        return courseEntityRepository.findById(id).orElseThrow { ContentException(ContentExceptionCode.CONTENT_COURSE_BAD_REQUEST) }
            .toDomainModel()
    }

    override fun getCoursesByGroupId(groupId: Long): List<Course> {
        return courseEntityRepository.findCourseEntitiesByGroupId(groupId)
            .map {
                it.toDomainModel()
            }
    }

    override fun getCursesByUserIdAndVisitedStatus(userId: Long, visitedStatus: Boolean): List<Course> {
        return courseEntityRepository.findCourseEntitiesByUserIdAndVisitedStatus(userId, visitedStatus)
            .map {
                it.toDomainModel()
            }
    }

    override fun getCourseByUserId(userId: Long): List<Course> {
        return courseEntityRepository.findCourseEntitiesByUserId(userId)
            .map { it.toDomainModel() }
    }
}