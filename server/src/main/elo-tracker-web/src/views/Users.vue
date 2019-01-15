<template>
  <v-card class="ma-3">
    <v-card-title> Users </v-card-title>

    <v-data-table
      :headers="tableHeaders"
      :items="users"
      class="elevation-1"
      v-if="users.length > 0"
    >
    <template slot="items" slot-scope="props">
      <td>{{ props.item.name }}</td>
      <td class="text-xs-right">{{ props.item.elo }}</td>
      <td class="text-xs-right">{{ props.item.wins }}</td>
      <td class="text-xs-right">{{ props.item.losses }}</td>
    </template>
  </v-data-table>

  </v-card>
</template>

<script>
import { http } from '../config/http.js'

export default {
  data () {
    return {
      tableHeaders: [
        { text: 'Name', align: 'left', sortable: true, value: 'name' },
        { text: 'Elo', align: 'right', sortable: true, value: 'elo' },
        { text: 'Wins', align: 'right', sortable: true, value: 'wins' },
        { text: 'Losses', align: 'right', sortable: true, value: 'lossses' }
      ],
      users: []
    }
  },

  methods: {
    getUsers() {
      http.get('v1/users')
      .then(res => {
        this.users = res.data
      })
      .catch(err => {
        console.log(err)
      })
    }
  },

  mounted() {
    this.getUsers()
  }
}
</script>

<style>

</style>
