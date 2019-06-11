package com.hockey.elo.elotracker.elohistory.repository.models

import com.hockey.elo.elotracker.shared.models.GameType
import java.util.*
import javax.persistence.*

@Entity
data class EloHistoryRecord(
     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     val id: Long,
     val userId: Long,
     val gameType: GameType,
     var elo: Int,
     var date: Date
)
