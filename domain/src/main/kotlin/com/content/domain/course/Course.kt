package com.content.domain.course

import com.content.domain.share.PlaceCategory

data class Course(
    val courseId: Long,
    val step: Int,
    val placeId: Long,
    val courseStage: CourseStage,
    val placeCategory: PlaceCategory,
    val visitedStatus: Boolean = false,
    val groupId: Long = 0L,
)