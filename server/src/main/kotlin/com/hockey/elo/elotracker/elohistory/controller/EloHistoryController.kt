package com.hockey.elo.elotracker.elohistory.controller

import com.hockey.elo.elotracker.elohistory.model.EloHistory
import com.hockey.elo.elotracker.elohistory.service.EloHistoryService
import com.hockey.elo.elotracker.shared.models.GameType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:8081", "http://localhost:9080"])
class EloHistoryController(private val eloHistoryService: EloHistoryService) {

  @GetMapping("/api/v1/users/{userId}/elo-history")
  fun retrieveEloHistoryForUser(
          @PathVariable("userId") userId: Long,
          @RequestParam("gameType") gameType: GameType): List<EloHistory> =
          eloHistoryService.retrieveEloHistoryFor(userId = userId, gameType = gameType)

}
