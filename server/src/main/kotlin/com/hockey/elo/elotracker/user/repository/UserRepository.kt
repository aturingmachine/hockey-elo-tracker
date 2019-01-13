package com.hockey.elo.elotracker.user.repository

import com.hockey.elo.elotracker.user.model.UserRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserRecord, Long> {

  fun findByRfid(name: String): UserRecord?

}
