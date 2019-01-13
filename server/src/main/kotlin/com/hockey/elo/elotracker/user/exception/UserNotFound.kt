package com.hockey.elo.elotracker.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user.user-not-found")
open class UserNotFound(message: String?) : Throwable(message)
