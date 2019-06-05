<template>
  <v-container class="pt-0" fluid>
    <v-layout row wrap align-content-center>
      <v-flex xs12 class="display-3 pb-2">{{ `Congratulations ${matchSummary.winnerName}!` }}</v-flex>
      <v-flex xs4>
        <v-card class="headline">
          <v-card-title class="display-1 orange darken-4">{{ playerOne.name }}</v-card-title>
          <v-card-text>
            <v-layout row wrap align-content-center>
              <v-flex xs4>
                Elo:
                <br>Wins:
                <br>Losses:
                <br>
              </v-flex>

              <v-flex xs4></v-flex>

              <v-flex xs4 class="text-xs-right">
                {{ matchSummary.playerOne.elo }}
                <br>
                {{ matchSummary.playerOne.wins }}
                <br>
                {{ matchSummary.playerOne.losses }}
                <br>
              </v-flex>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex xs4>
        <div class="text-xs-center">
          <v-btn @click="newGame()">New Game</v-btn>
        </div>
      </v-flex>
      <v-flex xs4>
        <v-card class="headline">
          <v-card-title class="display-1 orange darken-4">{{ playerTwo.name }}</v-card-title>
          <v-card-text>
            <v-layout row wrap align-content-center>
              <v-flex xs4>
                Elo:
                <br>Wins:
                <br>Losses:
                <br>
              </v-flex>

              <v-flex xs4></v-flex>

              <v-flex xs4 class="text-xs-right">
                {{ matchSummary.playerTwo.elo }}
                <br>
                {{ matchSummary.playerTwo.wins }}
                <br>
                {{ matchSummary.playerTwo.losses }}
                <br>
              </v-flex>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {};
  },

  props: {
    matchSummary: {
      type: Object
    },
    playerOne: {
      type: Object
    },
    playerTwo: {
      type: Object
    }
  },

  methods: {
    newGame() {
      //TODO
    }
  }
};
</script>

<style>
</style>
