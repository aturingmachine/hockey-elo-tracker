<template>
  <div class="pt-4">
    <!-- <v-btn @click="reset()">Dev Reset </v-btn> -->
    <!-- <main> -->
      <v-content v-if="!selectedGame && !checkLoadingStates()">
        <v-container fluid>
          <v-layout row wrap align-content-center>
            <v-flex xs4></v-flex>
            <v-flex xs4 class="title pb-3 text-xs-center">Choose A Game</v-flex>
            <v-flex xs4></v-flex>

            <game-card :game="'ping pong'" @startGame="startGame"></game-card>

            <v-flex xs2></v-flex>

            <game-card :game="'bubble hockey'" @startGame="startGame"></game-card>
          </v-layout>
        </v-container>
      </v-content>

      <v-content v-if="selectedGame && !checkLoadingStates()">
        <sign-in
          v-if="(!playerOneRFID || !playerTwoRFID) && !needsRegister"
          :player="playerOneRFID ? 'two' : 'one'"
          @sign-in-started="readId"
        ></sign-in>
      </v-content>

      <v-content v-if="needsRegister && !checkLoadingStates()">
        <v-container fluid>
          <v-layout row wrap align-content-center>
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

      <v-content v-if="playerOne && playerTwo && !inProgressMatch && !checkLoadingStates()">
        <h2></h2>
        <v-btn @click="startMatch()">
          Start Match 
        </v-btn>
      </v-content>

      <v-content v-if="inProgressMatch && !matchSummary && !checkLoadingStates()">
        <v-container fluid>
          <v-layout row wrap align-center>
            <v-flex xs12>
        <h2 class="mb-2"> {{ `${playerOne.name} VS ${playerTwo.name}` }} </h2>
            </v-flex>
            <v-flex xs4>
        <v-text-field v-model="scores.playerOne" type="number" :label="`${playerOne.name}'s Score`">
        </v-text-field>
            </v-flex>
            <v-flex xs4>
        <v-btn @click="completeMatch()">
          Finish Match 
        </v-btn>
            </v-flex>
            <v-flex xs4>
        <v-text-field v-model="scores.playerTwo" type="number" :label="`${playerTwo.name}'s Score`">
        </v-text-field>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>

      <v-content v-if="matchSummary && !checkLoadingStates()">
        <v-container fluid>
          <v-layout row wrap align-content-center>
            <v-flex xs12 class="display-3">
              {{ `Congratulations ${matchSummary.winnerName}!` }}
            </v-flex>
            <v-flex xs12 class="display-1 mb-3">
              {{ `Match Summary:` }}
            </v-flex>
            <v-flex xs6>
              <span class="headline">Player One</span> <br>
              Name: {{ playerOne.name }} <br>
              Elo: {{ matchSummary.playerOne.elo }} <br>
              Wins: {{ matchSummary.playerOne.wins }} <br>
              Losses: {{ matchSummary.playerOne.losses }} <br>
            </v-flex>
            <v-flex xs6>
              <span class="headline">Player Two</span> <br>
              Name: {{ playerTwo.name }} <br>
              Elo: {{ matchSummary.playerTwo.elo }} <br>
              Wins: {{ matchSummary.playerTwo.wins }} <br>
              Losses: {{ matchSummary.playerTwo.losses }} <br>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>

      <v-content v-if="checkLoadingStates()">
        <h2 v-if="loadingStates.creatingMatch"> Creating Match... </h2>
        <h2 v-if="loadingStates.readingSignIn"> Signing In... </h2>
        <h2 v-if="loadingStates.registeringUser"> Registering... </h2>
        <v-progress-linear :indeterminate="true"></v-progress-linear>
      </v-content>
    <!-- </main> -->
  </div>
</template>

<script>
import { ipcRenderer } from "electron";
import { http } from "../config/http.js";
import GameCard from "./LandingPage/GameCard.vue";
import SignIn from "./LandingPage/SignIn.vue";

const SerialPort = require('serialport');
const Readline = require('@serialport/parser-readline')

