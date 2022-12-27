package org.example.filters

import ch.qos.logback.core.util.OptionHelper.isEmpty
import org.example.dao.UserDao
import org.example.util.JwtTokenUtil
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(val userDao: UserDao, val jwtTokenUtil: JwtTokenUtil) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // Get jwt token and validate

        // Get jwt token and validate
        val token = header.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].trim { it <= ' ' }
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        // Get user identity and set it on the spring security context

        // Get user identity and set it on the spring security context
        val userDetails: UserDetails? = userDao
            .loadUserByUsername(jwtTokenUtil.getUsername(token))

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null,
            if (userDetails == null) listOf() else userDetails.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}