package com.hockey.elo.elotracker.match.repository

import com.hockey.elo.elotracker.match.model.Match
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository: JpaRepository<Match, Long>