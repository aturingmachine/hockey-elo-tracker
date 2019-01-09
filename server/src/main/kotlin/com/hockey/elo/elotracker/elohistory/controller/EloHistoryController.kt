package com.hockey.elo.elotracker.elohistory.controller

import com.hockey.elo.elotracker.elohistory.model.EloHistory
import com.hockey.elo.elotracker.elohistory.service.EloHistoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class EloHistoryController(private val eloHistoryService: EloHistoryService) {

  //this is untested
  @GetMapping("/api/v1/elo-history/{userId}")
  fun getEloHistoryForUser(@PathVariable("userId") userId: Long) : List<EloHistory> =
      eloHistoryService.getEloHistoryForUser(userId)
}