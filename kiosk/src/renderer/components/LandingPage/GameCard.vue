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
    return {};
  },
  props: {
    game: {
      type: String
    }
  },
  methods: {
    startGame() {
      this.$emit('startGame', this.game)
    }
  }
};
</script>
