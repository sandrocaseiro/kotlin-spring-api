package com.sandrocaseiro.template.services

import com.sandrocaseiro.template.models.domain.EUser
import com.sandrocaseiro.template.properties.JwtProperties
import com.sandrocaseiro.template.repositories.RoleRepository
import com.sandrocaseiro.template.repositories.UserRepository
import com.sandrocaseiro.template.security.TokenAuthResponse
import com.sandrocaseiro.template.security.TokenUser
import io.jsonwebtoken.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JwtAuthService(
    private val jwtProps: JwtProperties,
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository
) : UserDetailsService {
    private val TOKEN_REFRESH_HEADER_KEY = "refresh"

    fun generateTokenResponse(user: TokenUser): TokenAuthResponse {
        val roles: List<String> = user.roles.map { it.toString() }

        val expirationTime: Long = jwtProps.expiration
        val refreshExpirationTime: Long = jwtProps.refreshExpiration

        val token: String = generateBearerToken(user, roles, expirationTime, false)
        val tokenRefresh: String = generateBearerToken(user, roles, refreshExpirationTime, true)

        return TokenAuthResponse(
            "bearer",
            expirationTime,
            refreshExpirationTime,
            token,
            tokenRefresh)
    }

    private fun generateBearerToken(user: TokenUser, roles: List<String>, expirationTime: Long, isRefresh: Boolean): String {
        val expiration = LocalDateTime.now().plus(expirationTime, ChronoUnit.MILLIS);

        val tokenBuilder: JwtBuilder = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(user.username)
            .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, jwtProps.secret)
            .claim("userId", user.id)
            .claim("name", user.name)
            .claim("groupId", user.groupId)
            .claim("roles", roles.joinToString(","))

        if (isRefresh)
            tokenBuilder.setHeaderParam(TOKEN_REFRESH_HEADER_KEY, true);

        return tokenBuilder.compact();
    }

    fun parseBearerToken(token: String): TokenUser {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtProps.secret)
            .parseClaimsJws(token)
            .body;

        val userId = claims.get("userId", Integer::class.java)
        val name = claims.get("name", String::class.java)
        val email: String = claims.subject
        val groupId = claims.get("groupId", Integer::class.java)
        val roles: List<Int> = claims.get("roles", String::class.java).split(",")
            .map { it.toInt() }

        return TokenUser(userId.toInt(), name, email, "", groupId.toInt(), roles);
    }

    fun isRefreshToken(token: String): Boolean {
        val header: JwsHeader<out JwsHeader<*>> = Jwts.parser()
            .setSigningKey(jwtProps.secret)
            .parseClaimsJws(token)
            .header ?: return false

        return header.getOrDefault(TOKEN_REFRESH_HEADER_KEY, false) as Boolean;
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user: EUser = userRepository.findByUsernameActive(username) ?: throw UsernameNotFoundException(username);

        val roles: List<Int> = roleRepository.findAllByUserId(user.id).map { it.id }

        return TokenUser(
            user.id,
            user.name,
            username,
            user.password,
            user.groupId,
            roles
        );
    }
}
