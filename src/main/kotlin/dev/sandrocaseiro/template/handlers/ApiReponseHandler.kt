package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.localization.IMessageSource
import dev.sandrocaseiro.template.models.DResponse
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice(value = ["dev.sandrocaseiro.template.controllers"])
@Order(2)
class ApiReponseHandler(private val messageSource: IMessageSource) : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>)
        : Boolean {
        return returnType.parameterType != ResponseEntity::class.java
    }

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
                                 selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest,
                                 response: ServerHttpResponse): DResponse<Any?> {
        val message: String = messageSource.getMessage(AppErrors.SUCCESS)
        return DResponse.ok(body, AppErrors.SUCCESS.code, message)
    }
}
