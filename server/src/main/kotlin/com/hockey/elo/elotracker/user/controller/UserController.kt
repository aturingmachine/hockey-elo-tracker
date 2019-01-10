package com.hockey.elo.elotracker.user.controller

import com.hockey.elo.elotracker.user.model.User
import com.hockey.elo.elotracker.user.model.UserDTO
import com.hockey.elo.elotracker.user.model.UserLoginSubmission
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
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
  fun createNewUser(@RequestBody userLoginSubmission: UserLoginSubmission): UserDTO =
      userService.createNewUser(userLoginSubmission)

}
