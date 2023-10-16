package com.content.api.exception

import com.content.util.exception.ContentException
import com.content.util.exception.SystemException
import com.content.util.exceptioncode.ContentExceptionCode
import com.content.util.share.Logger
import com.fasterxml.jackson.databind.JsonMappingException
import com.user.api.exception.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ResponseStatus
    @ExceptionHandler(ContentException::class)
    fun handleUserException(exception: ContentException): ResponseEntity<ExceptionResponse> {
        val httpStatus = HttpStatus.valueOf(exception.status)
        log.info("exception = ${exception.message}")

        return ResponseEntity(
            ExceptionResponse(
                status = exception.status,
                code = exception.errorCode,
                message = exception.message,
            ), httpStatus
        )
    }

    @ResponseStatus
    @ExceptionHandler(SystemException::class)
    fun handleSystemException(exception: SystemException): ResponseEntity<ExceptionResponse> {
        val httpStatus = HttpStatus.valueOf(exception.status)

        log.info("exception = ${exception.message}")

        return ResponseEntity(
            ExceptionResponse(
                status = exception.status,
                code = exception.errorCode,
                message = exception.message,
            ), httpStatus
        )
    }

    @ResponseStatus
    @ExceptionHandler(JsonMappingException::class)
    fun handleJsonMappingException(exception: JsonMappingException): ResponseEntity<ExceptionResponse> {
        val httpStatus = HttpStatus.valueOf(400)

        log.info("exception = ${exception.message}")

        return ResponseEntity(
            ExceptionResponse(
                status = httpStatus.value(),
                code = ContentExceptionCode.DEFAULT_BAD_REQUEST.errorCode,
                message = ContentExceptionCode.DEFAULT_BAD_REQUEST.message,
            ), httpStatus
        )
    }

    companion object : Logger()
}