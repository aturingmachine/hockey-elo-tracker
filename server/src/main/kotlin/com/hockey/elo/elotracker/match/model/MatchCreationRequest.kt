package com.hockey.elo.elotracker.match.model

data class MatchCreationRequest(
    val playerOne: Long,
    val playerTwo: Long
)