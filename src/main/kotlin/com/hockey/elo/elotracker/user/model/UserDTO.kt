package com.hockey.elo.elotracker.user.model

data class UserDTO(val user: User) {
    var id: Long = user.id
    var name: String = user.name
    var elo: Long = user.elo
    var wins: Long = user.wins
    var losses: Long = user.losses
}