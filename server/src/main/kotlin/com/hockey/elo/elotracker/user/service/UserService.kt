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

  fun createUser(userLoginSubmission: UserLoginSubmission): UserDTO {
    userRepository.findByRfid(userLoginSubmission.rfid) ?: run {
      val userRecord = UserRecord(
              name = userLoginSubmission.name,
              rfid = userLoginSubmission.rfid)
      val savedUser = userRepository.save(userRecord)
      return UserDTO(savedUser.id, savedUser.name)
    }
    throw UserAlreadyRegistered("user.rfid-in-use")
  }

  fun retrieveAllUsers(): List<UserDTO> {
    val userRecords = userRepository.findAll()
    val userDTOs = mutableListOf<UserDTO>()
    for (userRecord in userRecords) {
      val userStatsRecords = userStatsRepository.findByUserId(userRecord.id)
      val userStatsDTOs = mutableListOf<UserStatsDTO>()
      for (userStatRecord in userStatsRecords) {
        userStatsDTOs.add(convert(userStatRecord))
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
      for (userStatRecord in userStatsRecords) {
        userStatsDTOs.add(convert(userStatRecord))
      }
      return UserDTO(userRecord.id, userRecord.name, userRecord.email, userStatsDTOs)
    } else {
      throw UserNotFound("user.id-not-found")
    }
  }

  fun updateUserStats(userId: Long, opponentId: Long, gameType: GameType, didUserWin: Boolean): List<UserDTO> {
    val userStatsRecord = userStatsOrDefault(userId, gameType)
    val opponentStatsRecord = userStatsOrDefault(opponentId, gameType)
    if (didUserWin) {
      userStatsRecord.elo = EloRatings(userStatsRecord.elo).calcWinAgainst(opponentStatsRecord.elo)
      userStatsRecord.wins = userStatsRecord.wins + 1
      opponentStatsRecord.elo = EloRatings(opponentStatsRecord.elo).calcLossAgainst(userStatsRecord.elo)
      opponentStatsRecord.losses = opponentStatsRecord.losses + 1
    } else {
      userStatsRecord.elo = EloRatings(userStatsRecord.elo).calcLossAgainst(opponentStatsRecord.elo)
      userStatsRecord.losses = userStatsRecord.losses + 1
      opponentStatsRecord.elo = EloRatings(opponentStatsRecord.elo).calcWinAgainst(userStatsRecord.elo)
      opponentStatsRecord.wins = opponentStatsRecord.wins + 1
    }
    userStatsRepository.save(userStatsRecord)
    userStatsRepository.save(opponentStatsRecord)

    val userDTOs = mutableListOf<UserDTO>()
    userDTOs.add(retrieveUser(userId))
    userDTOs.add(retrieveUser(opponentId))
    return userDTOs
  }

  private fun convert(statsRecord: UserStatsRecord): UserStatsDTO {
    return UserStatsDTO(statsRecord.id, statsRecord.gameType,
            statsRecord.elo, statsRecord.wins, statsRecord.losses)
  }

  private fun userStatsOrDefault(userId: Long, gameType: GameType): UserStatsRecord {
    val userStatsRecord = UserStatsRecord(userId = userId, gameType = gameType)
    return userStatsRepository.findByUserIdAndGameType(userId, gameType) ?: userStatsRecord
  }

}
