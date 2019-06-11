package com.hockey.elo.elotracker.elohistory.service

import com.hockey.elo.elotracker.elohistory.repository.EloHistoryRepository
import com.hockey.elo.elotracker.elohistory.repository.models.EloHistoryRecord
import com.hockey.elo.elotracker.shared.models.GameType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*

import java.util.*

class EloHistoryServiceTest {

    private val eloHistoryRepository = mock(EloHistoryRepository::class.java)
    private lateinit var eloHistoryService: EloHistoryService

    private val userId = 1L
    private val gameType = GameType.BUBBLE_HOCKEY
    private val elo = 1300

    private val eloHistoryRecords = listOf(EloHistoryRecord(1, userId, gameType, elo, Date()))

    @Before
    fun setUp() {
        eloHistoryService = EloHistoryService(eloHistoryRepository)
    }

    @Test
    fun `createEloHistory-EloHistoryRecord is saved without initialization`() {
        `when`(eloHistoryRepository.findAllByUserIdAndGameType(userId, gameType))
                .thenReturn(eloHistoryRecords)

        val date = Date()
        val eloHistoryRecord = EloHistoryRecord(0, userId, gameType, elo, date)
        `when`(eloHistoryRepository.save(any(EloHistoryRecord::class.java)))
                .thenReturn(eloHistoryRecord)

        val eloHistory = eloHistoryService.createEloHistory(userId, gameType, elo)
        assertThat(eloHistory.userId).isEqualTo(userId)
        assertThat(eloHistory.gameType).isEqualTo(gameType)
        assertThat(eloHistory.elo).isEqualTo(elo)
    }

    @Test
    fun `retrieveEloHistoryFor-EloHistoryRecord is converted to EloHistory`() {
        `when`(eloHistoryRepository.findAllByUserIdAndGameType(userId, gameType))
                .thenReturn(eloHistoryRecords)

        val eloHistories = eloHistoryService.retrieveEloHistoryFor(userId, gameType)
        assertThat(eloHistories).hasSize(1)
        val eloHistory = eloHistories[0]
        assertThat(eloHistory.userId).isEqualTo(userId)
        assertThat(eloHistory.gameType).isEqualTo(gameType)
        assertThat(eloHistory.elo).isEqualTo(elo)
    }

}
