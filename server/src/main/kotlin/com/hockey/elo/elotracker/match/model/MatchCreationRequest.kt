package com.hockey.elo.elotracker.match.model

import com.hockey.elo.elotracker.shared.models.GameType
import javax.validation.constraints.NotBlank

data class MatchCreationRequest(
        @NotBlank
        val playerOneId: Long,

        @NotBlank
        val playerTwoId: Long,

        @NotBlank
        val gameType: GameType
)
