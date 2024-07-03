package com.content.util.share

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeCustomFormatter {
    private val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateMinuteFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun nowToDefaultFormat(): String {
        return LocalDateTime.now().format(defaultFormatter)
    }

    fun timeToDateMinuteFormat(localDateTime: LocalDateTime): String {
        return localDateTime.format(dateMinuteFormatter)
    }
}
