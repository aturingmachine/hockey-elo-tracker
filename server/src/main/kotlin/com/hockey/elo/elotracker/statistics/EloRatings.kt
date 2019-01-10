package com.hockey.elo.elotracker.statistics

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class EloRatings(
        private val player1Elo: Int
) {

    private val kFactor = 32.0

    fun calcWinAgainst(player2Elo: Int): Int = calcRatingAgainst(player2Elo, actualScore = 1.0)

    fun calcLossAgainst(player2Elo: Int): Int = calcRatingAgainst(player2Elo, actualScore = 0.0)

    fun calcTieAgainst(player2Elo: Int): Int = calcRatingAgainst(player2Elo, actualScore = 0.5)

    fun oddsAgainst(player2Elo: Int): Double {
        val differenceInElo = player1Elo - player2Elo
        val probability =  1 - (1 / (1 + Math.exp(0.00583 * differenceInElo - 0.0505)))
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        return df.format(probability).toDouble()
    }

    private fun calcRatingAgainst(elo2: Int, actualScore: Double): Int {
        return player1Elo + (kFactor * (actualScore -
                expectedScore(transformedRating(player1Elo), transformedRating(elo2))))
                .roundToInt()
    }

    private fun transformedRating(currentElo: Int): Double =
            Math.pow(10.0, currentElo.toDouble() / 400.0)
    
    private fun expectedScore(player1: Double, player2: Double): Double =
            player1 / (player1 + player2)

}
