package com.hockey.elo.elotracker.user.model

import javax.persistence.*

@Entity
data class UserRecord (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0L,
        var rfid: String = "",
        var name: String = "",
        var elo: Int = 1200,
        var wins: Int = 0,
        var losses: Int = 0
)
