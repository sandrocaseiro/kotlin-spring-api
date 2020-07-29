package dev.sandrocaseiro.template.configs

import dev.sandrocaseiro.template.exceptions.AppErrors
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.HeaderParameter
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.parameters.QueryParameter
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfig {
    private val SECURITY_SCHEME = "JWT"

    @Value("\${info.app.version}")
    private var apiVersion: String = ""

    @Value("\${info.app.name}")
    private var apiName: String = ""

    @Value("\${info.app.description}")
    private var apiDescription: String = ""

    @Bean
    fun api(): OpenAPI = OpenAPI()
        .info(apiInfo())
        .components(addComponents())
        .addSecurityItem(SecurityRequirement().addList(SECURITY_SCHEME))

    @Bean
    fun globalHeadersCustomiser() = OpenApiCustomiser { openApi: OpenAPI ->
        openApi.paths.values.flatMap { it.readOperations() }.forEach { operation ->
            operation.addParametersItem(
                HeaderParameter()
                    .name(HttpHeaders.ACCEPT_LANGUAGE)
                    .required(false)
                    .schema(
                        StringSchema()
                            ._default("en-US")
                            .addEnumItem("en-US")
                            .addEnumItem("pt-BR")
                    )
            )
            val pageableParam: Parameter? = operation.parameters.filter {
                it.schema != null && it.schema.`$ref` != null && it.schema.`$ref`.endsWith("DPageable")
            }.firstOrNull()
            if (pageableParam != null) {
                operation.parameters.remove(pageableParam)
                operation.addParametersItem(
                    QueryParameter()
                        .name("\$pageoffset")
                        .description("Current page number")
                        .example(1)
                )
                operation.addParametersItem(
                    QueryParameter()
                        .name("\$pagelimit")
                        .description("Result page size")
                        .example(10)
                )
                operation.addParametersItem(
                    QueryParameter()
                        .name("\$sort")
                        .description("Sorting fields\n\n* -[field] - field descending\n\n* +[field] - field ascending")
                        .example("-id,+name")
                )
            }
        }
        val errorSchema: Schema<*>? = openApi.components.schemas["Error"]
        if (errorSchema != null) {
            val codeSchema = errorSchema.properties["code"] as Schema<Any>
            val builder = StringBuilder(codeSchema.description)
                .append(System.lineSeparator()).append(System.lineSeparator())
                .append(System.lineSeparator()).append(System.lineSeparator())

            AppErrors.values().forEach {
                codeSchema.addEnumItemObject(it.code)
                builder
                    .append("* ")
                    .append(it.code)
                    .append(" - ")
                    .append(it.toString())
                    .append(System.lineSeparator()).append(System.lineSeparator())
            }
            codeSchema.description(builder.toString())
        }
    }

    private fun addComponents() = Components().addSecuritySchemes(SECURITY_SCHEME,
        SecurityScheme()
            .type(SecurityScheme.Type.OAUTH2)
            .`in`(SecurityScheme.In.HEADER)
            .bearerFormat("JWT")
            .flows(OAuthFlows()
                .password(OAuthFlow()
                    .tokenUrl("/api/v1/token")
                    .refreshUrl("/api/v1/token")
                )
            )
            .name(HttpHeaders.AUTHORIZATION)
    )

    private fun apiInfo() = Info()
        .title(apiName)
        .description(apiDescription)
        .version(apiVersion)
}
