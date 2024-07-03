package com.content.util.exceptioncode

enum class SystemExceptionCode(
    val status: Int,
    val errorCode: String,
    val message: String,
) {
    USER_SERVER_REST_EXCEPTION(500, "COTR-9000", "서버 연결이 올바르지 않아요. 잠시 후에 요청 부탁드려요."),
}
