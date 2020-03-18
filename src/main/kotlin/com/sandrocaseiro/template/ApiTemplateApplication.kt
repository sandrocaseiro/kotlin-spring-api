package com.sandrocaseiro.template

import com.sandrocaseiro.template.properties.CorsProperties
import com.sandrocaseiro.template.properties.EndpointProperties
import com.sandrocaseiro.template.properties.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@EnableFeignClients
@EnableConfigurationProperties(
    CorsProperties::class,
    EndpointProperties::class,
    JwtProperties::class
)
class ApiTemplateApplication
    fun main(args: Array<String>) {
        runApplication<ApiTemplateApplication>(*args)
    }
