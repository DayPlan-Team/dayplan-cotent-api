package com.content.util.exceptioncode

enum class ContentExceptionCode(
    val status: Int,
    val errorCode: String,
    val message: String,
) {
    USER_INVALID(403, "COTR-001", "올바르지 않은 계정 상태입니다."),
    CONTENT_GROUP_INVALID(400, "COTR-002", "잘못된 요청입니다."),
    CONTENT_GROUP_USER_ID_NOT_MATCH(400, "COTR-003", "잘못된 요청입니다."),

    BAD_REQUEST_CITY_CODE(400, "COTR-004", "올바르지 않은 시/도입니다."),
    BAD_REQUEST_DISTRICT_CODE(400, "COTR-005", "올바르지 않은 시/군/구입니다."),

    CONTENT_GROUP_EMPTY(400, "COTR-006", "코스를 추가해야 합니다."),
    COURSE_SEARCH_BAD_REQUEST(400, "COTR-007", "올바르지 않은 요청입니다."),
    CONTENT_COURSE_BAD_REQUEST(400, "COTR-008", "올바르지 않은 요청입니다."),
    NOT_POSSIBLE_REVIEW_COURSE_GROUP(400, "COTR-009", "올바르지 않은 요청입니다."),
    ALREADY_REVIEW_COURSE_GROUP(400, "COTR-010", "이미 처리 중인 요청입니다."),
    NOT_FOUND_REVIEW_GROUP(400, "COTR-011", "올바르지 않은 요청입니다."),
    DEFAULT_BAD_REQUEST(400, "COTR-012", "올바르지 않은 요청입니다."),
    NOT_FOUND_REVIEW(400, "COTR-013", "리뷰를 찾을 수 없습니다."),
    NOT_FOUND_REVIEW_IMAGE(400, "COTR-014", "리뷰 이미지를 찾을 수 없습니다."),
    BAD_REQUEST_REVIEW_IMAGE(400, "COTR-015", "잘못된 리뷰 이미지 정보입니다"),
    BAD_REQUEST_REVIEW_IMAGE_SIZE(400, "COTR-016", "이미지 사이즈 총합은 10MB를 넘을 수 없습니다."),
    BAD_REQUEST_REVIEW(400, "COTR-017", "잘못된 리뷰 요청입니다."),
    BAD_REQUEST_COURSE_NOT_UPDATE_ALREADY_REVIEW(400, "COTR-018", "이미 리뷰가 작성된 데이트 코스는 수정할 수 없습니다."),
}
