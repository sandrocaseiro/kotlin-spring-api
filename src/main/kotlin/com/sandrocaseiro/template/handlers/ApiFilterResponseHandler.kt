package com.sandrocaseiro.template.handlers

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.sandrocaseiro.template.exceptions.FilterBadRequestException
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.ServletRequestUtils
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
@Order(1)
class ApiFilterResponseHandler(private val mapper: ObjectMapper) : ResponseBodyAdvice<Any> {
    private val FILTER_PARAM = "\$filter";

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>)
        : Boolean {
        return returnType.parameterType != ResponseEntity::class.java
    }


    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
                                 selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest,
                                 response: ServerHttpResponse): Any? {
        val servletRequest: HttpServletRequest = (request as ServletServerHttpRequest).servletRequest
        val filter: String = ServletRequestUtils.getStringParameter(servletRequest, FILTER_PARAM, "")

        if (filter.isEmpty())
            return body

        mapper.addMixIn(Any::class.java, PropertyFilterMixin::class.java)

        val filterProvider = SimpleFilterProvider()
            .addFilter("PropertyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(filter.split(",").toMutableSet()));
        mapper.setFilterProvider(filterProvider)

        try {
            val json: String = mapper.writeValueAsString(body)

            return if (Collection::class.java.isAssignableFrom(body!!.javaClass) || body.javaClass.isArray)
                mapper.readValue(json, List::class.java)
            else
                mapper.readValue(json, Map::class.java)
        }
        catch (e: JsonProcessingException) {
            throw FilterBadRequestException(FILTER_PARAM, e);
        }
    }

    @JsonFilter("PropertyFilter")
    object PropertyFilterMixin {}
}
