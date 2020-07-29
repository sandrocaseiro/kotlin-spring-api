package dev.sandrocaseiro.template.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import dev.sandrocaseiro.template.utils.toTimeString
import java.time.LocalTime

class LocalTimeSerializer : JsonSerializer<LocalTime>() {
    override fun serialize(value: LocalTime?, gen: JsonGenerator, serializers: SerializerProvider) {
        if (value == null)
            gen.writeString("")
        else
            gen.writeString(value.toTimeString())
    }
}
