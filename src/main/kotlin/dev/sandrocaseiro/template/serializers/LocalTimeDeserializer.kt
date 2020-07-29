package dev.sandrocaseiro.template.serializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import dev.sandrocaseiro.template.utils.toTime
import java.time.LocalTime

class LocalTimeDeserializer : JsonDeserializer<LocalTime?>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalTime? = p.valueAsString.toTime()
}
