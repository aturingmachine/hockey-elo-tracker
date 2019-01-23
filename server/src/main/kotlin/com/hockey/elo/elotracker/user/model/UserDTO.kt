package com.hockey.elo.elotracker.user.model

data class UserDTO(
    var id: Long = 0L,
    var name: String = "",
    var email: String? = null,
    var stats: MutableList<UserStatsDTO> = mutableListOf()
)
