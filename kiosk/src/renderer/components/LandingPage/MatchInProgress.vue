<template>
  <v-container fluid>
    <v-layout row wrap align-content-center>
      <v-flex xs4>
        <div class="display-2 text-xs-center">{{ playerOne.name }}</div>
        <div
          v-ripple
          class="orange darken-4 text-xs-center display-2 point-button"
          @click="increment(1)"
        >+</div>
        <div class="text-xs-center display-4">{{ scores.playerOne }}</div>
        <div
          v-ripple
          class="orange darken-4 text-xs-center display-2 point-button"
          @click="decrement(1)"
        >-</div>
      </v-flex>
      <v-flex xs4>
        <div class="display-2 text-xs-center">VS</div>
      </v-flex>
      <v-flex xs4>
        <div class="display-2 text-xs-center">{{ playerTwo.name }}</div>
        <div
          v-ripple
          class="orange darken-4 text-xs-center display-2 point-button"
          @click="increment(2)"
        >+</div>
        <div class="text-xs-center display-4">{{ scores.playerTwo }}</div>
        <div
          v-ripple
          class="orange darken-4 text-xs-center display-2 point-button"
          @click="decrement(2)"
        >-</div>
      </v-flex>
      <!-- <v-flex xs5></v-flex> -->
      <v-flex xs12 justify-center class="mt-4">
        <v-btn
          @click="completeMatch()"
          block
          class="mt-3 mb-0"
          color="orange darken-4"
          :disabled="shouldDisable()"
        >Finish Match</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {
      scores: {
        playerOne: 0,
        playerTwo: 0
      }
    };
  },

  props: {
    playerOne: {
      type: Object
    },
    playerTwo: {
      type: Object
    }
  },

  methods: {
    shouldDisable() {
      return (
        !Object.keys(this.scores).some(i => this.scores[i] > 0) ||
        this.scores.playerOne === this.scores.playerTwo
      );
    },

    completeMatch() {
      this.$emit("completeMatch", this.scores);
    },

    increment(player) {
      if (player === 1) {
        this.scores.playerOne++;
      } else {
        this.scores.playerTwo++;
      }
    },

    decrement(player) {
      if (player === 1) {
        if (this.scores.playerOne > 0) {
          this.scores.playerOne--;
        }
      } else {
        if (this.scores.playerTwo > 0) {
          this.scores.playerTwo--;
        }
      }
    }
  }
};
</script>

<style>
.point-button {
  user-select: none;
  cursor: pointer;
}
</style>
