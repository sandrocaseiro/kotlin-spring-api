package com.sandrocaseiro.template.configs

import com.sandrocaseiro.template.security.UserPrincipal
import com.google.common.base.Predicate
import com.google.common.net.HttpHeaders.AUTHORIZATION
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.RequestHandler
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    private val SECURITY_TYPE = "JWT"

    @Value("info.app.version")
    private var apiVersion: String = ""
    @Value("info.app.name")
    private var apiName: String = ""
    @Value("info.app.description")
    private var apiDescription: String = ""

    @Bean
    fun api() =
        Docket(DocumentationType.SWAGGER_2)
            .ignoredParameterTypes(Errors::class.java, UserPrincipal::class.java)
        .select()
            .apis(controllers())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))

    private fun controllers(): Predicate<RequestHandler> =
        RequestHandlerSelectors
            .withClassAnnotation(RestController::class.java)

    private fun apiKey() = ApiKey(SECURITY_TYPE, AUTHORIZATION, "header")

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")

        return listOf(SecurityReference(SECURITY_TYPE, arrayOf(authorizationScope)))
    }

    private fun securityContext(): SecurityContext =
        SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()

    fun apiInfo(): ApiInfo =
        ApiInfoBuilder()
            .title(apiName)
            .description(apiDescription)
            .version(apiVersion)
            .build()
}
