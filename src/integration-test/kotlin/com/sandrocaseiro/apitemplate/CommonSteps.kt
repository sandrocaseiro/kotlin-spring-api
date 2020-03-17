package com.sandrocaseiro.apitemplate

import com.sandrocaseiro.template.ApiTemplateApplication
import io.cucumber.java8.En
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.config.EncoderConfig
import io.restassured.config.LogConfig
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ApiTemplateApplication::class, TestState::class])
class CommonSteps(
    private val state: TestState,
    @LocalServerPort private val port: Int): En {
    init {
        Before { _ ->
            RestAssured.config = RestAssured.config()
                .encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails().and().enablePrettyPrinting(true))
            RestAssured.port = port
        }

        When("With content-type: {string}") { contentType: String ->
            val request: RequestSpecification = given()
                .contentType(contentType)
            state.request = request
        }

        Then("The client receives status code of {int}") { statusCode: Int ->
            state.response!!
                .then()
                .statusCode(statusCode)
        }

        Then("With payload:") { payload: String ->
            val request: RequestSpecification = state.request!!
                .body(payload)

            state.setPayload(payload)
            state.request = request
        }

        Then("The response returns error code {int}") { errorCode: Int ->
            state.response!!
                .then()
                .body("errors.code", hasItem(errorCode))
        }

        Then("The response has {int} errors") { qty: Int ->
            state.response!!
                .then()
                .body("errors.size()", `is`(qty))
        }

        Then("The response contains error for the {word} field") { field: String ->
            state.response!!
                .then()
                .body("errors.description", hasItem(containsString(field)))
        }
    }
}
