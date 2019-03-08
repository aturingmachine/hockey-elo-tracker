package com.hockey.elo.elotracker.user.service

import com.hockey.elo.elotracker.statistics.EloRatings
import com.hockey.elo.elotracker.user.exception.UserAlreadyRegistered
import com.hockey.elo.elotracker.user.exception.UserNotFound
import com.hockey.elo.elotracker.shared.models.GameType
import com.hockey.elo.elotracker.user.repository.models.UserRecord
import com.hockey.elo.elotracker.user.model.User
import com.hockey.elo.elotracker.user.model.UserLoginRequest
import com.hockey.elo.elotracker.user.model.UserStats
import com.hockey.elo.elotracker.user.repository.UserRepository
import com.hockey.elo.elotracker.user.repository.UserStatsRepository
import com.hockey.elo.elotracker.user.repository.models.UserStatsRecord
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val userStatsRepository: UserStatsRepository) {

  fun createUser(userLoginRequest: UserLoginRequest): User {
    userRepository.findByRfid(userLoginRequest.rfid) ?: run {
      val userRecord = UserRecord(
              name = userLoginRequest.name,
              rfid = userLoginRequest.rfid)
      val savedUser = userRepository.save(userRecord)
      return User(savedUser.id, savedUser.name)
    }
    throw UserAlreadyRegistered("user.rfid-in-use")
  }

  fun loginUser(rfid: String): UserRecord {

      val record = userRepository.findByRfid(rfid)
      if (record != null) return record else throw UserNotFound("user.notFound")
  }

  fun retrieveAllUsers(): List<User> {
    val userRecords = userRepository.findAll()
    val users = mutableListOf<User>()
    for (userRecord in userRecords) {
      val userStatsRecords = userStatsRepository.findByUserId(userRecord.id)
      val userStats = mutableListOf<UserStats>()
      for (userStatRecord in userStatsRecords) {
        userStats.add(convert(userStatRecord))
      }
      users.add(User(userRecord.id, userRecord.name, userRecord.email, userStats))
    }
    return users
  }

  fun retrieveUser(id: Long): User {
    val userRecordOpt = userRepository.findById(id)
    if (userRecordOpt.isPresent) {
      val userRecord = userRecordOpt.get()
      val userStatsRecords = userStatsRepository.findByUserId(userRecord.id)
      val userStats: MutableList<UserStats> = mutableListOf()
      for (userStatRecord in userStatsRecords) {
        userStats.add(convert(userStatRecord))
      }
      return User(userRecord.id, userRecord.name, userRecord.email, userStats)
    } else {
      throw UserNotFound("user.id-not-found")
    }
  }

  fun updateUserStats(userId: Long, opponentId: Long, gameType: GameType, didUserWin: Boolean): List<User> {
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

    val users = mutableListOf<User>()
    users.add(retrieveUser(userId))
    users.add(retrieveUser(opponentId))
    return users
  }

  private fun convert(statsRecord: UserStatsRecord): UserStats {
    return UserStats(statsRecord.id, statsRecord.gameType,
            statsRecord.elo, statsRecord.wins, statsRecord.losses)
  }

  private fun userStatsOrDefault(userId: Long, gameType: GameType): UserStatsRecord {
    val userStatsRecord = UserStatsRecord(userId = userId, gameType = gameType)
    return userStatsRepository.findByUserIdAndGameType(userId, gameType) ?: userStatsRecord
  }

}
