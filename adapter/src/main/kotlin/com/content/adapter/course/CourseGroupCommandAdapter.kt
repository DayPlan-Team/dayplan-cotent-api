package com.content.adapter.course

import com.content.adapter.course.entity.CourseGroupEntity
import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.application.port.CourseGroupCommandPort
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CourseGroupCommandAdapter(
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
) : CourseGroupCommandPort {
    override fun upsertCourseGroup(courseGroup: CourseGroup): CourseGroup {
        return courseGroupEntityRepository.save(
            CourseGroupEntity.fromCourseGroup(courseGroup)
        ).toDomainModel()
    }
}