package com.hockey.elo.elotracker.match.controller

import com.hockey.elo.elotracker.match.model.MatchCreationRequest
import com.hockey.elo.elotracker.match.model.MatchUpdateRequest
import com.hockey.elo.elotracker.match.service.MatchService
import org.springframework.web.bind.annotation.*

@RestController
class MatchController(private val matchService: MatchService) {


  @PostMapping("/api/v1/matches")
  fun createMatch(@RequestBody matchCreationRequest: MatchCreationRequest): Long =
      matchService.createMatch(matchCreationRequest)

  @PutMapping("/api/v1/matches/{id}")
  fun updateMatch(@PathVariable("id") id : Long,
                  @RequestBody matchUpdateRequest: MatchUpdateRequest) =
      matchService.updateMatch(id, matchUpdateRequest)
}