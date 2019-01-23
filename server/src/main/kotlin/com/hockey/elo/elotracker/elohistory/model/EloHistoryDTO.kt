package com.hockey.elo.elotracker.elohistory.model

import com.hockey.elo.elotracker.shared.models.GameType
import java.util.*

data class EloHistoryDTO(
        val id: Long,
        val userId: Long,
        val gameType: GameType,
        var elo: Int,
        var date: Date
)
