package com.content.domain.course.port

import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface CourseGroupCommandPort {
    fun upsertCourseGroup(courseGroup: CourseGroup): CourseGroup

}