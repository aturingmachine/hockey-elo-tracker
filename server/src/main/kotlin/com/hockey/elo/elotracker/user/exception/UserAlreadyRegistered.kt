package com.hockey.elo.elotracker.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "user.rfid-in-use")
open class UserAlreadyRegistered(message: String?) : RuntimeException(message)
