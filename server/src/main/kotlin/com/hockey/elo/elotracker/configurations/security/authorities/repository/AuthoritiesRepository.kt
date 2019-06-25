package com.hockey.elo.elotracker.configurations.security.authorities.repository

import com.hockey.elo.elotracker.configurations.security.authorities.model.Authorities
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthoritiesRepository: JpaRepository<Authorities, Long>
