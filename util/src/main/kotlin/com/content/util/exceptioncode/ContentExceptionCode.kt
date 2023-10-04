package com.content.util.exceptioncode
enum class ContentExceptionCode(
    val status: Int,
    val errorCode: String,
    val message: String,
) {
    USER_INVALID(403, "COTR-001", "올바르지 않은 계정 상태에요."),
    CONTENT_GROUP_INVALID(400, "COTR-002", "잘못된 요청이에요"),
    CONTENT_GROUP_USER_ID_NOT_MATCH(400, "COTR-003", "잘못된 요청이에요"),

    CONTENT_GROUP_EMPTY(400, "COTR-004", "코스를 추가해주세요."),
}

