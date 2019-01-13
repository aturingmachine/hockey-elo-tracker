package com.hockey.elo.elotracker.match.model

import javax.persistence.*

@Entity
data class MatchRecord(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0L,
        var playerOneId: Long = 0L,
        var playerTwoId: Long = 0L,
        var playerOneScore: Int = 0,
        var playerTwoScore: Int = 0,
        var winnerId: Long = 0L
)
