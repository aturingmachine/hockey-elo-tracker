<template>
  <v-container fluid>
    <v-layout row wrap align-content-center>
      <v-flex xs4>
        <!-- <v-text-field
          v-model="scores.playerOne"
          type="number"
          min="0"
          :label="`${playerOne.name}'s Score`"
        ></v-text-field>-->
        <div class="display-2 text-xs-center">{{ playerOne.name }}</div>
        <div class="orange darken-4 text-xs-center display-2" @click="increment(1)">+</div>
        <div class="text-xs-center display-4">{{ scores.playerOne }}</div>
        <div class="orange darken-4 text-xs-center display-2" @click="decrement(1)">-</div>
      </v-flex>
      <v-flex xs4>
        <div class="display-2 text-xs-center">VS</div>
        <!-- <div></div>
        <div>
          <v-btn @click="completeMatch()">Finish Match</v-btn>
        </div>
        <div></div>-->
      </v-flex>
      <v-flex xs4>
        <!-- <v-text-field
          v-model="scores.playerTwo"
          type="number"
          min="0"
          :label="`${playerTwo.name}'s Score`"
        ></v-text-field>-->
        <div class="display-2 text-xs-center">{{ playerTwo.name }}</div>
        <div class="orange darken-4 text-xs-center display-2" @click="increment(2)">+</div>
        <div class="text-xs-center display-4">{{ scores.playerTwo }}</div>
        <div class="orange darken-4 text-xs-center display-2" @click="decrement(2)">-</div>
      </v-flex>
      <v-flex xs5></v-flex>
      <v-flex xs4 justify-center>
        <v-btn @click="completeMatch()">Finish Match</v-btn>
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
</style>
