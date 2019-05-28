<template>
  <v-container fluid>
    <v-layout row wrap align-content-center>
      Please Sign In {{ player === 'one' ? 'Player One' : 'Player Two' }}
      <v-btn @click="signIn()">Start Dev Sign In</v-btn>
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