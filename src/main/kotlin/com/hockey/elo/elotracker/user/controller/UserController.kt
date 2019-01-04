package com.hockey.elo.elotracker.user.controller

import com.hockey.elo.elotracker.user.model.User
import com.hockey.elo.elotracker.user.model.UserSubmission
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

  @GetMapping("/api/v1/users")
  fun getAllUsers(): List<User> =
      userService.getAllUsers()

  @PostMapping("/api/v1/users")
  fun createNewUser(@RequestBody userSubmission: UserSubmission): User =
      userService.createNewUser(userSubmission)

}