package com.hockey.elo.elotracker.user.repository.models

import com.hockey.elo.elotracker.shared.models.GameType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UserStatsRecord(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
        var userId: Long = 0L,
        var gameType: GameType = GameType.BUBBLE_HOCKEY,
        var elo: Int = 1200,
        var wins: Int = 0,
        var losses: Int = 0
)
