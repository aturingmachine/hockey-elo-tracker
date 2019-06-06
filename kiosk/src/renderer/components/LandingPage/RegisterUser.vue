<template>
  <v-container fluid>
    <v-layout row wrap align-content-center>
      <v-flex xs12 class="display-1 text-xs-center">Seems like you're new here, whats your name?</v-flex>
      <v-flex xs12>
        <v-text-field v-model="registeringName" class="pt-5" box label="Name"></v-text-field>
      </v-flex>

      <v-flex xs12>
        <v-btn
          @click="register()"
          :disabled="shouldDisable()"
          block
          large
          class="pa-5"
          color="orange darken-4"
        >Register</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {
      registeringName: ""
    };
  },

  methods: {
    shouldDisable() {
      return this.registeringName.length < 3;
    },

    register() {
      this.$emit("register", this.registeringName);
      this.registeringName = "";
    }
  }
};
</script>