package com.hockey.elo.elotracker.user.controller

import com.hockey.elo.elotracker.user.model.User
import com.hockey.elo.elotracker.user.model.UserDTO
import com.hockey.elo.elotracker.user.model.UserSubmission
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {

  @GetMapping("/api/v1/users")
  fun getAllUsers(): List<User> =
      userService.getAllUsers()

  @GetMapping("/api/v1/users/{id}")
  fun getUser(@PathVariable id: Long): UserDTO =
      userService.getUser(id)

  @PostMapping("/api/v1/users")
  fun createNewUser(@RequestBody userSubmission: UserSubmission): User =
      userService.createNewUser(userSubmission)

}