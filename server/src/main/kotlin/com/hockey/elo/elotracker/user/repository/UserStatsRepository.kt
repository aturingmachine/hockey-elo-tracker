package com.hockey.elo.elotracker.user.repository

import com.hockey.elo.elotracker.shared.models.GameType
import com.hockey.elo.elotracker.user.repository.models.UserStatsRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStatsRepository : JpaRepository<UserStatsRecord, Long> {

  fun findByUserId(userId: Long): List<UserStatsRecord>

  fun findByUserIdAndGameType(userId: Long, gameType: GameType): UserStatsRecord?

}
