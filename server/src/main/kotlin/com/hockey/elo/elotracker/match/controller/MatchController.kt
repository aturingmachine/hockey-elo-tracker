package com.hockey.elo.elotracker.match.controller

import com.hockey.elo.elotracker.elohistory.service.EloHistoryService
import com.hockey.elo.elotracker.match.model.*
import com.hockey.elo.elotracker.match.service.MatchService
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
class MatchController(
        private val matchService: MatchService,
        private val userService: UserService,
        private val eloHistoryService: EloHistoryService
) {

  @PostMapping("/api/v1/matches")
  fun createMatch(@RequestBody matchCreationRequest: MatchCreationRequest): Match =
          matchService.createMatch(matchCreationRequest)

  @GetMapping("/api/v1/matches/{id}")
  fun retrieveMatch(@PathVariable("id") id: Long): Match {
    return matchService.retrieveMatch(id)
  }

  @GetMapping("/api/v1/matches")
  fun retrieveAllMatches(): List<Match> {
    return matchService.retrieveAllMatches()
  }

  @PutMapping("/api/v1/matches/{id}/score")
  fun updateScore(@PathVariable("id") id: Long,
                  @RequestBody scoreUpdateRequest: ScoreUpdateRequest): Match =
          matchService.updateScore(id, scoreUpdateRequest)

  @PutMapping("/api/v1/matches/{id}/winner/{winnerId}")
  fun completeMatch(@PathVariable("id") id: Long,
                    @PathVariable("winnerId") winnerId: String): MatchSummary {
    val match = matchService.completeMatch(id, winnerId.toLong())

    val didUserWin = match.playerOneId == winnerId.toLong()
    val users = userService.updateUserStats(
            userId = match.playerOneId,
            opponentId = match.playerTwoId,
            gameType = match.gameType,
            didUserWin = didUserWin
    )

    val user = users.first { it.id == match.playerOneId}
    val userStats = user.stats.first { it.gameType == match.gameType }
    val opponent = users.first { it.id == match.playerTwoId }
    val opponentStats = opponent.stats.first { it.gameType == match.gameType }
    val winnerName = if (didUserWin) user.name else opponent.name

    eloHistoryService.createEloHistory(userId = user.id,
            gameType = match.gameType,
            elo= userStats.elo)

    eloHistoryService.createEloHistory(userId = opponent.id,
            gameType = match.gameType,
            elo= opponentStats.elo)

    return MatchSummary(
            gameName = match.gameType.toString(),
            playerOne = UserMatchStats(user.name,
                    userStats.elo, userStats.wins, userStats.losses),
            playerTwo = UserMatchStats(opponent.name,
                    opponentStats.elo, opponentStats.wins, opponentStats.losses),
            playerOneScore = match.playerOneScore,
            playerTwoScore = match.playerTwoScore,
            winnerName = winnerName
    )
  }

}
