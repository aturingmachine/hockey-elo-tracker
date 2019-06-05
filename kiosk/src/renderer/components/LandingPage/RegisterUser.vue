<template>
  <v-container fluid>
    <v-layout row wrap align-content-center>
      <v-flex xs4>
        <v-text-field v-model="registeringName" label="Name"></v-text-field>
      </v-flex>

      <v-flex xs2></v-flex>

      <v-flex xs4>
        <v-btn @click="register()">Register</v-btn>
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
    register() {
      this.$emit("register", this.registeringName);
      this.registeringName = "";
    }
  }
};
</script>