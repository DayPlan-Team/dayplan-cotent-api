package com.content.application.request

import com.content.domain.location.Location
import com.content.domain.share.PlaceCategory

data class CourseSettingRequest(
    val groupId: Long,
    val userId: Long,
    val step: Int,
    val placeCategory: PlaceCategory,
    val placeName: String,
    val address: String,
    val roadAddress: String,
    val location: Location,
)