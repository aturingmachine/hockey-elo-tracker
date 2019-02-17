package com.hockey.elo.elotracker.match.model

data class MatchSummary (
        val gameName: String,
        val playerOne: UserMatchStats,
        val playerTwo: UserMatchStats,
        val playerOneScore: Int,
        val playerTwoScore: Int,
        val winnerName: String
)

