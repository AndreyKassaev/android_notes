package com.kassaev.notes

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object Util {
    fun toLocalDateTime(timeStamp: Long): String =
        DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
            .format(
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(timeStamp),
                    ZoneId.systemDefault()
                )
            )
}
