<template>
  <div class>
    <v-content v-if="!hasLoggedIn">
      <v-container fluid>
        <v-layout row wrap align-content-center>
          <v-flex xs4></v-flex>
          <v-flex xs4>
            Please Sign In:
            <v-text-field v-model="credentials.username" label="Username"></v-text-field>
            <v-text-field v-model="credentials.password" label="Password" type="password"></v-text-field>
            <v-btn @click="authLogin()">Login</v-btn>
          </v-flex>
          <v-flex xs4></v-flex>
        </v-layout>
      </v-container>
    </v-content>

    <div v-if="hasLoggedIn">
      <v-content v-if="!selectedGame && !checkLoadingStates()">
        <v-container fluid>
          <v-layout row wrap align-content-center>
            <v-flex xs12 class="display-2 pb-5 text-xs-center">What Are We Playing Today?</v-flex>

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
        <register-user @register="register"></register-user>
      </v-content>

      <v-content v-if="playerOne && playerTwo && !inProgressMatch && !checkLoadingStates()">
        <h1>{{ playerOne.name }} VS {{ playerTwo.name }}</h1>
        <v-btn @click="startMatch()">Start Match</v-btn>
      </v-content>

      <v-content v-if="inProgressMatch && !matchSummary && !checkLoadingStates()">
        <match-in-progress
          :playerOne="playerOne"
          :playerTwo="playerTwo"
          @completeMatch="completeMatch"
        ></match-in-progress>
      </v-content>

      <v-content v-if="matchSummary && !checkLoadingStates()">
        <match-summary
          :matchSummary="matchSummary"
          :playerOne="playerOne"
          :playerTwo="playerTwo"
          @newGame="newGame"
        ></match-summary>
      </v-content>

      <v-content v-if="checkLoadingStates()">
        <h2 v-if="loadingStates.creatingMatch">Creating Match...</h2>
        <h2 v-if="loadingStates.readingSignIn">Signing In...</h2>
        <h2 v-if="loadingStates.registeringUser">Registering...</h2>
        <v-progress-linear :indeterminate="true"></v-progress-linear>
      </v-content>
    </div>
  </div>
</template>

<script>
import { ipcRenderer } from "electron";
import { http } from "../config/http.js";
import GameCard from "./LandingPage/GameCard.vue";
import SignIn from "./LandingPage/SignIn.vue";
import MatchSummary from "./LandingPage/MatchSummary.vue";
import MatchInProgress from "./LandingPage/MatchInProgress.vue";
import RegisterUser from "./LandingPage/RegisterUser.vue";

const SerialPort = require("serialport");
const Readline = require("@serialport/parser-readline");

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
      inProgressMatch: null,
      matchSummary: null,
      loadingStates: {
        creatingMatch: false,
        readingSignIn: false,
        registeringUser: false,
        completingMatch: false
      },
      hasLoggedIn: false,
      credentials: {
        username: "",
        password: ""
      }
    };
  },

  watch: {},

  components: {
    gameCard: GameCard,
    signIn: SignIn,
    matchSummary: MatchSummary,
    matchInProgress: MatchInProgress,
    registerUser: RegisterUser
  },

  methods: {
    open(link) {
      require("electron").shell.openExternal(link);
    },

    authLogin() {
      const asdf = Object.keys(this.credentials)
        .map(
          key =>
            encodeURIComponent(key) +
            "=" +
            encodeURIComponent(this.credentials[key])
        )
        .join("&");

      http
        .post("login", asdf, { headers: { Accept: "application/json" } })
        .then(response => {
          console.log(response);
          this.hasLoggedIn = true;
        })
        .catch(err => {
          console.log(err);
        });
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
        const scannerPort = ports.find(port => {
          return port.serialNumber === "A906XU0J";
        });

        if (!!scannerPort) {
          var port = new SerialPort(scannerPort.comName);
          const parser = port.pipe(
            new Readline({
              delimiter: "\r\n"
            })
          );

          port.on("open", () => {
            parser.on("data", this.signIn);
          });
        }
      });
    },

    readId() {
      this.loadingStates.readingSignIn = true;
      ipcRenderer.send("read-sign-in");
    },

    //I am not sure if the below comment is valid still
    //this endpoint needs to be updated
    signIn(auth) {
      console.log(auth);
      const card = JSON.parse(auth);
      console.log(card);
      if (card.payload.cardCode) {
        const cardCode = card.payload.cardCode;

        http
          .get(`/api/v1/users/login/${cardCode}`)
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

    register(registeringName) {
      this.loadingStates.registeringUser = true;
      http
        .post("/api/v1/users", {
          name: registeringName,
          rfid: this.needsRegister
        })
        .then(response => {
          console.log(response);
          console.log("registered");
          this.needsRegister = null;
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
    //   http.get(`/api/v1/users/${this.playerOne.id}/elo-history?gameType=${this.selectedGame}`)
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
        .post(`/api/v1/matches`, {
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

    completeMatch(scores) {
      this.loadingStates.completingMatch = true;
      http
        .put(`/api/v1/matches/${this.inProgressMatch.id}/score`, {
          playerOneScore: scores.playerOne,
          playerTwoScore: scores.playerTwo
        })
        .then(response => {
          let winnerId =
            scores.playerOne > scores.playerTwo
              ? this.playerOne.id
              : this.playerTwo.id;
          http
            .put(
              `/api/v1/matches/${this.inProgressMatch.id}/winner/${winnerId}`
            )
            .then(response => {
              this.matchSummary = response.data;
              this.loadingStates.completingMatch = false;
            })
            .catch(err => {
              console.log(err);
              this.loadingStates.completingMatch = false;
            });
        })
        .catch(err => {
          console.log(err);
          this.loadingStates.completingMatch = false;
        });
    },

    newGame() {
      (this.selectedGame = null),
        (this.playerOneRFID = null),
        (this.playerTwoRFID = null),
        (this.playerOne = null),
        (this.playerTwo = null),
        (this.playerOneElo = null),
        (this.playerTwoElo = null),
        (this.needsRegister = false),
        (this.registeringName = null),
        (this.inProgressMatch = null),
        (this.matchSummary = null),
        (this.loadingStates = {
          creatingMatch: false,
          readingSignIn: false,
          registeringUser: false,
          completingMatch: false
        });
    }
  },

  mounted() {
    const sessionId = document.cookie.match(
      "(^|[^;]+)\\s*JSESSIONID\\s*=\\s*([^;]+)"
    );
    if (sessionId) {
      this.hasLoggedIn = true;
    }
    //catch the sign in event and set the player ids to the response
    ipcRenderer.on("sign-in-read", (event, arg) => {
      console.log("hit renderer");
      if (this.playerOneRFID) {
        console.log("setting two");
        this.playerTwoRFID = arg;
        this.signIn(arg);
      } else {
        console.log("setting one");
        this.playerOneRFID = arg;
        this.signIn(arg);
      }
      console.log(arg);
    });
  }
};
</script>