export default {
  data: () => {
    return {
      name: "landing-page",
      selectedGame: null,
      playerOneRFID: null,
      playerTwoRFID: null,
      playerOne: null,
      playerTwo: null,
      playerOneElo: null,
      playerTwoElo: null,
      needsRegister: false,
      registeringName: null,
      inProgressMatch: null,
      matchSummary: null,
      scores: {
        playerOne: 0,
        playerTwo: 0
      },
      loadingStates: {
        creatingMatch: false,
        readingSignIn: false,
        registeringUser: false,
        completingMatch: false
      }
    };
  },

  watch: {
    // playerTwo() {
    //   this.fetchUserHistory();
    // }
  },

  components: {
    gameCard: GameCard,
    signIn: SignIn
  },

  methods: {
    reset() {
      (this.selectedGame = null),
        (this.playerOneRFID = null),
        (this.playerTwoRFID = null),
        (this.playerOne = null),
        (this.playerTwo = null),
        (this.needsRegister = false),
        (this.registeringNam = null);
    },

    open(link) {
      require("electron").shell.openExternal(link);
    },

    checkLoadingStates() {
      return Object.keys(this.loadingStates).some(key => {
        return this.loadingStates[key];
      });
    },

    startGame(gameType) {
      this.selectedGame = gameType;
      console.log(gameType);

      SerialPort.list((err, ports) => {
        if (err) {
          console.log(err);
        }
        const scannerPort = ports.find((port) => {
          return port.serialNumber === 'A906XU0J';
        });

        var port = new SerialPort(scannerPort.comName);
        const parser = port.pipe(new Readline({
          delimiter: '\r\n' 
        }));

        port.on('open', () => {
          parser.on('data', this.signIn); 
        });
      });
    },

    readId() {
      this.loadingStates.readingSignIn = true;
      ipcRenderer.send("read-sign-in");
    },

    //this endpoint needs to be updated
    signIn(auth) {
      const card = JSON.parse(auth);
      console.log(card);
      if (card.payload.cardCode) {
        const cardCode = card.payload.cardCode;

        http
          .get(`/v1/users/login/${cardCode}`)
          .then(response => {
            console.log(response);
            if (!this.playerOne) {
              this.playerOne = response.data;
            } else {
              this.playerTwo = response.data;
            }
            this.loadingStates.readingSignIn = false;
          })
          .catch(err => {
            console.log(err);
            this.needsRegister = cardCode;
            this.loadingStates.readingSignIn = false;
          });
      }
    },

    register() {
      this.loadingStates.registeringUser = true;
      http
        .post("/v1/users", {
          name: this.registeringName,
          rfid: this.needsRegister
        })
        .then(response => {
          console.log(response);
          console.log("registered");
          this.needsRegister = null;
          this.registeringName = null;
          if (!this.playerOne) {
            this.playerOne = response.data;
          } else {
            this.playerTwo = response.data;
          }
          this.loadingStates.registeringUser = false;
        })
        .catch(err => {
          console.log(err);
          this.loadingStates.registeringUser = false;
        });
    },

    // fetchUserHistory() {
    //   http.get(`/v1/users/${this.playerOne.id}/elo-history?gameType=${this.selectedGame}`)
    //   .then(response => {
    //     console.log(response)
    //   })
    //   .catch(err => {
    //     console.log(err)
    //   })
    // },

    startMatch() {
      this.loadingStates.creatingMatch = true;
      http
        .post(`/v1/matches`, {
          playerOneId: this.playerOne.id,
          playerTwoId: this.playerTwo.id,
          gameType: this.selectedGame
        })
        .then(response => {
          console.log(response);
          this.inProgressMatch = response.data;
          this.loadingStates.creatingMatch = false;
        })
        .catch(err => {
          console.log(err);
          this.loadingStates.creatingMatch = false;
        });
    },

    completeMatch() {
      this.loadingStates.completingMatch = true
      http.put(`/v1/matches/${this.inProgressMatch.id}/score`, {
        playerOneScore: this.scores.playerOne,
        playerTwoScore: this.scores.playerTwo
      })
      .then(response => {
        let winnerId = this.scores.playerOne > this.scores.playerTwo ? this.playerOne.id : this.playerTwo.id
        http.put(`/v1/matches/${this.inProgressMatch.id}/winner/${winnerId}`)
        .then(response => {
          this.matchSummary = response.data
          this.loadingStates.completingMatch = false
        })
        .catch(err => {
          console.log(err)
          this.loadingStates.completingMatch = false
        })
      })
      .catch(err => {
        console.log(err)
        this.loadingStates.completingMatch = false
      })
    }
  },

  mounted() {
    //catch the sign in event and set the player ids to the response
    ipcRenderer.on("sign-in-read", (event, arg) => {
      console.log("hit renderer");
      if (this.playerOneRFID) {
        console.log("setting two");
        this.playerTwoRFID = arg + 2; //the concat is for testing in dev
        this.signIn(arg + 2);
      } else {
        console.log("setting one");
        this.playerOneRFID = arg + 1;
        this.signIn(arg + 1);
      }
      console.log(arg);
    });
  }
};
</script>