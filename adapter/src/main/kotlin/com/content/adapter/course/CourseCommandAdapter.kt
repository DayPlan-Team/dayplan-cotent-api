package com.content.adapter.course

import com.content.adapter.course.entity.CourseEntity
import com.content.adapter.course.entity.CourseGroupEntity
import com.content.adapter.course.persistence.CourseEntityRepository
import com.content.adapter.course.persistence.CourseGroupEntityRepository
import com.content.application.port.CourseCommandPort
import com.content.application.request.CourseSettingRequest
import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import com.content.util.share.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CourseCommandAdapter(
    private val courseEntityRepository: CourseEntityRepository,
    private val courseGroupEntityRepository: CourseGroupEntityRepository,
) : CourseCommandPort {
    override fun createCourseGroupAndCourse(courseSettingRequest: CourseSettingRequest): Long {
        val courseGroupEntity = courseGroupEntityRepository.save(
            CourseGroupEntity(
                userId = courseSettingRequest.userId,
                groupName = CourseGroup.DEFAULT_NAME,
            )
        )

        courseEntityRepository.save(
            CourseEntity(
                groupId = courseGroupEntity.id,
                userId = courseSettingRequest.userId,
                step = courseSettingRequest.step,
                placeId = courseSettingRequest.placeId,
                latitude = courseSettingRequest.location.latitude,
                longitude = courseSettingRequest.location.longitude,
                visitedStatus = false,
            )
        )

        return courseGroupEntity.id
    }

    override fun upsertCourse(courseSettingRequest: CourseSettingRequest) {
        courseEntityRepository.save(
            CourseEntity(
                groupId = courseSettingRequest.groupId,
                userId = courseSettingRequest.userId,
                step = courseSettingRequest.step,
                placeId = courseSettingRequest.placeId,
                latitude = courseSettingRequest.location.latitude,
                longitude = courseSettingRequest.location.longitude,
                visitedStatus = false,
                id = courseSettingRequest.groupId,
            )
        )
    }

    override fun upsertCourseGroup(courseGroup: CourseGroup) {
        courseGroupEntityRepository.save(
            CourseGroupEntity.fromCourseGroup(courseGroup)
        )
    }

    override fun upsertCourses(courses: List<Course>) {
        courseEntityRepository.saveAll(
            courses.map {
                CourseEntity.fromCourse(it)
            }
        )
    }

    companion object : Logger()
}