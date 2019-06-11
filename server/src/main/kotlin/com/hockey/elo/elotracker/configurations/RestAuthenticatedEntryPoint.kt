package com.hockey.elo.elotracker.configurations

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RestAuthenticatedEntryPoint: AuthenticationEntryPoint {

    override fun commence(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authException: AuthenticationException?) {
        if (response == null) {
            return
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }

}