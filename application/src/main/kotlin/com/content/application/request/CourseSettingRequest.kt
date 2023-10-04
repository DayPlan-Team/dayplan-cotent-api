package com.content.application.request

import com.content.domain.course.Course
import com.content.domain.location.Location

data class CourseSettingRequest(
    val groupId: Long,
    val userId: Long,
    val step: Int,
    val placeId: Long,
    val location: Location,
)