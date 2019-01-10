package com.hockey.elo.elotracker.user.model

data class UserDTO(val user: User) {
    var id: Long = user.id
    var name: String = user.name
    var elo: Int = user.elo
    var wins: Int = user.wins
    var losses: Int = user.losses
}
