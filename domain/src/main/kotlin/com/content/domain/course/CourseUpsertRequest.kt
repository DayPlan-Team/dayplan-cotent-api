package com.content.domain.course

import com.content.domain.share.PlaceCategory

data class CourseUpsertRequest(
    val userId: Long,
    val courseId: Long,
    val groupId: Long,
    val step: Int,
    val placeCategory: PlaceCategory,
    val placeId: Long,
)