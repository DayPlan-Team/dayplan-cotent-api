package com.content.adapter.course

import com.content.adapter.course.persistence.CourseEntityRepository
import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.domain.course.Course
import com.content.domain.course.port.CourseQueryPort
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class CourseQueryAdapter(
    private val courseEntityRepository: CourseEntityRepository,
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
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

    override fun getCoursesByGroupIds(groupIds: List<Long>): List<Course> {
        return courseEntityRepository.findCourseEntitiesByGroupIdIn(groupIds)
            .map { it.toDomainModel() }
    }

    override fun getCursesByUserIdAndVisitedStatus(
        userId: Long,
        visitedStatus: Boolean,
    ): List<Course> {
        val courseGroups = courseGroupEntityRepository.findCourseGroupEntitiesByUserId(userId)

        return courseEntityRepository.findCourseEntitiesByGroupIdInAndVisitedStatus(courseGroups.map { it.userId }, visitedStatus)
            .map {
                it.toDomainModel()
            }
    }
}
