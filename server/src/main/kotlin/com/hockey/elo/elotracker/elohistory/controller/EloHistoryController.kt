package com.hockey.elo.elotracker.elohistory.controller

import com.hockey.elo.elotracker.elohistory.model.EloHistoryDTO
import com.hockey.elo.elotracker.elohistory.repository.models.EloHistoryRecord
import com.hockey.elo.elotracker.elohistory.service.EloHistoryService
import com.hockey.elo.elotracker.shared.models.GameType
import org.springframework.web.bind.annotation.*

@RestController
class EloHistoryController(private val eloHistoryService: EloHistoryService) {

  @GetMapping("/api/v1/users/{userId}/elo-history")
  fun retrieveEloHistoryForUser(
          @PathVariable("userId") userId: Long,
          @RequestParam("gameType") gameType: GameType): List<EloHistoryDTO> =
          eloHistoryService.retrieveEloHistoryFor(userId = userId, gameType = gameType)

}
