package com.sandrocaseiro.template.serializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.sandrocaseiro.template.utils.toDateTime
import java.time.LocalDateTime

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime?>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDateTime? = p.valueAsString.toDateTime()
}
