package com.hockey.elo.elotracker.elohistory.repository

import com.hockey.elo.elotracker.elohistory.model.EloHistory
import org.springframework.data.jpa.repository.JpaRepository


interface EloHistoryRepository: JpaRepository<EloHistory, Long>