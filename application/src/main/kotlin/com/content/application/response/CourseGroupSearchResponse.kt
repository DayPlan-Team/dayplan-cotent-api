package com.content.application.response

import java.time.LocalDateTime

data class CourseGroupSearchResponse(
    val hasNext: Boolean,
    val courseGroupItems: List<CourseGroupItem>,
)

data class CourseGroupItem(
    val title: String,
    val groupId: Long,
    val cityName: String,
    val districtName: String,
    val modifiedAt: LocalDateTime,
)
