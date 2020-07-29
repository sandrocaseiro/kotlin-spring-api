package dev.sandrocaseiro.template.steps

import dev.sandrocaseiro.template.MockServer
import dev.sandrocaseiro.template.states.TestState
import dev.sandrocaseiro.template.ApiApplication
import io.cucumber.spring.CucumberContextConfiguration
import io.restassured.RestAssured
import io.restassured.config.EncoderConfig
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@CucumberContextConfiguration
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [
        ApiApplication::class,
        MockServer::class,
        TestState::class
    ]
)
abstract class BaseSteps {
    @LocalServerPort
    var port: Int = 0
    @Autowired
    lateinit var mockServer: MockServer
    @Autowired
    lateinit var state: TestState
    @Autowired
    lateinit var flyway: Flyway

    fun setupRestAssured() {
        RestAssured.config = RestAssuredConfig.config()
            .encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails().and().enablePrettyPrinting(true))
        RestAssured.port = port
    }

    fun rebuildDbData() {
        flyway.apply {
            clean()
            migrate()
        }
    }
}
