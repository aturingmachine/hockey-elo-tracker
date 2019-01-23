package com.hockey.elo.elotracker.user.service

import com.hockey.elo.elotracker.statistics.EloRatings
import com.hockey.elo.elotracker.user.exception.UserAlreadyRegistered
import com.hockey.elo.elotracker.user.exception.UserNotFound
import com.hockey.elo.elotracker.shared.models.GameType
import com.hockey.elo.elotracker.user.repository.models.UserRecord
import com.hockey.elo.elotracker.user.model.UserDTO
import com.hockey.elo.elotracker.user.model.UserLoginSubmission
import com.hockey.elo.elotracker.user.model.UserStatsDTO
import com.hockey.elo.elotracker.user.repository.UserRepository
import com.hockey.elo.elotracker.user.repository.UserStatsRepository
import com.hockey.elo.elotracker.user.repository.models.UserStatsRecord
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val userStatsRepository: UserStatsRepository) {

  fun createNewUser(userLoginSubmission: UserLoginSubmission): UserDTO {
    userRepository.findByRfid(userLoginSubmission.rfid) ?: run {
      val userRecord = UserRecord()
      userRecord.name = userLoginSubmission.name
      userRecord.rfid = userLoginSubmission.rfid
      val savedUser = userRepository.save(userRecord)
      return UserDTO(savedUser.id, savedUser.name, savedUser.email)
    }
    throw UserAlreadyRegistered("user.rfid-in-use")
  }

  fun retrieveAllUsers(): List<UserDTO> {
    val userRecordList = userRepository.findAll()
    val userDTOs: MutableList<UserDTO> = mutableListOf()
    for (userRecord in userRecordList) {
      val userStatsRecords = userStatsRepository.findByUserId(userRecord.id)
      val userStatsDTOs: MutableList<UserStatsDTO> = mutableListOf()
      for (stat in userStatsRecords) {
        userStatsDTOs.add(convert(stat))
      }
      userDTOs.add(UserDTO(userRecord.id, userRecord.name, userRecord.email, userStatsDTOs))
    }
    return userDTOs
  }

  fun retrieveUser(id: Long): UserDTO {
    val userRecordOpt = userRepository.findById(id)
    if (userRecordOpt.isPresent) {
      val userRecord = userRecordOpt.get()
      val userStatsRecords = userStatsRepository.findByUserId(userRecord.id)
      val userStatsDTOs: MutableList<UserStatsDTO> = mutableListOf()
      for (stat in userStatsRecords) {
        userStatsDTOs.add(convert(stat))
      }
      return UserDTO(userRecord.id, userRecord.name, userRecord.email, userStatsDTOs)
    } else {
      throw UserNotFound("user.id-not-found")
    }
  }

  private fun convert(statsRecord: UserStatsRecord): UserStatsDTO {
    return UserStatsDTO(statsRecord.id, statsRecord.gameType,
            statsRecord.elo, statsRecord.wins, statsRecord.losses)
  }

  fun updateUserStats(userId: Long, opponentId: Long, gameType: GameType, didUserWin: Boolean) {
    var userStatsRecord = UserStatsRecord()
    var opponentStatsRecord = UserStatsRecord()

    userStatsRecord.userId = userId
    opponentStatsRecord.userId = opponentId

    val optionalUserStatsRecord = userStatsRepository.findByUserIdAndGameType(userId, gameType)
    val optionalOpponentStatsRecord = userStatsRepository.findByUserIdAndGameType(opponentId, gameType)
    if (optionalUserStatsRecord != null && optionalOpponentStatsRecord != null) {
      userStatsRecord = optionalUserStatsRecord
      opponentStatsRecord = optionalOpponentStatsRecord
    }

    if (didUserWin) {
      userStatsRecord.elo = EloRatings(userStatsRecord.elo).calcWinAgainst(opponentStatsRecord.elo)
      userStatsRecord.wins = userStatsRecord.wins + 1
      opponentStatsRecord.elo = EloRatings(opponentStatsRecord.elo).calcLossAgainst(userStatsRecord.elo)
      opponentStatsRecord.losses = opponentStatsRecord.losses + 1
    } else {
      opponentStatsRecord.elo = EloRatings(opponentStatsRecord.elo).calcWinAgainst(userStatsRecord.elo)
      opponentStatsRecord.wins = opponentStatsRecord.wins + 1
      userStatsRecord.elo = EloRatings(userStatsRecord.elo).calcLossAgainst(opponentStatsRecord.elo)
      userStatsRecord.losses = userStatsRecord.losses + 1
    }
    userStatsRepository.save(userStatsRecord)
    userStatsRepository.save(opponentStatsRecord)
  }

}
