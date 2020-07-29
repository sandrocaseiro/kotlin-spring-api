package dev.sandrocaseiro.template

import dev.sandrocaseiro.template.configs.FeignConfig
import dev.sandrocaseiro.template.properties.CorsProperties
import dev.sandrocaseiro.template.properties.EndpointProperties
import dev.sandrocaseiro.template.properties.InfoProperties
import dev.sandrocaseiro.template.properties.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class])
@EnableFeignClients(defaultConfiguration = [FeignConfig::class])
@EnableConfigurationProperties(
    CorsProperties::class,
    EndpointProperties::class,
    JwtProperties::class,
    InfoProperties::class
)
class ApiApplication
    fun main(args: Array<String>) {
        runApplication<ApiApplication>(*args)
    }
