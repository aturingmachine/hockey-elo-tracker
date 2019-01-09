package com.hockey.elo.elotracker.statistics

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EloRatingsTest {

    private val player1Elo = 2400
    private val player2Elo = 2000
    private val player1Ratings = EloRatings(player1Elo)
    private val player2Ratings = EloRatings(player2Elo)

    @Test
    fun calcWinAgainst_Player1BeatsPlayer2() {
        assertThat(player1Ratings.calcWinAgainst(player2Elo)).isEqualTo(2403)
    }

    @Test
    fun calcLossAgainst_Player1LosesToPlayer2() {
        assertThat(player1Ratings.calcLossAgainst(player2Elo)).isEqualTo(2371)
    }

    @Test
    fun calcTieAgainst_Player1TiesPlayer2() {
        assertThat(player1Ratings.calcTieAgainst(player2Elo)).isEqualTo(2387)
    }

    @Test
    fun calcWinAgainst_Player2BeatsPlayer1() {
        assertThat(player2Ratings.calcWinAgainst(player1Elo)).isEqualTo(2029)
    }

    @Test
    fun calcLossAgainst_Player2LosesToPlayer1() {
        assertThat(player2Ratings.calcLossAgainst(player1Elo)).isEqualTo(1997)
    }

    @Test
    fun calcTieAgainst_Player2TiesPlayer1() {
        assertThat(player2Ratings.calcTieAgainst(player1Elo)).isEqualTo(2013)
    }
}
