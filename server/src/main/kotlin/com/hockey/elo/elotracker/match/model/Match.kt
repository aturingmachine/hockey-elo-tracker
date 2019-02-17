package com.hockey.elo.elotracker.match.model

import com.hockey.elo.elotracker.shared.models.GameType

data class Match(
        val id: Long,
        val gameType: GameType,
        val playerOneId: Long,
        val playerTwoId: Long,
        val playerOneScore: Int,
        val playerTwoScore: Int,
        val winnerId: Long
)
