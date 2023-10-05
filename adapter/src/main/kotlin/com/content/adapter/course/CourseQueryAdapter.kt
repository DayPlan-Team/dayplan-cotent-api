package com.content.adapter.course

import com.content.adapter.course.persistence.CourseEntityRepository
import com.content.application.port.CourseQueryPort
import com.content.domain.course.Course
import org.springframework.stereotype.Component

@Component
class CourseQueryAdapter(
    private val courseEntityRepository: CourseEntityRepository,
) : CourseQueryPort {

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