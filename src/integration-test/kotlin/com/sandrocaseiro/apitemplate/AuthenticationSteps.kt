package com.sandrocaseiro.apitemplate

import com.sandrocaseiro.template.security.TokenAuthResponse
import com.sandrocaseiro.template.security.TokenUser
import com.sandrocaseiro.template.services.JwtAuthService
import io.cucumber.java8.En
import io.restassured.response.Response
import org.hamcrest.Matchers.hasKey

class AuthenticationSteps(
    private val state: TestState,
    private val jwtAuthService: JwtAuthService
): En {
    init {
        Given("I am authenticated") {
            val user = jwtAuthService.loadUserByUsername("user1@mail.com") as TokenUser
            val tokenResp: TokenAuthResponse = jwtAuthService.generateTokenResponse(user)

            state.token = tokenResp.accessToken
        }

        When("The client calls \\/v1\\/token endpoint with username: {word} and password: {word}") { username: String, password: String ->
            val response: Response = state.request!!
                .body("username=$username&password=$password")
                .`when`()
                .post("/v1/token")

            state.response = response
        }

        When("The client calls \\/v1\\/token endpoint") {
            val response: Response = state.request!!
                .`when`()
                .post("/v1/token")

            state.response = response
        }

        When("The client calls \\/v1\\/token endpoint with the payload") { payload: String ->
            val response: Response = state.request!!
                .body(payload)
                .`when`()
                .post("/v1/token")

            state.response = response
        }

        Then("Response returned bearer token") {
            state.response!!
                .then()
                .body("$", hasKey("accessToken"))
        }
    }
}
