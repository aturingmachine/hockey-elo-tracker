package com.hockey.elo.elotracker.match.controller

import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.MatchDTO
import com.hockey.elo.elotracker.match.model.ScoreUpdateRequest
import com.hockey.elo.elotracker.match.service.MatchService
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class MatchController(
        private val matchService: MatchService,
        private val userService: UserService
) {

  @PostMapping("/api/v1/matches")
  fun createMatch(@RequestBody matchCreationRequest: MatchCreationRequest): MatchDTO =
          matchService.createMatch(matchCreationRequest)

  @GetMapping("/api/v1/matches/{id}")
  fun retrieveMatch(@PathVariable("id") id: Long): MatchDTO {
    return matchService.retrieveMatch(id)
  }

  @GetMapping("/api/v1/matches")
  fun retrieveAllMatches(): List<MatchDTO> {
    return matchService.retrieveAllMatches()
  }

  @PutMapping("/api/v1/matches/{id}/score")
  fun updateScore(@PathVariable("id") id: Long,
                  @RequestBody scoreUpdateRequest: ScoreUpdateRequest): MatchDTO =
          matchService.updateScore(id, scoreUpdateRequest)

  @PutMapping("/api/v1/matches/{id}/winner/{winnerId}")
  fun completeMatch(@PathVariable("id") id: Long,
                    @PathVariable("winnerId") winnerId: String) {
    val matchDTO = matchService.completeMatch(id, winnerId.toLong())
    userService.updateUserStats(
            userId = matchDTO.playerOneId,
            opponentId = matchDTO.playerTwoId,
            gameType = matchDTO.gameType,
            didUserWin = matchDTO.playerOneId == winnerId.toLong()
    )
  }

}
