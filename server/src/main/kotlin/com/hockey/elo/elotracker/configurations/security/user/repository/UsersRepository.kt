package com.hockey.elo.elotracker.configurations.security.user.repository

import com.hockey.elo.elotracker.configurations.security.user.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: JpaRepository<Users, Long>
