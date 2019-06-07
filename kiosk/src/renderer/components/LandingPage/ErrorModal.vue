<template>
  <v-card>
    <v-card-title class="orange darken-4 display-1">Woah there, something went wrong...</v-card-title>

    <v-card-text class="headline">{{ errorMessage }}</v-card-text>

    <v-card-actions>
      <v-btn @click="close()">Close</v-btn>
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
    }
  }
};
</script>

<style>
</style>
