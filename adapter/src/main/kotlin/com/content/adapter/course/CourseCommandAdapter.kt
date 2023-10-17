package com.content.adapter.course

import com.content.adapter.course.entity.CourseEntity
import com.content.adapter.course.persistence.CourseEntityRepository
import com.content.domain.course.port.CourseCommandPort
import com.content.domain.course.Course
import com.content.util.share.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CourseCommandAdapter(
    private val courseEntityRepository: CourseEntityRepository,
) : CourseCommandPort {

    override fun upsertCourse(course: Course) {
        courseEntityRepository.save(
            CourseEntity.fromCourse(course)
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