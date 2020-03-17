package com.sandrocaseiro.template.handlers

import com.sandrocaseiro.template.security.MethodSecurityExpressionRoot
import org.aopalliance.intercept.MethodInvocation
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.core.Authentication

class MethodSecurityExpressionHandler : DefaultMethodSecurityExpressionHandler() {
    private val trustResolver = AuthenticationTrustResolverImpl()

    override fun createSecurityExpressionRoot(authentication: Authentication, invocation: MethodInvocation): MethodSecurityExpressionOperations {
        return MethodSecurityExpressionRoot(authentication).apply {
            setPermissionEvaluator(permissionEvaluator);
            setTrustResolver(trustResolver);
            setRoleHierarchy(roleHierarchy);
        }
    }
}
