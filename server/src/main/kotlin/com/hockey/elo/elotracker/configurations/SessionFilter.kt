package com.hockey.elo.elotracker.configurations

import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SessionFilter : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse
        chain.doFilter(req, res)

        removeHttpOnlyFlag(res)
    }

    private fun removeHttpOnlyFlag(res: HttpServletResponse) {
        val setCookieHeaderName = "set-cookie"
        var setCookieHeader = res.getHeader(setCookieHeaderName)

        if (setCookieHeader != null) {
            setCookieHeader = setCookieHeader.replace("; HttpOnly", "")
            res.setHeader(setCookieHeaderName, setCookieHeader)
        }
    }
}