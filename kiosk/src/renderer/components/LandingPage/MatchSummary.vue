<template>
  <v-container class="pt-0" fluid>
    <v-layout row wrap align-content-center>
      <v-flex
        xs12
        class="display-2 pb-2 text-xs-center"
      >{{ `${matchSummary.winnerName} Won at ${gameType.split('_').map(i => i.slice(0,1).toUpperCase() + i.toLowerCase().slice(1, i.length)).join(' ') }!` }}</v-flex>
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
      <v-flex xs4></v-flex>
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
      <v-flex xs12>
        <div class="text-xs-center mt-4">
          <v-btn
            class="pt-3 pb-4"
            color="orange darken-4"
            block
            large
            :disabled="count < 5"
            :loading="count < 5"
            @click="newGame()"
          >
            New Game
            <template v-slot:loader>
              <v-progress-circular :value="count * 20"></v-progress-circular>
            </template>
          </v-btn>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { setInterval } from "timers";
export default {
  data: () => {
    return {
      count: 0
    };
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
    },
    gameType: {
      type: String
    }
  },

  watch: {
    count() {
      if (this.count > 120) {
        this.newGame();
      }
    }
  },

  methods: {
    newGame() {
      this.$emit("newGame");
    }
  },

  mounted() {
    setInterval(() => {
      this.count++;
    }, 1000);
  }
};
</script>

<style>
</style>
