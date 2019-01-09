package com.hockey.elo.elotracker.statistics

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EloRatingsTest {

    private val player1Elo = 2400
    private val player2Elo = 2000

    @Test
    fun calcEloRating_Player1MatchesPlayer2() {
        val player1Ratings = EloRatings(player1Elo)

        assertThat(player1Ratings.calcWinAgainst(player2Elo)).isEqualTo(2403)
        assertThat(player1Ratings.calcLossAgainst(player2Elo)).isEqualTo(2371)
        assertThat(player1Ratings.calcTieAgainst(player2Elo)).isEqualTo(2387)
    }

    @Test
    fun calcEloRating_Player2MatchesPlayer1() {
        val player2Ratings = EloRatings(player2Elo)

        assertThat(player2Ratings.calcWinAgainst(player1Elo)).isEqualTo(2029)
        assertThat(player2Ratings.calcLossAgainst(player1Elo)).isEqualTo(1997)
        assertThat(player2Ratings.calcTieAgainst(player1Elo)).isEqualTo(2013)
    }
}