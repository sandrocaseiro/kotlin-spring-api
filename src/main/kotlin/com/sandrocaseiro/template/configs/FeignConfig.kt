package com.sandrocaseiro.template.configs

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sandrocaseiro.template.exceptions.APIException
import com.sandrocaseiro.template.models.AError
import feign.Response
import feign.codec.Decoder
import feign.codec.Encoder
import feign.codec.ErrorDecoder
import feign.form.FormEncoder
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException

@Configuration
class FeignConfig {
    @Bean
    fun feignEncoder(): Encoder = FormEncoder(JacksonEncoder())

    @Bean
    fun feignDecoder(): Decoder = JacksonDecoder(jacksonObjectMapper());

    @Bean
    fun apiErrorDecoder(): ErrorDecoder = APIErrorDecoder()

    private class APIErrorDecoder : ErrorDecoder {
        override fun decode(s: String, response: Response): APIException {
            val json = jacksonObjectMapper()
            return try {
                val error = json.readValue<AError>(response.body().asInputStream())

                APIException(error.errors.map { it.description }.firstOrNull() ?: "")
            } catch (e: IOException) {
                APIException(e);
            }
        }
    }
}
