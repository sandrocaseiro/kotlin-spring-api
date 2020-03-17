package com.sandrocaseiro.template.utils

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeParseException

class DateUtilTest {
    @Test
    fun testStringToDateShouldReturnNullIfNotInformed() {
        assertThat(null.toDate()).isNull()
        assertThat("".toDate("")).isNull()
    }

    @Test
    fun testStringToDateShouldThrowIfInvalidFormat() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "202-02-27".toDate() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-2-27".toDate() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-2".toDate() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020/02/27".toDate() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27/02/2020".toDate() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27-02-2020".toDate() }
    }

    @Test
    fun testStringToDateShouldThrowIfInvalidDate() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-31".toDate() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27".toDate() }
    }

    @Test
    fun testStringToDateShouldReturnDate() {
        val date: LocalDate? = "2020-02-27".toDate()

        assertThat(date?.year).isEqualTo(2020)
        assertThat(date?.monthValue).isEqualTo(2)
        assertThat(date?.dayOfMonth).isEqualTo(27)
    }

    @Test
    fun testStringToDatePatternShouldReturnNullIfNotInformed() {
        val pattern = "uuuu-MM-dd"
        assertThat(null.toDate(pattern)).isNull()
        assertThat("".toDate(pattern)).isNull()
    }

    @Test
    fun testStringToDatePatternShouldThrowIfInvalidFormat() {
        val pattern = "uuuu-MM-dd"
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "202-02-27".toDate(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-2-27".toDate(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-2".toDate(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020/02/27".toDate(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27/02/2020".toDate(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27-02-2020".toDate(pattern) }
    }

    @Test
    fun testStringToDatePatternShouldThrowIfInvalidDate() {
        val pattern = "uuuu-MM-dd"
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-31".toDate(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27".toDate(pattern) }
    }

    @Test
    fun testStringToDatePatternShouldThrowIdInvalidPattern() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-27".toDate("yyyy-MM-dd") }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-27".toDate("dd-MM-uuuu") }
    }

    @Test
    fun testStringToDatePatternShouldReturnDate() {
        val date: LocalDate? = "2020-02-27".toDate("uuuu-MM-dd")

        assertThat(date?.year).isEqualTo(2020)
        assertThat(date?.monthValue).isEqualTo(2)
        assertThat(date?.dayOfMonth).isEqualTo(27)
    }

    @Test
    fun testStringToDateTimeShouldReturnNullIfNotInformed() {
        assertThat(null.toDateTime()).isNull()
        assertThat("".toDateTime()).isNull()
    }

    @Test
    fun testStringToDateTimeShouldThrowIfInvalidFormat() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020/02/27T10:43:0Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "202-02-27 10:43:00".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-27T1:43:00Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-2T10:4:00Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-27T10:43:0Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27-02-2020T10:43:00Z".toDateTime() }
    }

    @Test
    fun testStringToDateTimeShouldThrowIfInvalidDateTime() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-31T10:43:00Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27T10:43:00Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27T24:43:00Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27T10:60:00Z".toDateTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27-02-2020T10:43:60Z".toDateTime() }
    }

    @Test
    fun testStringToDateTimeShouldReturnDate() {
        val dateTime: LocalDateTime? = "2020-02-27T10:43:30Z".toDateTime()

        assertThat(dateTime?.year).isEqualTo(2020)
        assertThat(dateTime?.monthValue).isEqualTo(2)
        assertThat(dateTime?.dayOfMonth).isEqualTo(27)
        assertThat(dateTime?.hour).isEqualTo(10)
        assertThat(dateTime?.minute).isEqualTo(43)
        assertThat(dateTime?.second).isEqualTo(30)
    }

    @Test
    fun testStringToDateTimePatternShouldReturnNullIfNotInformed() {
        val pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'"
        assertThat(null.toDateTime(pattern)).isNull()
        assertThat("".toDateTime(pattern)).isNull()
    }

    @Test
    fun testStringToDateTimePatternShouldThrowIfInvalidFormat() {
        val pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'"
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020/02/27T10:43:0Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "202-02-27 10:43:00".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-27T1:43:00Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-2T10:4:00Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-27T10:43:0Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27-02-2020T10:43:00Z".toDateTime(pattern) }
    }

    @Test
    fun testStringToDateTimePatternShouldThrowIfInvalidDateTime() {
        val pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'"
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-31T10:43:00Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27T10:43:00Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27T24:43:00Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-13-27T10:60:00Z".toDateTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "27-02-2020T10:43:60Z".toDateTime(pattern) }
    }

    @Test
    fun testStringToDateTimePatternShouldThrowIdInvalidPattern() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-31T10:43:00Z".toDate("yyyy-MM-dd'T'HH:mm:ss'Z'") }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "2020-02-31T10:43:00Z".toDate("uuuu-MM-dd HH:mm:ss") }
    }

    @Test
    fun testStringToDateTimePatternShouldReturnDate() {
        val dateTime: LocalDateTime? = "2020-02-27T10:43:30Z".toDateTime("uuuu-MM-dd'T'HH:mm:ss'Z'")

        assertThat(dateTime?.year).isEqualTo(2020)
        assertThat(dateTime?.monthValue).isEqualTo(2)
        assertThat(dateTime?.dayOfMonth).isEqualTo(27)
        assertThat(dateTime?.hour).isEqualTo(10)
        assertThat(dateTime?.minute).isEqualTo(43)
        assertThat(dateTime?.second).isEqualTo(30)
    }

    @Test
    fun testStringToTimeShouldReturnNullIfNotInformed() {
        assertThat(null.toTime()).isNull()
        assertThat("".toTime()).isNull()
    }

    @Test
    fun testStringToTimeShouldThrowIfInvalidFormat() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "1043".toTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10 43".toTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10-43".toTime() }
    }

    @Test
    fun testStringToTimeShouldThrowIfInvalidTime() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "24:43".toTime() }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10:60".toTime() }
    }

    @Test
    fun testStringToTimeShouldReturnTime() {
        val time: LocalTime? = "10:43".toTime()

        assertThat(time?.hour).isEqualTo(10)
        assertThat(time?.minute).isEqualTo(43)
    }

    @Test
    fun testStringToTimePatternShouldReturnNullIfNotInformed() {
        val pattern = "HH:mm"
        assertThat(null.toTime(pattern)).isNull()
        assertThat("".toTime(pattern)).isNull()
    }

    @Test
    fun testStringToTimePatternShouldThrowIfInvalidFormat() {
        val pattern = "HH:mm"
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "1043".toTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10 43".toTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10-43".toTime(pattern) }
    }

    @Test
    fun testStringToTimePatternShouldThrowIfInvalidTime() {
        val pattern = "HH:mm"
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "24:43".toTime(pattern) }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10:60".toTime(pattern) }
    }

    @Test
    fun testStringToTimePatternShouldThrowIdInvalidPattern() {
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10:43".toTime("HH mm") }
        assertThatExceptionOfType(DateTimeParseException::class.java).isThrownBy { "10:43".toTime("hh:MM") }
    }

    @Test
    fun testStringToTimePatternShouldReturnTime() {
        val time: LocalTime? = "10:43".toTime("HH:mm")

        assertThat(time?.hour).isEqualTo(10)
        assertThat(time?.minute).isEqualTo(43)
    }

    @Test
    fun testDateToStringShouldReturnNullIfNotInformed() {
        assertThat((null as LocalDate?).toDateString()).isNull()
    }

    @Test
    fun testDateToStringShouldReturnFormattedDate() {
        assertThat(LocalDate.of(2020, 2, 27)).isNotNull().isEqualTo("2020-02-27")
    }

    @Test
    fun testDateToStringPatternShouldReturnNullIfNotInformed() {
        val pattern = "uuuu-MM-dd"
        assertThat((null as LocalDate?).toDateString(pattern)).isNull()
    }

    @Test
    fun testDateToStringPatternShouldThrowIdInvalidPattern() {
        val date = LocalDate.of(2020, 2, 27)
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { date.toDateString("uuuu-mm-aa") }
    }

    @Test
    fun testDateToStringPatternShouldReturnFormattedDate() {
        val date = LocalDate.of(2020, 2, 27)
        val pattern = "uuuu-MM-dd"
        assertThat(date.toDateString(pattern)).isNotNull().isEqualTo("2020-02-27")
    }

    @Test
    fun testDateTimeToStringShouldReturnNullIfNotInformed() {
        assertThat((null as LocalDateTime?).toDateTimeString()).isNull()
    }

    @Test
    fun testDateTimeToStringShouldReturnFormattedDate() {
        assertThat(LocalDateTime.of(2020, 2, 27, 10, 43, 30).toDateTimeString()).isNotNull().isEqualTo("2020-02-27T10:43:30Z")
    }

    @Test
    fun testDateTimeToStringPatternShouldReturnNullIfNotInformed() {
        val pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'"
        assertThat((null as LocalDateTime?).toDateTimeString(pattern)).isNull()
    }

    @Test
    fun testDateTimeToStringPatternShouldThrowIdInvalidPattern() {
        val dateTime = LocalDateTime.of(2020, 2, 27, 10, 43, 30)
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { dateTime.toDateTimeString("uuuu-mm-aaTHH:mm:ss'Z'") }
    }

    @Test
    fun testDateTimeToStringPatternShouldReturnFormattedDate() {
        val dateTime = LocalDateTime.of(2020, 2, 27, 10, 43, 30)
        val pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'"
        assertThat(dateTime.toDateTimeString(pattern)).isNotNull().isEqualTo("2020-02-27T10:43:30Z")
    }

    @Test
    fun testTimeToStringShouldReturnNullIfNotInformed() {
        assertThat((null as LocalTime?).toTimeString()).isNull()
    }

    @Test
    fun testTimeToStringShouldReturnFormattedDate() {
        assertThat(LocalTime.of(10, 43).toTimeString()).isNotNull().isEqualTo("10:43")
    }

    @Test
    fun testTimeToStringPatternShouldReturnNullIfNotInformed() {
        val pattern = "HH:mm"
        assertThat((null as LocalTime?).toTimeString(pattern)).isNull()
    }

    @Test
    fun testTimeToStringPatternShouldThrowIdInvalidPattern() {
        val time = LocalTime.of(10, 43)
        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy { time.toTimeString("aa:mm") }
    }

    @Test
    fun testTimeToStringPatternShouldReturnFormattedDate() {
        val time = LocalTime.of(10, 43)
        val pattern = "HH:mm"
        assertThat(time.toTimeString(pattern)).isNotNull().isEqualTo("10:43")
    }
}
