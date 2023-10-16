package com.content.util.exceptioncode
enum class ContentExceptionCode(
    val status: Int,
    val errorCode: String,
    val message: String,
) {
    USER_INVALID(403, "COTR-001", "올바르지 않은 계정 상태에요."),
    CONTENT_GROUP_INVALID(400, "COTR-002", "잘못된 요청이에요"),
    CONTENT_GROUP_USER_ID_NOT_MATCH(400, "COTR-003", "잘못된 요청이에요"),

    BAD_REQUEST_CITY_CODE(400, "COTR-004", "올바르지 않은 시/도에요"),
    BAD_REQUEST_DISTRICT_CODE(400, "COTR-005", "올바르지 않은 시/군/구에요"),

    CONTENT_GROUP_EMPTY(400, "COTR-006", "코스를 추가해주세요."),
    COURSE_SEARCH_BAD_REQUEST(400, "COTR-007", "올바르지 않은 요청이에요."),
    CONTENT_COURSE_BAD_REQUEST(400, "COTR-008", "올바르지 않은 요청이에요."),
    NOT_POSSIBLE_REVIEW_COURSE_GROUP(400, "COTR-009", "올바르지 않은 요청이에요."),
    ALREADY_REVIEW_COURSE_GROUP(400, "COTR-010", "이미 처리 중인 요청이에요."),
}

