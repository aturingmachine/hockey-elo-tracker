package com.hockey.elo.elotracker.match.model

import javax.validation.constraints.NotBlank

data class MatchCreationRequest(
        @NotBlank
        val playerOne: Long,

        @NotBlank
        val playerTwo: Long
)
