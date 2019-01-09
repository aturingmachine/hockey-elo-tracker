package com.hockey.elo.elotracker.match.repository

import com.hockey.elo.elotracker.match.model.MatchRecord
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository: JpaRepository<MatchRecord, Long>