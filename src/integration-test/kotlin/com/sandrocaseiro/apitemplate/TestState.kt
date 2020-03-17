package com.sandrocaseiro.apitemplate

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sandrocaseiro.template.models.domain.EUser
import io.cucumber.spring.CucumberTestContext
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
class TestState {
    private val REQUEST = "request";
    private val RESPONSE = "response";
    private val TOKEN = "token";
    private val REQUEST_PAYLOAD = "request_payload";
    private val USER = "user";

    private val states = mutableMapOf<String, Any?>()

    fun <T> get(name: String): T? {
        return states.getOrDefault(name, null) as T?
    }

    fun <T> set(name: String, value: T) {
        states[name] = value
    }

    var request: RequestSpecification?
        get() = get(REQUEST)
        set(value) {
            if (!token.isNullOrEmpty())
                value?.header("Authorization", "Bearer $token")

            set(REQUEST, value)
        }

    var response: Response?
        get() = get(RESPONSE)
        set(value) = set(RESPONSE, value)

    var token: String?
        get() = get(TOKEN)
        set(value) = set(TOKEN, value)

    var requestPayload: JsonNode?
        get() = get(REQUEST_PAYLOAD)
        set(value) = set(REQUEST_PAYLOAD, value)

    fun setPayload(value: String) {
        requestPayload = jacksonObjectMapper().readTree(value)
    }

    var user: EUser?
        get() = get(USER)
        set(value) = set(USER, value)
}
