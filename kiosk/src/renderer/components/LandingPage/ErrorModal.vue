<template>
  <v-card>
    <v-card-title class="orange darken-4 display-1">Woah there, something went wrong...</v-card-title>

    <v-card-text class="title text-xs-center">
      {{ errorMessage }} The following QR code will take you to the issue tracker.
      <br>
      <br>
      <img src="@/assets/RANCORIssueTracker.png">
      <!-- /home/turing/Dev/hockey-elo-tracker/kiosk/src/renderer/assets/RANCORIssueTracker.png -->
    </v-card-text>

    <v-card-actions>
      <v-btn v-if="!isFatalError" @click="close()">Close</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  data: () => {
    return {};
  },

  methods: {
    close() {
      this.$emit("closeErrorModal");
    }
  },

  props: {
    errorMessage: {
      type: String
    },
    isFatalError: {
      type: Boolean
    }
  }
};
</script>

<style>
</style>
