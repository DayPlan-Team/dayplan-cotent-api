package com.content.domain.review

import java.time.LocalDateTime

data class ReviewGroup(
    val courseGroupId: Long,
    val reviewGroupId: Long,
    val reviewGroupName: String,
    val userId: Long,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
    companion object {
        const val DEFAULT_NAME = "제목없음"
    }
}