package dev.sandrocaseiro.template.configs

import dev.sandrocaseiro.template.filters.JWTAuthenticationFilter
import dev.sandrocaseiro.template.filters.JWTAuthorizationFilter
import dev.sandrocaseiro.template.filters.ServletErrorFilter
import dev.sandrocaseiro.template.properties.CorsProperties
import dev.sandrocaseiro.template.properties.JwtProperties
import dev.sandrocaseiro.template.security.TokenAuthProvider
import dev.sandrocaseiro.template.services.JwtAuthService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@EnableWebSecurity
class SecurityConfig(
    private val jwtProperties: JwtProperties,
    private val jwtAuthService: JwtAuthService,
    private val corsProperties: CorsProperties,
    private val tokenAuthProvider: TokenAuthProvider,
    private val errorFilter: ServletErrorFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers().frameOptions().sameOrigin()

        if (corsProperties.enabled)
            http.cors().and().authorizeRequests { it.requestMatchers(RequestMatcher { r -> CorsUtils.isPreFlightRequest(r) }).permitAll() }

        http.authorizeRequests {
                it.antMatchers(
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml**",
                    "/swagger-ui/**",
                    "/swagger-ui.html**",
                    "/_monitor/**",
                    "/h2-console/**"
                ).permitAll()
                .antMatchers("/v*/token/**").anonymous()
                .antMatchers(HttpMethod.POST, "/v?/users").permitAll()
                .antMatchers(HttpMethod.GET, "/v?/roles", "/v?/groups").permitAll()
                .antMatchers("/v1/**").authenticated()
                .antMatchers("/v2/**").authenticated()
                .anyRequest().denyAll()
            }
            .exceptionHandling {
                it.accessDeniedHandler(errorFilter)
                it.authenticationEntryPoint(errorFilter)
            }
            .addFilterBefore(errorFilter, CorsFilter::class.java)
            .addFilterAfter(jwtAuthenticationFilter(), CorsFilter::class.java)
            .addFilterAfter(jwtAuthorizationFilter(), CorsFilter::class.java)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(tokenAuthProvider)
    }

    @Bean
    @ConditionalOnProperty(value = ["cors.enabled"], havingValue = "true", matchIfMissing = false)
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowedMethods = corsProperties.allowedMethods
            allowedHeaders = corsProperties.allowedHeaders
            exposedHeaders = corsProperties.exposedHeaders
            allowedOrigins = corsProperties.allowedOrigins
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }

    private fun jwtAuthenticationFilter() =
        JWTAuthenticationFilter(authenticationManager(), jwtAuthService, jwtProperties).apply {
            setFilterProcessesUrl("/v1/token")
        }

    private fun jwtAuthorizationFilter() = JWTAuthorizationFilter(jwtAuthService, jwtProperties, authenticationManager())
}
