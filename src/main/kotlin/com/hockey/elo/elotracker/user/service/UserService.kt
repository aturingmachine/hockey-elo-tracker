package com.hockey.elo.elotracker.user.service

import com.hockey.elo.elotracker.user.exception.UserAlreadyRegisteredException
import com.hockey.elo.elotracker.user.exception.UserNotFoundException
import com.hockey.elo.elotracker.user.model.User
import com.hockey.elo.elotracker.user.model.UserDTO
import com.hockey.elo.elotracker.user.model.UserSubmission
import com.hockey.elo.elotracker.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

  fun getAllUsers(): List<User> =
      userRepository.findAll()

  fun createNewUser(userSubmission: UserSubmission): User {
    userRepository.findByRfid(userSubmission.rfid) ?: run {
      val user = User()
      user.name = userSubmission.name
      user.rfid = userSubmission.rfid
      return userRepository.save(user)
    }
    throw UserAlreadyRegisteredException("user.rfid-in-use")
  }

  fun getUser(id: Long): UserDTO {
    var user = userRepository.findById(id)

    if (user.isPresent) {
      return UserDTO(user.get())
    } else {
      throw UserNotFoundException("user.id-not-found")
    }
  }


}