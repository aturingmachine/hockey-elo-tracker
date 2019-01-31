package com.hockey.elo.elotracker.match.model

data class UserMatchStats (
        val name: String,
        val elo: Int,
        val wins: Int,
        val losses: Int
)
