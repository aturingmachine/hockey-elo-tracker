<template>
  <v-container fluid>
    <v-layout row wrap align-content-center>
      <v-flex
        xs12
        class="text-xs-center display-2"
      >Please Scan Your Badge {{ player === 'one' ? 'Player One' : 'Player Two' }}</v-flex>
      <v-flex xs12>
        <v-btn @click="signIn()" block large>Start Dev Sign In</v-btn>
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
    player: {
      type: String
    }
  },
  methods: {
    signIn() {
      this.$emit("sign-in-started");
    }
  }
};
</script>