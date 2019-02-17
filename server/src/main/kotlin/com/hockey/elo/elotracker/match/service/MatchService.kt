package com.hockey.elo.elotracker.match.service

import com.hockey.elo.elotracker.match.exception.MatchNotFound
import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.Match
import com.hockey.elo.elotracker.match.repository.models.MatchRecord
import com.hockey.elo.elotracker.match.model.ScoreUpdateRequest
import com.hockey.elo.elotracker.match.repository.MatchRepository
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

    fun createMatch(matchCreationRequest: MatchCreationRequest): Match {
        val newMatchRecord = MatchRecord(
                gameType = matchCreationRequest.gameType,
                playerOneId = matchCreationRequest.playerOneId,
                playerTwoId = matchCreationRequest.playerTwoId)
        val savedMatchRecord = matchRepository.save(newMatchRecord)
        return Match(savedMatchRecord.id,
                savedMatchRecord.gameType,
                savedMatchRecord.playerOneId, savedMatchRecord.playerTwoId,
                savedMatchRecord.playerOneScore, savedMatchRecord.playerTwoScore,
                savedMatchRecord.winnerId)
    }

    fun retrieveMatch(id: Long): Match {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            return Match(id, matchRecord.gameType,
                    matchRecord.playerOneId, matchRecord.playerTwoId,
                    matchRecord.playerOneScore, matchRecord.playerTwoScore,
                    matchRecord.winnerId)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

    fun retrieveAllMatches(): List<Match> {
        val matchEntityList = matchRepository.findAll()
        return matchEntityList.map {
            Match(it.id, it.gameType, it.playerOneId, it.playerTwoId,
                    it.playerOneScore, it.playerTwoScore, it.winnerId)
        }
    }

    fun updateScore(id: Long, scoreUpdateRequest: ScoreUpdateRequest): Match {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            val updatedRecord = MatchRecord(
                    matchRecord.id,
                    matchRecord.gameType,
                    matchRecord.playerOneId, matchRecord.playerTwoId,
                    scoreUpdateRequest.playerOneScore, scoreUpdateRequest.playerTwoScore,
                    matchRecord.winnerId)
            val savedMatchRecord = matchRepository.save(updatedRecord)
            return Match(savedMatchRecord.id,
                    savedMatchRecord.gameType,
                    savedMatchRecord.playerOneId, savedMatchRecord.playerTwoId,
                    savedMatchRecord.playerOneScore, savedMatchRecord.playerTwoScore,
                    savedMatchRecord.winnerId)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

    fun completeMatch(id: Long, winnerId: Long): Match {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            val updatedRecord = MatchRecord(
                    matchRecord.id,
                    matchRecord.gameType,
                    matchRecord.playerOneId, matchRecord.playerTwoId,
                    matchRecord.playerOneScore, matchRecord.playerTwoScore,
                    winnerId)
            val updatedMatchRecord = matchRepository.save(updatedRecord)

            return Match(
                    updatedMatchRecord.id,
                    updatedMatchRecord.gameType,
                    updatedMatchRecord.playerOneId, updatedMatchRecord.playerTwoId,
                    updatedMatchRecord.playerOneScore, updatedMatchRecord.playerTwoScore,
                    updatedMatchRecord.winnerId)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

}
