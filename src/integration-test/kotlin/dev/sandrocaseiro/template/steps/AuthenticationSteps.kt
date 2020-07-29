package dev.sandrocaseiro.template.steps

import dev.sandrocaseiro.template.security.TokenAuthResponse
import dev.sandrocaseiro.template.security.TokenUser
import dev.sandrocaseiro.template.services.JwtAuthService
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class AuthenticationSteps: BaseSteps(), En {
    @Autowired
    lateinit var jwtAuthService: JwtAuthService

    init {
        Given("I am authenticated") {
            val user: TokenUser = jwtAuthService.loadUserByUsername("user1@mail.com") as TokenUser
            val tokenResp: TokenAuthResponse = jwtAuthService.generateTokenResponse(user)
            state.token = tokenResp.accessToken
        }

        Given("I am authenticated with {string}") { username: String ->
            val user: TokenUser = jwtAuthService.loadUserByUsername(username) as TokenUser
            val tokenResp: TokenAuthResponse = jwtAuthService.generateTokenResponse(user)
            state.token = tokenResp.accessToken
        }
    }
}
