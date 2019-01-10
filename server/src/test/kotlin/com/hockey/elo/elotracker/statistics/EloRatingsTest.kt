package com.hockey.elo.elotracker.statistics

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EloRatingsTest {

    private val player1Elo = 2400
    private val player2Elo = 2000
    private val player1Ratings = EloRatings(player1Elo)
    private val player2Ratings = EloRatings(player2Elo)

    @Test
    fun `calcWinAgainst - Player1 Beats Player2`() {
        assertThat(player1Ratings.calcWinAgainst(player2Elo)).isEqualTo(2403)
    }

    @Test
    fun `calcLossAgainst - Player1 Loses To Player2`() {
        assertThat(player1Ratings.calcLossAgainst(player2Elo)).isEqualTo(2371)
    }

    @Test
    fun `calcTieAgainst - Player1 Ties Player2`() {
        assertThat(player1Ratings.calcTieAgainst(player2Elo)).isEqualTo(2387)
    }

    @Test
    fun `calcWinAgainst - Player2 Beats Player1`() {
        assertThat(player2Ratings.calcWinAgainst(player1Elo)).isEqualTo(2029)
    }

    @Test
    fun `calcLossAgainst - Player2 Loses To Player1`() {
        assertThat(player2Ratings.calcLossAgainst(player1Elo)).isEqualTo(1997)
    }

    @Test
    fun `calcTieAgainst - Player2 Ties Player1`() {
        assertThat(player2Ratings.calcTieAgainst(player1Elo)).isEqualTo(2013)
    }

    @Test
    fun `oddsAgainst - Player1 Against Player2 Odds Are High`() {
        assertThat(player1Ratings.oddsAgainst(player2Elo)).isEqualTo(0.908)
    }

    @Test
    fun `oddsAgainst - Player2 Against Player 1 Odds Are Low`() {
        assertThat(player2Ratings.oddsAgainst(player1Elo)).isEqualTo(0.085)
    }

}
