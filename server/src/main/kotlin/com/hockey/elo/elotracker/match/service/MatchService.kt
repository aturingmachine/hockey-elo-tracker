package com.hockey.elo.elotracker.match.service

import com.hockey.elo.elotracker.match.exception.MatchNotFoundException
import com.hockey.elo.elotracker.match.model.Match
import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.MatchUpdateRequest
import com.hockey.elo.elotracker.match.repository.MatchRepository
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {


  fun createMatch(matchCreationRequest: MatchCreationRequest): Long {
    val newMatch = Match()
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
      throw MatchNotFoundException("Match Id: $id Not Found")
    }

  }

  fun completeMatch(id: Long, playerOneWin: Boolean) {
    if (matchRepository.findById(id).isPresent) {
      val match = matchRepository.findById(id).get()
      if (playerOneWin) match.winner = match.playerOneId else match.winner = match.playerTwoId
      matchRepository.save(match)
      //calculate new elos using class from ISSUE #8
    } else {
      throw MatchNotFoundException("Match Id: $id Not Found")
    }
  }

}