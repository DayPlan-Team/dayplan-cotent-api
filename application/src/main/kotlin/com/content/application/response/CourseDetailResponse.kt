package com.content.application.response

import com.content.domain.share.PlaceCategory
import com.content.domain.location.Location

data class CourseDetailResponse(
    val step: Int,
    val placeCategory: PlaceCategory,
    val location: Location,
)