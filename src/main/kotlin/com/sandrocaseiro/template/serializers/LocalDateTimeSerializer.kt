package com.sandrocaseiro.template.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.sandrocaseiro.template.utils.toDateTimeString
import java.time.LocalDateTime

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator, serializers: SerializerProvider) {
        if (value == null)
            gen.writeString("")
        else
            gen.writeString(value.toDateTimeString())
    }
}
