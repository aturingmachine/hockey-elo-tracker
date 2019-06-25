package com.hockey.elo.elotracker.configurations.security.user.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Users (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var username: String = "",
        var password: String = "",
        var enabled: Boolean = true
)
