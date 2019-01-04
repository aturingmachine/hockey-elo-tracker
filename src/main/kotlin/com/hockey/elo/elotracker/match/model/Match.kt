package com.hockey.elo.elotracker.match.model

import com.hockey.elo.elotracker.user.model.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Match(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val playerOne: User,
    val playerTwo: User,
    var playerOneScore: Long = 0L,
    var playerTwoScore: Long = 0L
)