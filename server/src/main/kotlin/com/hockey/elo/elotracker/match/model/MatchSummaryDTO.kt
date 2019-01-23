package com.hockey.elo.elotracker.match.model

data class MatchSummaryDTO (
        val gameName: String,
        val playerOne: UserMatchStatsDTO,
        val playerTwo: UserMatchStatsDTO,
        val playerOneScore: Int,
        val playerTwoScore: Int,
        val winnerName: String
)
