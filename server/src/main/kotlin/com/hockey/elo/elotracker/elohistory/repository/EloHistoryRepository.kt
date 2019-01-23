package com.hockey.elo.elotracker.elohistory.repository

import com.hockey.elo.elotracker.elohistory.repository.models.EloHistoryRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EloHistoryRepository: JpaRepository<EloHistoryRecord, Long> {

  fun findAllByUserId(userId: Long): List<EloHistoryRecord>

}
