package com.hockey.elo.elotracker.user.service

import com.hockey.elo.elotracker.statistics.EloRatings
import com.hockey.elo.elotracker.user.exception.UserAlreadyRegistered
import com.hockey.elo.elotracker.user.exception.UserNotFound
import com.hockey.elo.elotracker.user.model.UserRecord
import com.hockey.elo.elotracker.user.model.UserDTO
import com.hockey.elo.elotracker.user.model.UserLoginSubmission
import com.hockey.elo.elotracker.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

  fun retrieveAllUsers(): List<UserDTO> {
    val userEntityList = userRepository.findAll()
    return userEntityList.map { it -> UserDTO(it.id, it.name, it.elo, it.wins, it.losses) }
  }

  fun retrieveUser(id: Long): UserDTO {
    val userEntityOpt = userRepository.findById(id)
    if (userEntityOpt.isPresent) {
      val userEntity = userEntityOpt.get()
      return UserDTO(userEntity.id, userEntity.name, userEntity.elo, userEntity.wins,
              userEntity.losses)
    } else {
      throw UserNotFound("user.id-not-found")
    }
  }

  fun createNewUser(userLoginSubmission: UserLoginSubmission): UserDTO {
    userRepository.findByRfid(userLoginSubmission.rfid) ?: run {
      val userEntity = UserRecord()
      userEntity.name = userLoginSubmission.name
      userEntity.rfid = userLoginSubmission.rfid
      val savedUser = userRepository.save(userEntity)
      return UserDTO(savedUser.id, savedUser.name, savedUser.elo, savedUser.wins, savedUser.losses)
    }
    throw UserAlreadyRegistered("user.rfid-in-use")
  }

  fun updateUserStats(userId: Long, opponentId: Long, didUserWin: Boolean) {
    val optionalUserRecord = userRepository.findById(userId)
    val optionalOpponentRecord = userRepository.findById(opponentId)

    if (optionalUserRecord.isPresent && optionalOpponentRecord.isPresent) {
      val userRecord = optionalUserRecord.get()
      val opponentRecord = optionalOpponentRecord.get()

      if (didUserWin) {
        userRecord.elo = EloRatings(userRecord.elo).calcWinAgainst(opponentRecord.elo)
        userRecord.wins = userRecord.wins + 1
        opponentRecord.elo = EloRatings(opponentRecord.elo).calcLossAgainst(userRecord.elo)
        opponentRecord.losses = opponentRecord.losses + 1
      } else {
        opponentRecord.elo = EloRatings(opponentRecord.elo).calcWinAgainst(userRecord.elo)
        opponentRecord.wins = opponentRecord.wins + 1
        userRecord.elo = EloRatings(userRecord.elo).calcLossAgainst(opponentRecord.elo)
        userRecord.losses = userRecord.losses + 1
      }
      userRepository.save(userRecord)
      userRepository.save(opponentRecord)
    } else {
      throw UserNotFound("user.id-not-found")
    }
  }

}
