package dev.sandrocaseiro.template.steps

import com.github.tomakehurst.wiremock.client.WireMock.*
import io.cucumber.java8.En
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class ExternalApiSteps: BaseSteps(), En {
    init {
        Before { _ -> mockServer.reset() }

        Given("CEP API is not working") { stubNotWorking() }

        Given("CEP API is working") { stubIsWorking() }
    }

    fun stubNotWorking() {
        stubFor(
            any(urlPathMatching("/cep/.*"))
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
                get(urlPathMatching("/cep/03310000/.*"))
                    .atPriority(1)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withBodyFile("/cep.json")
                    )
            )


            stubFor(
                get(urlPathMatching("/cep/99999999/.*"))
                    .atPriority(1)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withBodyFile("/cep-not-found.json")
                    )
            )

            stubFor(
                any(urlPathMatching("/cep/.*"))
                    .atPriority(99)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.NOT_FOUND.value())
                    )
            )
        }
    }
}