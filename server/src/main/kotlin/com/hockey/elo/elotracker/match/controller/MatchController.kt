package com.hockey.elo.elotracker.match.controller

import com.hockey.elo.elotracker.match.model.*
import com.hockey.elo.elotracker.match.service.MatchService
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
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
                    @PathVariable("winnerId") winnerId: String): MatchSummaryDTO {
    val matchDTO = matchService.completeMatch(id, winnerId.toLong())

    val didUserWin = matchDTO.playerOneId == winnerId.toLong()
    val userDTOs = userService.updateUserStats(
            userId = matchDTO.playerOneId,
            opponentId = matchDTO.playerTwoId,
            gameType = matchDTO.gameType,
            didUserWin = didUserWin
    )

    val userDTO = userDTOs.first { it -> it.id == matchDTO.playerOneId}
    val userStatsDTO = userDTO.stats.first { it -> it.gameType == matchDTO.gameType }
    val opponentDTO = userDTOs.first { it -> it.id == matchDTO.playerTwoId }
    val opponentStats = opponentDTO.stats.first { it -> it.gameType == matchDTO.gameType }
    val winnerName = if (didUserWin) userDTO.name else opponentDTO.name

    return MatchSummaryDTO(
            gameName = matchDTO.gameType.toString(),
            playerOne = UserMatchStatsDTO(userDTO.name,
                    userStatsDTO.elo, userStatsDTO.wins, userStatsDTO.losses),
            playerTwo = UserMatchStatsDTO(opponentDTO.name,
                    opponentStats.elo, opponentStats.wins, opponentStats.losses),
            playerOneScore = matchDTO.playerOneScore,
            playerTwoScore = matchDTO.playerTwoScore,
            winnerName = winnerName
    )
  }

}
