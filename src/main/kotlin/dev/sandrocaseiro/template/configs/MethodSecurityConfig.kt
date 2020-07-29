package dev.sandrocaseiro.template.configs

import dev.sandrocaseiro.template.handlers.CustomMethodSecurityExpressionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class MethodSecurityConfig : GlobalMethodSecurityConfiguration() {
    override fun createExpressionHandler(): MethodSecurityExpressionHandler = CustomMethodSecurityExpressionHandler()
}
