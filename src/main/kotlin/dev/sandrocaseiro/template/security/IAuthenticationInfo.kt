package dev.sandrocaseiro.template.security

interface IAuthenticationInfo {
    fun isAuthenticated(): Boolean

    fun getId(): Int?
}
