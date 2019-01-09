package com.hockey.elo.elotracker.match.model

data class MatchUpdateRequest(
    val playerOneScore: Int,
    val playerTwoScore: Int
)