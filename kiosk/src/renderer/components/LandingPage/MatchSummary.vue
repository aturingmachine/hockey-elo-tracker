<template>
  <v-container fluid>
    <v-layout row wrap align-content-center>
      <v-flex xs12 class="display-3">{{ `Congratulations ${matchSummary.winnerName}!` }}</v-flex>
      <v-flex xs12 class="display-1 mb-3">{{ `Match Summary:` }}</v-flex>
      <v-flex xs6>
        <span class="headline">{{ playerOne.name }}</span>
        <!-- <br>
        Name: {{ playerOne.name }}
        <br>-->
        <br>
        Elo: {{ matchSummary.playerOne.elo }}
        <br>
        Wins: {{ matchSummary.playerOne.wins }}
        <br>
        Losses: {{ matchSummary.playerOne.losses }}
        <br>
      </v-flex>
      <v-flex xs6>
        <span class="headline">{{ playerTwo.name }}</span>
        <!-- <br>
        Name: {{ playerTwo.name }}
        <br>-->
        <br>
        Elo: {{ matchSummary.playerTwo.elo }}
        <br>
        Wins: {{ matchSummary.playerTwo.wins }}
        <br>
        Losses: {{ matchSummary.playerTwo.losses }}
        <br>
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
  }
};
</script>

<style>
</style>
