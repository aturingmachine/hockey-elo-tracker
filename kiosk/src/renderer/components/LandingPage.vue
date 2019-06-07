<template>
  <div class>
    <v-content v-if="!hasLoggedIn && !checkLoadingStates()">
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
      <v-content v-if="!selectedGame && !checkLoadingStates()" class="fade-in">
        <v-container fluid>
          <v-layout row wrap align-content-center>
            <v-flex xs12 class="display-2 pb-5 text-xs-center">What Are We Playing Today?</v-flex>

            <game-card :game="'ping pong'" @startGame="startGame"></game-card>

            <v-flex xs2></v-flex>

            <game-card :game="'bubble hockey'" @startGame="startGame"></game-card>
          </v-layout>
        </v-container>
      </v-content>

      <v-content v-if="selectedGame && !checkLoadingStates()" class="fade-in">
        <sign-in
          v-if="(!playerOneRFID || !playerTwoRFID) && !needsRegister"
          :player="playerOneRFID ? 'two' : 'one'"
          @sign-in-started="readId"
        ></sign-in>
      </v-content>

      <v-content v-if="needsRegister && !checkLoadingStates()" class="fade-in">
        <register-user @register="register"></register-user>
      </v-content>

      <v-content v-if="this.loadingStates.welcomeMessage" class="fade-in-out">
        <v-layout row wrap align-content-center>
          <v-flex xs12 class="display-3 text-xs-center pt-5">Welcome {{ welcomeName }}</v-flex>
        </v-layout>
      </v-content>

      <v-content
        v-if="playerOne && playerTwo && !inProgressMatch && !checkLoadingStates()"
        class="fade-in"
      >
        <v-layout row wrap align-content-center>
          <v-flex xs12 class="display-3 text-xs-center">{{ playerOne.name }} VS {{ playerTwo.name }}</v-flex>
          <v-flex xs12 class="pt-5">
            <v-btn
              @click="startMatch()"
              block
              large
              class="pb-5 pt-3 display-1 font-weight-black"
              color="orange darken-4"
            >Start Match</v-btn>
          </v-flex>
        </v-layout>
      </v-content>

      <v-content v-if="inProgressMatch && !matchSummary && !checkLoadingStates()" class="fade-in">
        <match-in-progress
          :playerOne="playerOne"
          :playerTwo="playerTwo"
          @completeMatch="completeMatch"
        ></match-in-progress>
      </v-content>

      <v-content v-if="matchSummary && !checkLoadingStates()" class="fade-in">
        <match-summary
          :matchSummary="matchSummary"
          :playerOne="playerOne"
          :playerTwo="playerTwo"
          :gameType="selectedGame"
          @newGame="newGame"
        ></match-summary>
      </v-content>

      <v-content
        v-if="checkLoadingStates() && !loadingStates.welcomeMessage && !showError"
        class="display-2"
      >
        <v-layout row wrap align-content-center>
          <v-flex xs12 class="text-xs-center">
            <span v-if="loadingStates.creatingMatch">Creating Match...</span>
            <span v-if="loadingStates.readingSignIn">Signing In...</span>
            <span v-if="loadingStates.registeringUser">Registering...</span>
          </v-flex>
          <v-flex xs1 class="pt-5"></v-flex>
        </v-layout>
        <v-progress-linear :indeterminate="true" color="orange darken-4"></v-progress-linear>
      </v-content>

      <v-dialog v-model="showError">
        <error-modal
          v-if="showError"
          :errorMessage="errorMessage"
          @closeErrorModal="closeErrorModal"
        ></error-modal>
      </v-dialog>
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
import ErrorModal from "./LandingPage/ErrorModal.vue";

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
      welcomeName: null,
      playerOneElo: null,
      playerTwoElo: null,
      needsRegister: false,
      inProgressMatch: null,
      matchSummary: null,
      loadingStates: {
        creatingMatch: false,
        readingSignIn: false,
        registeringUser: false,
        completingMatch: false,
        welcomeMessage: false
      },
      hasLoggedIn: false,
      credentials: {
        username: "",
        password: ""
      },
      showError: false,
      errorMessage: ""
    };
  },

  watch: {},

  components: {
    gameCard: GameCard,
    signIn: SignIn,
    matchSummary: MatchSummary,
    matchInProgress: MatchInProgress,
    registerUser: RegisterUser,
    errorModal: ErrorModal
  },

  methods: {
    open(link) {
      require("electron").shell.openExternal(link);
    },

    authLogin() {
      const encodedCredentials = Object.keys(this.credentials)
        .map(
          key =>
            encodeURIComponent(key) +
            "=" +
            encodeURIComponent(this.credentials[key])
        )
        .join("&");

      http
        .post("login", encodedCredentials, {
          headers: { Accept: "application/json" }
        })
        .then(response => {
          console.log(response);
          this.hasLoggedIn = true;
        })
        .catch(err => {
          console.log("Auth Error", err);
          this.errorMessage =
            "Auth Login Failed. Check your credentials and try again or contact the Dev Team.";
          this.showError = true;
        });
    },

    checkLoadingStates() {
      return (
        Object.keys(this.loadingStates).some(key => {
          return this.loadingStates[key];
        }) || this.showError
      );
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
            this.welcomeName = `Back ${response.data.name}`;
            this.loadingStates.welcomeMessage = true;
            setTimeout(() => {
              this.loadingStates.welcomeMessage = false;
              this.welcomeName = null;
            }, 4000);
          })
          .catch(err => {
            console.log(err);
            this.needsRegister = cardCode;
            this.loadingStates.readingSignIn = false;
            // We cannot intelligently handle errors here since the login endpoint 500's on a missing id, and server errors
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
          this.welcomeName = ` To RĀNCŌR ${response.data.name}`;
          this.loadingStates.welcomeMessage = true;
          setTimeout(() => {
            this.loadingStates.welcomeMessage = false;
            this.welcomeName = null;
          }, 4000);
        })
        .catch(err => {
          console.log(err);
          this.loadingStates.registeringUser = false;
          this.handleError(
            "Unable to Register User. Please Try Again or Contact the Dev Team."
          );
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
          this.handleError(
            "Unable to Start Game. Please Try Again or Contact the Dev Team"
          );
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
              this.handleError(
                "Unable to Complete Match. Please Try Again or Contact the Dev Team."
              );
            });
        })
        .catch(err => {
          console.log(err);
          this.loadingStates.completingMatch = false;
          this.handleError(
            "Unable to Update Match Score. Please Try Again or Contact the Dev Team."
          );
        });
    },

    handleError(errorMessage) {
      this.errorMessage = errorMessage;
      this.showError = true;
    },

    closeErrorModal() {
      (this.errorMessage = ""), (this.showError = false);
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
<style>
.fade-in {
  animation-duration: 1s;
  animation-fill-mode: both;
  animation-name: fadeIn;
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.fade-in-out {
  animation-duration: 4s;
  animation-fill-mode: both;
  animation-name: fadeInOut;
}

@keyframes fadeInOut {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
</style>
