package com.sandrocaseiro.template.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

private const val DATE_PATTERN = "uuuu-MM-dd"
private const val TIME_PATTERN = "HH:mm"
private const val DATE_TIME_PATTERN = "uuuu-MM-dd'T'HH:mm:ss'Z'"

fun String?.toDate(): LocalDate? = this.toDate(DATE_PATTERN)

fun String?.toDate(pattern: String): LocalDate? = if (this.isNullOrBlank()) null else dateFormatter(pattern).let { LocalDate.parse(this, it) }

fun String?.toDateTime(): LocalDateTime? = this.toDateTime(DATE_TIME_PATTERN)

fun String?.toDateTime(pattern: String): LocalDateTime? = if (this.isNullOrBlank()) null else dateFormatter(pattern).let { LocalDateTime.parse(this, it) }

fun String?.toTime(): LocalTime? = this.toTime(TIME_PATTERN)

fun String?.toTime(pattern: String): LocalTime? = if (this.isNullOrBlank()) null else dateFormatter(pattern).let { LocalTime.parse(this, it) }

fun LocalDate?.toDateString(): String? = this.toDateString(DATE_PATTERN)

fun LocalDate?.toDateString(pattern: String): String? = this?.format(dateFormatter(pattern))

fun LocalDateTime?.toDateTimeString(): String? = this.toDateTimeString(DATE_TIME_PATTERN)

fun LocalDateTime?.toDateTimeString(pattern: String): String? = this?.format(dateFormatter(pattern))

fun LocalTime?.toTimeString(): String? = this.toTimeString(TIME_PATTERN)

fun LocalTime?.toTimeString(pattern: String): String? = this?.format(dateFormatter(pattern))

private fun dateFormatter(pattern: String): DateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT)
