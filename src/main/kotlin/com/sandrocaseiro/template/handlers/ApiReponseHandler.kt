package com.sandrocaseiro.template.handlers

import com.sandrocaseiro.template.exceptions.CustomErrors
import com.sandrocaseiro.template.models.DResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice
class ApiReponseHandler(private val messageSource: MessageSource) : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>)
        : Boolean {
        return returnType.parameterType != ResponseEntity::class.java
    }

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
                                 selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest,
                                 response: ServerHttpResponse): DResponse<Any?> {
        val message: String = messageSource.getMessage(CustomErrors.SUCCESS.messageRes, null, LocaleContextHolder.getLocale())
        return DResponse.ok(body, CustomErrors.SUCCESS.code, message)
    }
}
