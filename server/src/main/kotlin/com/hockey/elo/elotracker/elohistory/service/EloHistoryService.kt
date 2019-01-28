package com.hockey.elo.elotracker.elohistory.service

import com.hockey.elo.elotracker.elohistory.model.EloHistoryDTO
import com.hockey.elo.elotracker.elohistory.repository.models.EloHistoryRecord
import com.hockey.elo.elotracker.elohistory.repository.EloHistoryRepository
import com.hockey.elo.elotracker.shared.models.GameType
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class EloHistoryService(private val eloHistoryRepository: EloHistoryRepository) {

  fun createEloHistory(userId: Long, gameType: GameType, elo: Int): EloHistoryDTO {
    initEloHistory(userId, gameType)
    val eloHistoryRecord = EloHistoryRecord(0, userId, gameType, elo, Date())
    val updatedEloHistoryRecord = eloHistoryRepository.save(eloHistoryRecord)
    return convert(updatedEloHistoryRecord)
  }

  fun retrieveEloHistoryFor(userId: Long, gameType: GameType): List<EloHistoryDTO> {
    val records = eloHistoryRepository.findAllByUserIdAndGameType(userId, gameType)
    val eloHistoryRecords = lastNumberOfMonths(6, records)
    return eloHistoryRecords.map { convert(it) }
  }

  private fun initEloHistory(userId: Long, gameType: GameType) {
    val userEloHistory = retrieveEloHistoryFor(userId, gameType)
    if (userEloHistory.isEmpty()) {
      val eloHistoryRecord = EloHistoryRecord(0, userId, gameType,1200, Date())
      eloHistoryRepository.save(eloHistoryRecord)
    }
  }

  private fun convert(record: EloHistoryRecord): EloHistoryDTO = EloHistoryDTO(record.id,
          record.userId, record.gameType, record.elo, record.date)

  private fun lastNumberOfMonths(months: Long, records: List<EloHistoryRecord>): List<EloHistoryRecord> =
    records.filter { record ->
      record.date.after(
              Date.from(ZonedDateTime.now().minusMonths(months).toInstant())
      )}
}
