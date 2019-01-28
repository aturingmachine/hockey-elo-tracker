package com.hockey.elo.elotracker.elohistory.repository

import com.hockey.elo.elotracker.elohistory.repository.models.EloHistoryRecord
import com.hockey.elo.elotracker.shared.models.GameType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EloHistoryRepository: JpaRepository<EloHistoryRecord, Long> {

  fun findAllByUserIdAndGameType(userId: Long, gameType: GameType): List<EloHistoryRecord>

}
