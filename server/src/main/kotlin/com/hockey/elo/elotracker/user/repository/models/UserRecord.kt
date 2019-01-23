package com.hockey.elo.elotracker.user.repository.models

import javax.persistence.*

@Entity
data class UserRecord (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var rfid: String = "",
    var name: String = "",
    var email: String = ""
)
