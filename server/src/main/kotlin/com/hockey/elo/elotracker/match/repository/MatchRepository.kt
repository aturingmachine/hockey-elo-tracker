package com.hockey.elo.elotracker.match.repository

import com.hockey.elo.elotracker.match.repository.models.MatchRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository: JpaRepository<MatchRecord, Long>
