package com.sandrocaseiro.template.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationInfo : IAuthenticationInfo {
    override fun isAuthenticated(): Boolean = SecurityContextHolder.getContext().authentication.isAuthenticated

    override fun getId(): Int? {
        if (!isAuthenticated())
            return null;

        return (SecurityContextHolder.getContext().authentication.principal as UserPrincipal).id
    }
}
