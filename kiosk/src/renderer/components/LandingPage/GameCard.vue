<template>
  <v-flex xs5>
    <v-card @click="startGame()">
      <v-card-title class="text-xs-center title">
        {{ game.split(' ').map(i => i.charAt(0).toUpperCase() + i.slice(1)).join(' ') }}
      </v-card-title>
    </v-card>
  </v-flex>
</template>

<script>
export default {
  data: () => {
    return {
      gameTypes: {
        bubble_hockey: "BUBBLE_HOCKEY",
        ping_pong: "PING_PONG"
      }
    };
  },
  props: {
    game: {
      type: String
    }
  },
  methods: {
    startGame() {
      console.log(this.game.replace(' ', '_').toUpperCase())
      this.$emit("startGame", this.gameTypes[this.game.replace(' ', '_')]);
    }
  }
};
</script>
