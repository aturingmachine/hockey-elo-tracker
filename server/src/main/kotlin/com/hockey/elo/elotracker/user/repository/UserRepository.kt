package com.hockey.elo.elotracker.user.repository

import com.hockey.elo.elotracker.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

  fun findByRfid(name: String): User?

}
