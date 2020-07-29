package dev.sandrocaseiro.template

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy

@Component
class MockServer {
    val mockServer = WireMockServer(
        options()
            .port(8089)
            .usingFilesUnderClasspath("mocks")
    ).apply {
        start()
        configureFor(this.port())
    }

    fun reset() = mockServer.resetMappings()

    @PreDestroy
    fun dispose() {
        mockServer.shutdownServer()
        while (mockServer.isRunning) {
            try {
                Thread.sleep(100L)
            }
            catch (e: InterruptedException) {}
        }
    }
}