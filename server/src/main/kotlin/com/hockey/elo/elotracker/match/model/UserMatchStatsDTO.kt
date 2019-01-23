package com.hockey.elo.elotracker.match.model

data class UserMatchStatsDTO (
        val name: String,
        val elo: Int,
        val wins: Int,
        val losses: Int
)