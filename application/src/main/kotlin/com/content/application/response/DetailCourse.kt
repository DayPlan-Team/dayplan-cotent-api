package com.content.application.response

import com.content.domain.course.CourseStage
import com.content.domain.share.PlaceCategory

data class DetailCourse(
    val step: Int,
    val placeCategory: PlaceCategory,
    val courseStage: CourseStage,
    val latitude: Double?,
    val longitude: Double?,
    val courseId: Long,
    val placeId: Long,
    val placeName: String = "",
    val address: String = "",
    val roadAddress: String = "",
)