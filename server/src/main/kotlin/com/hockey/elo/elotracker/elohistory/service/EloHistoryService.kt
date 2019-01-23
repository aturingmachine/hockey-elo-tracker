package com.hockey.elo.elotracker.elohistory.service

import com.hockey.elo.elotracker.elohistory.model.EloHistoryDTO
import com.hockey.elo.elotracker.elohistory.repository.models.EloHistoryRecord
import com.hockey.elo.elotracker.elohistory.repository.EloHistoryRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class EloHistoryService(private val eloHistoryRepository: EloHistoryRepository) {

  //should return last 6 months of a user's elo history
  fun retrieveEloHistoryFor(userId: Long): List<EloHistoryDTO> {
    val records = eloHistoryRepository.findAllByUserId(userId)
    //probably a better way to do this but it is 5 AM and I have 4 hours of sleep
    val eloHistoryRecords = records.filter { record ->
      record.date.after(
          Date.from(ZonedDateTime.now().minusMonths(6).toInstant())
      )}

    return eloHistoryRecords.map { it ->
      EloHistoryDTO(it.id, it.userId, it.gameType, it.elo, it.date)
    }
  }

}
