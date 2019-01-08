package com.hockey.elo.elotracker.match.model

data class MatchUpdateRequest(
    val playerOneScore: Long,
    val playerTwoScore: Long
)