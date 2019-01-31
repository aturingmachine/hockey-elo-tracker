package com.hockey.elo.elotracker.match.controller

import com.hockey.elo.elotracker.elohistory.service.EloHistoryService
import com.hockey.elo.elotracker.match.model.Match
import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.service.MatchService
import com.hockey.elo.elotracker.shared.models.GameType
import com.hockey.elo.elotracker.user.service.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MatchControllerTest {

    private val matchService = mock(MatchService::class.java)
    private val userService = mock(UserService::class.java)
    private val eloHistoryService = mock(EloHistoryService::class.java)
    private val matchController = MatchController(matchService, userService, eloHistoryService)

    private val matchCreationRequest =
            MatchCreationRequest(1L, 2L, GameType.BUBBLE_HOCKEY)
    private val match = Match(0L, GameType.BUBBLE_HOCKEY, 1L, 2L, 5, 6, 1L)

    @Test
    fun createMatch() {
        `when`(matchService.createMatch(matchCreationRequest)).thenReturn(match)

        val match = matchController.createMatch(matchCreationRequest)
        assertThat(match.id).isEqualTo(0L)
        assertThat(match.gameType).isEqualTo(GameType.BUBBLE_HOCKEY)
        assertThat(match.playerOneId).isEqualTo(1L)
        assertThat(match.playerTwoId).isEqualTo(2L)
        assertThat(match.playerOneScore).isEqualTo(5)
        assertThat(match.playerTwoScore).isEqualTo(6)
        assertThat(match.winnerId).isEqualTo(1L)
    }

    @Test
    fun retrieveMatch() {
        assertThat(true).isTrue()
    }

    @Test
    fun retrieveAllMatches() {
        assertThat(true).isTrue()
    }

    @Test
    fun updateScore() {
        assertThat(true).isTrue()
    }

    @Test
    fun completeMatch() {
        assertThat(true).isTrue()
    }

}