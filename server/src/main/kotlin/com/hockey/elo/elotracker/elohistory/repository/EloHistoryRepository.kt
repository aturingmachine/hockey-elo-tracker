package com.hockey.elo.elotracker.elohistory.repository

import com.hockey.elo.elotracker.elohistory.model.EloHistoryRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EloHistoryRepository: JpaRepository<EloHistoryRecord, Long>
