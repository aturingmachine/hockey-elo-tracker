package com.hockey.elo.elotracker.match.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
open class MatchNotFoundException(message: String?) : Throwable(message)