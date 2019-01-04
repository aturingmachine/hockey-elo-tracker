package com.hockey.elo.elotracker.match.controller

import com.hockey.elo.elotracker.match.service.MatchService
import org.springframework.web.bind.annotation.RestController

@RestController
class MatchController(private val matchService: MatchService) {

}