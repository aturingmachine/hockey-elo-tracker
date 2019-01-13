package com.hockey.elo.elotracker.match.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "match.not-found")
open class MatchNotFound(message: String?) : Throwable(message)
