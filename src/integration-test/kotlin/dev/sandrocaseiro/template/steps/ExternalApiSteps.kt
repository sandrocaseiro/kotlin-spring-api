package dev.sandrocaseiro.template.steps

import com.github.tomakehurst.wiremock.client.WireMock.*
import io.cucumber.java8.En
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class ExternalApiSteps: BaseSteps(), En {
    init {
        Before { _ -> mockServer.reset() }

        Given("External API is not working") { stubNotWorking() }

        Given("External API is working") { stubIsWorking() }
    }

    fun stubNotWorking() {
        stubFor(
            any(urlPathMatching("/api/.*"))
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withFixedDelay(5000)
                )
        )
    }

    companion object {
        fun stubIsWorking() {
            stubFor(
                post(urlPathMatching("/api/.*/token$"))
                    .atPriority(1)
                    .withHeader(HttpHeaders.CONTENT_TYPE, containing(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withBodyFile("/auth-token.json")
                    )
            )

            stubFor(
                patch(urlPathMatching("/api/.*/1/balance$"))
                    .atPriority(1)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.NO_CONTENT.value())
                    )
            )

            stubFor(
                get(urlPathMatching("/api/.*/[1-4]$"))
                    .atPriority(1)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withBodyFile("/get-user.json")
                    )
            )

            stubFor(
                any(urlPathMatching("/api/.*"))
                    .atPriority(99)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.NOT_FOUND.value())
                    )
            )
        }
    }
}