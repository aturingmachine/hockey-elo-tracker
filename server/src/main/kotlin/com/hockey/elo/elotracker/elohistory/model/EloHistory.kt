package com.hockey.elo.elotracker.elohistory.model

import java.util.*
import javax.persistence.Entity

@Entity
data class EloHistory(
    val userId: Long,
    var elo: Int,
    var date: Date
)