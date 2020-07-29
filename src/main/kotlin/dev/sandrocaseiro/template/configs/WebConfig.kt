package dev.sandrocaseiro.template.configs

import com.fasterxml.jackson.databind.ObjectMapper
import dev.sandrocaseiro.template.handlers.LogHandlerInterceptor
import dev.sandrocaseiro.template.handlers.PageableRequestResolver
import dev.sandrocaseiro.template.services.LogService
import dev.sandrocaseiro.template.utils.MAPPER
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.charset.StandardCharsets

@Configuration
class WebConfig(
    private val logService: LogService
): WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(PageableRequestResolver())
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LogHandlerInterceptor(logService))
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.defaultContentType(MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
    }

    @Bean
    fun jsonMapper(): ObjectMapper = MAPPER
}
