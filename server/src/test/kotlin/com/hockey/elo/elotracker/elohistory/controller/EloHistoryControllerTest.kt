package com.hockey.elo.elotracker.elohistory.controller

import com.hockey.elo.elotracker.elohistory.model.EloHistory
import com.hockey.elo.elotracker.elohistory.service.EloHistoryService
import com.hockey.elo.elotracker.shared.models.GameType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock

import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
class EloHistoryControllerTest {

    private val eloHistoryService = mock(EloHistoryService::class.java)
    private val eloHistoryController = EloHistoryController(eloHistoryService)

    @Test
    fun `retrieveEloHistoryForUser-Retrieves a history record successfully`() {
        val date = Date()
        Mockito.`when`(eloHistoryService.retrieveEloHistoryFor(1L, GameType.BUBBLE_HOCKEY))
                .thenReturn(listOf(EloHistory(0L, 1L, GameType.BUBBLE_HOCKEY, 1300, date)))

        val eloHistories = eloHistoryController.retrieveEloHistoryForUser(1L, GameType.BUBBLE_HOCKEY)
        assertThat(eloHistories).hasSize(1)
        val eloHistory = eloHistories[0]
        assertThat(eloHistory.id).isEqualTo(0L)
        assertThat(eloHistory.userId).isEqualTo(1L)
    }

}
