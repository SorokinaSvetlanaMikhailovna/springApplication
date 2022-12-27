package org.example.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.example.entity.User
import org.springframework.stereotype.Component
import java.lang.String.format
import java.util.*


@Component
class JwtTokenUtil {
    private val jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP"
    private val jwtIssuer = "svetlana.sorokina"

    fun generateAccessToken(user: User): String {
        return Jwts.builder()
            .setSubject(format("%s,%s", user.id, user.login))
            .setIssuer(jwtIssuer)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserId(token: String?): String? {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody()
        return claims.subject.split(",").get(0)
    }

    fun getUsername(token: String?): String? {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody()
        return claims.subject.split(",").get(1)
    }

    fun getExpirationDate(token: String?): Date? {
        val claims: Claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.expiration
    }

    fun validate(token: String?): Boolean {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true


    }
}