package com.content.adapter.course

import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.application.port.CourseGroupQueryPort
import com.content.domain.course.CourseGroup
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class CourseGroupQueryAdapter(
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
) : CourseGroupQueryPort {
    override fun getCourseGroupByGroupIdAndUserId(groupId: Long, userId: Long): CourseGroup {
        val courseGroupEntity = courseGroupEntityRepository.findById(groupId)
            .orElseThrow { throw ContentException(ContentExceptionCode.CONTENT_GROUP_INVALID) }

        if (courseGroupEntity.userId == userId) {
            return courseGroupEntity.toDomainModel()
        }
        throw ContentException(ContentExceptionCode.CONTENT_GROUP_USER_ID_NOT_MATCH)
    }

    override fun getCourseGroupByUserId(userId: Long): List<CourseGroup> {
        return courseGroupEntityRepository.findCourseGroupEntitiesByUserId(userId)
            .sortedBy { it.modifiedAt }
            .map {
                it.toDomainModel()
            }
    }

    override fun getCourseGroupByIds(courseGroupIds: List<Long>): List<CourseGroup> {
        return courseGroupEntityRepository.findCourseGroupEntitiesByIdIn(courseGroupIds)
            .map { it.toDomainModel() }
    }
}