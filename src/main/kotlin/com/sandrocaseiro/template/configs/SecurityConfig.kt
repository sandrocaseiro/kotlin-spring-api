package com.sandrocaseiro.template.configs

import com.sandrocaseiro.template.filters.JWTAuthenticationFilter
import com.sandrocaseiro.template.filters.JWTAuthorizationFilter
import com.sandrocaseiro.template.filters.ServletErrorFilter
import com.sandrocaseiro.template.properties.CorsProperties
import com.sandrocaseiro.template.properties.JwtProperties
import com.sandrocaseiro.template.security.TokenAuthProvider
import com.sandrocaseiro.template.services.JwtAuthService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
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
            .headers().frameOptions().sameOrigin();

        if (corsProperties.enabled)
            http.cors().and().authorizeRequests { it.requestMatchers(RequestMatcher { r -> CorsUtils.isPreFlightRequest(r) }).permitAll() }

        http.authorizeRequests {
                it.antMatchers(
                    "/v2/api-docs",
                    "/swagger-resources/**",
                    "/swagger-ui.html**",
                    "/webjars/**",
                    "/_monitor/**",
                    "/error",
                    "/h2-console/**").permitAll()
                .antMatchers("/v*/token/**").anonymous()
                .antMatchers(HttpMethod.POST, "/v*/users").permitAll()
                .anyRequest().authenticated()
            }
            .addFilterBefore(errorFilter, CorsFilter::class.java)
            .addFilterAfter(jwtAuthenticationFilter(), CorsFilter::class.java)
            .addFilterAfter(jwtAuthorizationFilter(), CorsFilter::class.java)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
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
