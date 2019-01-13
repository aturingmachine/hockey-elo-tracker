package com.hockey.elo.elotracker.match.model

data class MatchDTO(
        var id: Long,
        var playerOneId: Long,
        var playerTwoId: Long,
        var playerOneScore: Int,
        var playerTwoScore: Int,
        var winnerId: Long
)
