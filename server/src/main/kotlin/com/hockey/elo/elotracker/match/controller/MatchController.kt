package com.hockey.elo.elotracker.match.controller

import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.MatchUpdateRequest
import com.hockey.elo.elotracker.match.service.MatchService
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class MatchController(
        private val matchService: MatchService,
        private val userService: UserService
) {

  @PostMapping("/api/v1/matches")
  fun createMatch(@RequestBody matchCreationRequest: MatchCreationRequest): Long =
          matchService.createMatch(matchCreationRequest)

  @PutMapping("/api/v1/matches/{id}")
  fun updateMatch(@PathVariable("id") id: Long,
                  @RequestBody matchUpdateRequest: MatchUpdateRequest) =
          matchService.updateMatch(id, matchUpdateRequest)

  @PutMapping("/api/v1/matches/{id}/{winnerId}")
  fun completeMatch(@PathVariable("id") id: Long,
                    @PathVariable("winnerId") winnerId: String) {
    val matchDTO = matchService.completeMatch(id, winnerId.toLong())
    userService.updateUserStats(
            userId = matchDTO.playerOneId,
            opponentId = matchDTO.playerTwoId,
            didUserWin = matchDTO.playerOneId == winnerId.toLong()
    )
  }

}
