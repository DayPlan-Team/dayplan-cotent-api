package com.content.application.response

data class CourseSearchResponse(
    val nickname: String,
    val groupId: Long,
    val courseDetail: List<DetailCourse>,
    val contents: String,
)
