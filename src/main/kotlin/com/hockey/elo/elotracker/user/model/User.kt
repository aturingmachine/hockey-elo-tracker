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
        var elo: Long = 1200L,
        var wins: Long = 0L,
        var losses: Long = 0L
) {
    constructor() : this (0L, "", "", 1200L, 0L, 0L)
}