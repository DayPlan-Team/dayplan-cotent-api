package com.content.domain.course

import com.content.domain.location.Location

data class Course(
    val courseId: Long,
    val userId: Long,
    val step: Int,
    val placeId: Long,
    val location: Location,
    val visitedStatus: Boolean = false,
    val groupId: Long = 0L,
)