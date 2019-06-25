package com.hockey.elo.elotracker.match.repository.models

import com.hockey.elo.elotracker.shared.models.GameType
import javax.persistence.*

@Entity
data class MatchRecord(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
        val gameType: GameType = GameType.BUBBLE_HOCKEY,
        val playerOneId: Long = 0L,
        val playerTwoId: Long = 0L,
        val playerOneScore: Int = 0,
        val playerTwoScore: Int = 0,
        val winnerId: Long = 0L
)
