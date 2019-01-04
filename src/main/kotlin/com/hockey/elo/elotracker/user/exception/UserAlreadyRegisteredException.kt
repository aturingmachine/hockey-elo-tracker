package com.hockey.elo.elotracker.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class UserAlreadyRegisteredException(message: String?) : Throwable(message)