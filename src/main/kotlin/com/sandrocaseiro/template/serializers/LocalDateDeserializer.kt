package com.sandrocaseiro.template.serializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.sandrocaseiro.template.utils.toDate
import java.time.LocalDate

class LocalDateDeserializer : JsonDeserializer<LocalDate?>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDate? = p.valueAsString.toDate()
}
