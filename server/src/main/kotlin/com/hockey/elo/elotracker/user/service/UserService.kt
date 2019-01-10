package com.hockey.elo.elotracker.user.service

import com.hockey.elo.elotracker.user.exception.UserAlreadyRegistered
import com.hockey.elo.elotracker.user.exception.UserNotFound
import com.hockey.elo.elotracker.user.model.User
import com.hockey.elo.elotracker.user.model.UserDTO
import com.hockey.elo.elotracker.user.model.UserLoginSubmission
import com.hockey.elo.elotracker.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

  fun getAllUsers(): List<User> =
      userRepository.findAll()

  fun createNewUser(userLoginSubmission: UserLoginSubmission): UserDTO {
    userRepository.findByRfid(userLoginSubmission.rfid) ?: run {
      val user = User()
      user.name = userLoginSubmission.name
      user.rfid = userLoginSubmission.rfid
      return UserDTO(userRepository.save(user))
    }
    throw UserAlreadyRegistered("user.rfid-in-use")
  }

  fun getUser(id: Long): UserDTO {
    val user = userRepository.findById(id)
    if (user.isPresent) {
      return UserDTO(user.get())
    } else {
      throw UserNotFound("user.id-not-found")
    }
  }

}
