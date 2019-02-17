<template>
  <div class="pt-4">
    <!-- <v-btn @click="reset()">Dev Reset </v-btn> -->
    <main>
      <v-content v-if="!selectedGame">
        <v-container fluid>
          <v-layout row wrap align-content-center>
            <v-flex xs4></v-flex>
            <v-flex xs4 class="title pb-3 text-xs-center">Choose A Game</v-flex>
            <v-flex xs4></v-flex>

            <game-card :game="'ping pong'" @startGame="startGame"></game-card>

            <v-flex xs2></v-flex>

            <game-card :game="'hockey'" @startGame="startGame"></game-card>
          </v-layout>
        </v-container>
      </v-content>

      <v-content v-if="selectedGame">
        <sign-in
          v-if="(!playerOne || !playerTwo) && !needsRegister"
          :player="playerOne ? 'two' : 'one'"
          @sign-in-started="readId"
        ></sign-in>
      </v-content>

      <v-content v-if="needsRegister">
        <v-container fluid>
          <v-layout row wrap align-content-center>Needs Register
            <v-flex xs4>
              <v-text-field v-model="registeringName" label="Name"></v-text-field>
            </v-flex>

            <v-flex xs2></v-flex>

            <v-flex xs4>
              <v-btn @click="register()"> Register </v-btn>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
    </main>
  </div>
</template>

<script>
import { ipcRenderer } from "electron";
import { http } from "../config/http.js";

import GameCard from "./LandingPage/GameCard.vue";
import SignIn from "./LandingPage/SignIn.vue";

export default {
  data: () => {
    return {
      name: "landing-page",
      selectedGame: null,
      playerOne: null,
      playerTwo: null,
      needsRegister: false,
      registeringName: null
    };
  },

  components: {
    gameCard: GameCard,
    signIn: SignIn
  },

  methods: {
    reset() {
      this.selectedGame = null,
      this.playerOne = null,
      this.playerTwo = null,
      this.needsRegister = false,
      this.registeringNam = null
    },

    open(link) {
      require("electron").shell.openExternal(link);
    },

    startGame(gameType) {
      this.selectedGame = gameType;
      console.log(gameType);
    },

    readId() {
      ipcRenderer.send("read-sign-in");
    },

    //this endpoint needs to be updated
    signIn(id) {
      http
        .get(`/v1/users/${id}`)
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
          this.needsRegister = id;
        });
    },

    register() {
      http.post('/v1/users', {
        name: this.registeringName,
        rfid: this.needsRegister
      })
      .then(response => {
        console.log(response)
        console.log('registered')
        this.needsRegister = null
        this.registeringName = null
      })
      .catch(err => {
        console.log(err)
      })
    }
  },

  mounted() {
    //catch the sign in event and set the player ids to the response
    ipcRenderer.on("sign-in-read", (event, arg) => {
      console.log("hit renderer");
      if (this.playerOne) {
        console.log("setting two");
        this.playerTwo = arg + 2; //the concat is for testing in dev
        this.signIn(arg + 2);
      } else {
        console.log("setting one");
        this.playerOne = arg + 1;
        this.signIn(arg + 1);
      }
      console.log(arg);
    });
  }
};
</script>