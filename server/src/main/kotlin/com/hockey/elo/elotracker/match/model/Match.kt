package com.hockey.elo.elotracker.match.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Match(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var playerOneId: Long,
    var playerTwoId: Long,
    var playerOneScore: Long = 0L,
    var playerTwoScore: Long = 0L,
    var winner: Long //player Id
) {
    constructor() : this (0L, 0L, 0L, 0L, 0L, 0L)
}