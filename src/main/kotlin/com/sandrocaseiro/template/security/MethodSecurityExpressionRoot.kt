package com.sandrocaseiro.template.security

import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.core.Authentication

class MethodSecurityExpressionRoot(private val auth: Authentication)
    : SecurityExpressionRoot(auth), MethodSecurityExpressionOperations {
    private val principal: UserPrincipal = auth.principal as UserPrincipal

    fun isGroup(groupId: Int): Boolean {
        return principal.groupId == groupId;
    }

    fun canAccessUser(userId: Int): Boolean {
        //Example only
        return userId < 4;
    }

    fun hasRoles(): Boolean = principal.roles.isNotEmpty()

    override fun setReturnObject(returnObject: Any?) {
        //Method not needed
    }
    override fun getFilterObject(): Any = Any()
    override fun setFilterObject(filterObject: Any?) {
        //Method not needed
    }
    override fun getReturnObject(): Any = Any()
    override fun getThis(): Any = Any()
}