package com.content.adapter.course

import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.domain.course.CourseGroup
import com.content.domain.course.port.CourseGroupQueryPort
import com.content.util.exception.ContentException
import com.content.util.exceptioncode.ContentExceptionCode
import org.springframework.stereotype.Component

@Component
class CourseGroupQueryAdapter(
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
) : CourseGroupQueryPort {
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

    override fun getCourseGroupById(courseGroupId: Long): CourseGroup {
        return courseGroupEntityRepository.findById(courseGroupId)
            .orElseThrow { throw ContentException(ContentExceptionCode.CONTENT_COURSE_BAD_REQUEST) }
            .toDomainModel()
    }
}
