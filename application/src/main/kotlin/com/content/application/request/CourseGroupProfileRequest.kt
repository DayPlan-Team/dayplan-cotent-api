package com.content.application.request

data class CourseGroupProfileRequest(
    val groupId: Long,
    val userId: Long,
    val courseGroupName: String,
)