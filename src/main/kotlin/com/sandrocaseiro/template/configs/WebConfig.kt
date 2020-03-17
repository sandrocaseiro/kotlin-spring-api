package com.sandrocaseiro.template.configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sandrocaseiro.template.handlers.PageableRequestResolver
import com.sandrocaseiro.template.serializers.*
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class WebConfig : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(PageableRequestResolver())
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    fun jsonMapper(): ObjectMapper =
        jacksonObjectMapper()
            .registerModule(SimpleModule()
                .addSerializer(LocalDate::class.java, LocalDateSerializer())
                .addSerializer(LocalTime::class.java, LocalTimeSerializer())
                .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
                .addDeserializer(LocalDate::class.java, LocalDateDeserializer())
                .addDeserializer(LocalTime::class.java, LocalTimeDeserializer())
                .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
            )
}
