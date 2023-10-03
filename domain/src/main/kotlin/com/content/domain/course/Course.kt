package com.content.domain.course

import com.content.domain.location.Location
import com.content.domain.share.PlaceCategory

data class Course(
    val groupId: Long,
    val courseId: Long,
    val userId: Long,
    val step: Int,
    val placeCategory: PlaceCategory,
    val location: Location,
    val visitedStatus: Boolean,
)