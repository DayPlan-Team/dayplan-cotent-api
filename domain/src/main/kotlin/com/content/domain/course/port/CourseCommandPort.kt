package com.content.domain.course.port

import com.content.domain.course.Course
import org.springframework.stereotype.Component

@Component
interface CourseCommandPort {
    fun upsertCourse(course: Course)

    fun upsertCourses(courses: List<Course>)
}
