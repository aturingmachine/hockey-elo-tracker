package com.hockey.elo.elotracker.match.model

import javax.validation.constraints.NotBlank

data class ScoreUpdateRequest(
        @NotBlank
        val playerOneScore: Int,

        @NotBlank
        val playerTwoScore: Int
)
