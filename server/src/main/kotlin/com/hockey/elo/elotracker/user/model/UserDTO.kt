package com.hockey.elo.elotracker.user.model

data class UserDTO(
    var id: Long,
    var name: String,
    var elo: Int,
    var wins: Int,
    var losses: Int
)
