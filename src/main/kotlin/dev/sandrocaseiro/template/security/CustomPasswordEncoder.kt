package dev.sandrocaseiro.template.security

import org.springframework.security.crypto.password.PasswordEncoder

object CustomPasswordEncoder : PasswordEncoder {
    override fun encode(rawPassword: CharSequence): String = rawPassword.toString()

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean = encode(rawPassword) == encodedPassword
}
