package com.hockey.elo.elotracker.elohistory.model

import java.util.*
import javax.persistence.*

@Entity
data class EloHistoryRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val userId: Long,
    var elo: Int,
    var date: Date
)
