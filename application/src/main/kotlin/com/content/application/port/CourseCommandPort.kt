package com.content.application.port

import com.content.application.request.CourseSettingRequest
import com.content.application.response.CourseAndGroupResponse
import com.content.domain.course.Course
import com.content.domain.course.CourseGroup
import org.springframework.stereotype.Component

@Component
interface CourseCommandPort {

    fun createCourseGroupAndCourse(courseSettingRequest: CourseSettingRequest): CourseAndGroupResponse

    fun upsertCourse(courseSettingRequest: CourseSettingRequest)

    fun upsertCourseGroup(courseGroup: CourseGroup)

    fun upsertCourses(courses: List<Course>)
}