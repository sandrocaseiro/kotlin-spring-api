package dev.sandrocaseiro.template

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import dev.sandrocaseiro.template.steps.ExternalApiSteps
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class MockServer {
    @Autowired
    lateinit var env: Environment

    val mockServer = WireMockServer(
        options()
            .port(8089)
            .usingFilesUnderClasspath("mocks")
    ).apply {
        start()
        configureFor(this.port())
    }

    fun reset() = mockServer.resetMappings()

    @PostConstruct
    fun init() {
        if (!"true".equals(env.getProperty("isTest"))) {
            ExternalApiSteps.stubIsWorking()
        }
    }

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