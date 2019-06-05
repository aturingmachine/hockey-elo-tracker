package com.hockey.elo.elotracker.user.controller

import com.hockey.elo.elotracker.user.model.User
import com.hockey.elo.elotracker.user.model.UserLoginRequest
import com.hockey.elo.elotracker.user.repository.models.UserRecord
import com.hockey.elo.elotracker.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
// @CrossOrigin(origins = ["http://localhost:8081", "http://localhost:9080"])
@RestController
class UserController(
    private val userService: UserService
) {
  
  @GetMapping("/api/v1/users")
  fun retrieveAllUsers(): List<User> =
      userService.retrieveAllUsers()

  @GetMapping("/api/v1/users/{id}")
  fun retrieveUser(@PathVariable id: Long): User =
      userService.retrieveUser(id)

  @GetMapping("/api/v1/users/login/{rfid}")
  fun loginUser(@PathVariable rfid: String): UserRecord =
          userService.loginUser(rfid)

  @PostMapping("/api/v1/users")
  fun createNewUser(@RequestBody userLoginRequest: UserLoginRequest): User =
      userService.createUser(userLoginRequest)

}
