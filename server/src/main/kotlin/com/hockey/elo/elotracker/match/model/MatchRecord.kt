package com.hockey.elo.elotracker.match.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class MatchRecord(
    @Id @GeneratedValue
    var id: Long = 0L,
    var playerOneId: Long = 0L,
    var playerTwoId: Long = 0L,
    var playerOneScore: Int = 0,
    var playerTwoScore: Int = 0,
    var winner: Long = 0L
)
