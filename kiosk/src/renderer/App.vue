<template>
  <v-app dark>
    <v-card>
      <v-card-title
        @click="reset()"
        class="title orange darken-4 white--text font-weight-bold"
      >RĀNCŌR</v-card-title>
    </v-card>
    <landing-page></landing-page>

    <v-footer app>&nbsp; The Elo Tracking Application</v-footer>
  </v-app>
</template>

<script>
import LandingPage from "@/components/LandingPage";
import { ipcRenderer, ipcMain } from "electron";

export default {
  data: () => {
    return {
      name: "elo-kiosk",
      // readyToPlay: false,
      player1Id: "",
      player2Id: ""
    };
  },

  components: {
    LandingPage
  },

  methods: {
    //this is mostly here for devs
    reset() {
      location.reload();
    }
  }
};
</script>
