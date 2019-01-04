package com.hockey.elo.elotracker.match.service

import com.hockey.elo.elotracker.match.repository.MatchRepository
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

}