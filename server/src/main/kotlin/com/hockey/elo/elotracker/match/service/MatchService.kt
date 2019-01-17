package com.hockey.elo.elotracker.match.service

import com.hockey.elo.elotracker.match.exception.MatchNotFound
import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.MatchDTO
import com.hockey.elo.elotracker.match.model.MatchRecord
import com.hockey.elo.elotracker.match.model.MatchUpdateRequest
import com.hockey.elo.elotracker.match.repository.MatchRepository
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

    fun createMatch(matchCreationRequest: MatchCreationRequest): Long {
        val newMatchRecord = MatchRecord()
        newMatchRecord.playerOneId = matchCreationRequest.playerOne
        newMatchRecord.playerTwoId = matchCreationRequest.playerTwo
        return matchRepository.save(newMatchRecord).id
    }

    fun updateMatch(id: Long, matchUpdateRequest: MatchUpdateRequest) {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            matchRecord.playerOneScore = matchUpdateRequest.playerOneScore
            matchRecord.playerTwoScore = matchUpdateRequest.playerTwoScore
            matchRepository.save(matchRecord)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

    fun completeMatch(id: Long, winnerId: Long): MatchDTO {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            matchRecord.winnerId = winnerId
            val updatedMatchRecord = matchRepository.save(matchRecord)

            return MatchDTO(
                    updatedMatchRecord.id,
                    updatedMatchRecord.playerOneId, updatedMatchRecord.playerTwoId,
                    updatedMatchRecord.playerOneScore, updatedMatchRecord.playerTwoScore,
                    updatedMatchRecord.winnerId)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

    fun retrieveMatch(id: Long): MatchDTO {
        if (matchRepository.findById(id).isPresent) {
            val matchRecord = matchRepository.findById(id).get()
            return MatchDTO(id, matchRecord.playerOneId, matchRecord.playerTwoId,
                    matchRecord.playerOneScore, matchRecord.playerTwoScore, matchRecord.winnerId)
        } else {
            throw MatchNotFound("MatchRecord Id: $id Not Found")
        }
    }

    fun retrieveAllMatches(): List<MatchDTO> {
        val matchEntityList = matchRepository.findAll()
        return matchEntityList.map { it ->
            MatchDTO(it.id, it.playerOneId, it.playerTwoId,
                    it.playerOneScore, it.playerTwoScore, it.winnerId)
        }
    }
}
