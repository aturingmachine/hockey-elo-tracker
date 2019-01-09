package com.hockey.elo.elotracker.user.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0L,
        var rfid: String = "",
        var name: String = "",
        var elo: Int = 1200,
        var wins: Int = 0,
        var losses: Int = 0
) {
    constructor() : this (0L, "", "", 1200, 0, 0)
}