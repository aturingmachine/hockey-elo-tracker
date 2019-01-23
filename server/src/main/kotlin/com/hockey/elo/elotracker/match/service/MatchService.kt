package com.hockey.elo.elotracker.match.service

import com.hockey.elo.elotracker.match.exception.MatchNotFound
import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.MatchDTO
import com.hockey.elo.elotracker.match.repository.models.MatchRecord
import com.hockey.elo.elotracker.match.model.ScoreUpdateRequest
import com.hockey.elo.elotracker.match.repository.MatchRepository
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

    fun createMatch(matchCreationRequest: MatchCreationRequest): MatchDTO {
        val newMatchRecord = MatchRecord(
                gameType = matchCreationRequest.gameType,
                playerOneId = matchCreationRequest.playerOneId,
                playerTwoId = matchCreationRequest.playerTwoId)
        val savedMatchRecord = matchRepository.save(newMatchRecord)
        return MatchDTO(savedMatchRecord.id,
                savedMatchRecord.gameType,
                savedMatchRecord.playerOneId, savedMatchRecord.playerTwoId,
                savedMatchRecord.playerOneScore, savedMatchRecord.playerTwoScore,
                savedMatchRecord.winnerId)
    }

    fun retrieveMatch(id: Long): MatchDTO {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            return MatchDTO(id, matchRecord.gameType,
                    matchRecord.playerOneId, matchRecord.playerTwoId,
                    matchRecord.playerOneScore, matchRecord.playerTwoScore,
                    matchRecord.winnerId)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

    fun retrieveAllMatches(): List<MatchDTO> {
        val matchEntityList = matchRepository.findAll()
        return matchEntityList.map { it ->
            MatchDTO(it.id, it.gameType, it.playerOneId, it.playerTwoId,
                    it.playerOneScore, it.playerTwoScore, it.winnerId)
        }
    }

    fun updateScore(id: Long, scoreUpdateRequest: ScoreUpdateRequest): MatchDTO {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            val updatedRecord = MatchRecord(
                    matchRecord.id,
                    matchRecord.gameType,
                    matchRecord.playerOneId, matchRecord.playerTwoId,
                    scoreUpdateRequest.playerOneScore, scoreUpdateRequest.playerTwoScore,
                    matchRecord.winnerId)
            val savedMatchRecord = matchRepository.save(updatedRecord)
            return MatchDTO(savedMatchRecord.id,
                    savedMatchRecord.gameType,
                    savedMatchRecord.playerOneId, savedMatchRecord.playerTwoId,
                    savedMatchRecord.playerOneScore, savedMatchRecord.playerTwoScore,
                    savedMatchRecord.winnerId)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

    fun completeMatch(id: Long, winnerId: Long): MatchDTO {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            val updatedRecord = MatchRecord(
                    matchRecord.id,
                    matchRecord.gameType,
                    matchRecord.playerOneId, matchRecord.playerTwoId,
                    matchRecord.playerOneScore, matchRecord.playerTwoScore,
                    winnerId)
            val updatedMatchRecord = matchRepository.save(updatedRecord)

            return MatchDTO(
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
