package com.hockey.elo.elotracker.match.service

import com.hockey.elo.elotracker.match.exception.MatchNotFound
import com.hockey.elo.elotracker.match.model.MatchRecord
import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.MatchUpdateRequest
import com.hockey.elo.elotracker.match.repository.MatchRepository
import com.hockey.elo.elotracker.statistics.EloRatings
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

  fun createMatch(matchCreationRequest: MatchCreationRequest): Long {
    val newMatch = MatchRecord()
    newMatch.playerOneId = matchCreationRequest.playerOne
    newMatch.playerTwoId = matchCreationRequest.playerTwo
    return matchRepository.save(newMatch).id
  }

  fun updateMatch(id: Long, matchUpdateRequest: MatchUpdateRequest) {
    if (matchRepository.findById(id).isPresent) {
      val match = matchRepository.findById(id).get()
      match.playerOneScore = matchUpdateRequest.playerOneScore
      match.playerTwoScore = matchUpdateRequest.playerTwoScore
      matchRepository.save(match)
    } else {
      throw MatchNotFound("MatchRecord Id: $id Not Found")
    }
  }

  fun completeMatch(id: Long, playerOneDidWin: Boolean) {
    if (matchRepository.findById(id).isPresent) {
      val match = matchRepository.findById(id).get()
      if (playerOneDidWin) {
        match.winner = match.playerOneId
        match.playerOneScore = EloRatings(match.playerOneScore).calcWinAgainst(match.playerTwoScore)
        match.playerTwoScore = EloRatings(match.playerTwoScore).calcLossAgainst(match.playerOneScore)
      } else {
        match.winner = match.playerTwoId
        match.playerOneScore = EloRatings(match.playerOneScore).calcLossAgainst(match.playerTwoScore)
        match.playerTwoScore = EloRatings(match.playerTwoScore).calcWinAgainst(match.playerOneScore)
      }
      matchRepository.save(match)
    } else {
      throw MatchNotFound("MatchRecord Id: $id Not Found")
    }
  }

}