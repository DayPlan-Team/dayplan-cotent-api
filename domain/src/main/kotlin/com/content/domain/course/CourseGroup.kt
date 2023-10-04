package com.content.domain.course

data class CourseGroup(
    val userId: Long,
    val groupId: Long,
    val groupName: String = DEFAULT_NAME,
) {
    companion object {
        const val DEFAULT_NAME = "제목 없음"
    }
}