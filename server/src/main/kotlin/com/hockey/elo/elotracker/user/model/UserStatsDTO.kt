package com.hockey.elo.elotracker.user.model

import com.hockey.elo.elotracker.shared.models.GameType

data class UserStatsDTO(
        var id: Long,
        var gameType: GameType,
        var elo: Int,
        var wins: Int,
        var losses: Int
)
