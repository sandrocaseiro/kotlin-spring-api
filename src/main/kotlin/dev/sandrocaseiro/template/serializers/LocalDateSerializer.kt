package dev.sandrocaseiro.template.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import dev.sandrocaseiro.template.utils.toDateString
import java.time.LocalDate

class LocalDateSerializer : JsonSerializer<LocalDate>() {
    override fun serialize(value: LocalDate?, gen: JsonGenerator, serializers: SerializerProvider) {
        if (value == null)
            gen.writeString("")
        else
            gen.writeString(value.toDateString())
    }
}
