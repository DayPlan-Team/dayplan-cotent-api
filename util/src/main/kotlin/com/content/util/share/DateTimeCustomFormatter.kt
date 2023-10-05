package com.content.util.share

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeCustomFormatter {

    private val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun toDefaultFormat(): String {
        return LocalDateTime.now().format(defaultFormatter)
    }
}