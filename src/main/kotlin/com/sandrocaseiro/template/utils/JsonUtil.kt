package com.sandrocaseiro.template.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sandrocaseiro.template.serializers.LocalDateSerializer
import com.sandrocaseiro.template.serializers.LocalDateTimeSerializer
import com.sandrocaseiro.template.serializers.LocalTimeSerializer
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

private val LOGGER = LoggerFactory.getLogger(object{}::class.java.`package`.name)
private val MAPPER = jacksonObjectMapper()
    .registerModule(SimpleModule()
        .addSerializer(LocalDate::class.java, LocalDateSerializer())
        .addSerializer(LocalTime::class.java, LocalTimeSerializer())
        .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
    )

fun <T> T?.serialize(): String? {
    return if (this == null) null else try {
        MAPPER.writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        LOGGER.error("Json serialization error", e)
        null
    }
}

fun <T> T?.serializePrettyPrint(): String? {
    return if (this == null) null else try {
        MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        LOGGER.error("Json serialization error", e)
        null
    }
}
