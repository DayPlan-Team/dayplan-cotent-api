package com.user.api.exception

data class ExceptionResponse(
    val status: Int,
    val code: String,
    val message: String,
)
