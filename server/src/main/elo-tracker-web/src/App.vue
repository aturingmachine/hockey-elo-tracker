<template>
  <v-app>

    <v-navigation-drawer
      permanent
      app
      clipped
    >
    <v-list>
      <v-list-tile
        v-for="link in links"
        :key="link.title"
        :href="link.href"
      >
        <v-list-tile-action>
          <v-icon>{{ link.icon }}</v-icon>
        </v-list-tile-action>

        <v-list-tile-content>
          <v-list-tile-title>{{ link.title }}</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
    </v-navigation-drawer>

    <v-toolbar app>
      <v-toolbar-title class="headline text-uppercase">
        <span>ELO</span>
        <span class="font-weight-light">TRACKER</span>
      </v-toolbar-title>
    </v-toolbar>

    <v-content>
    </v-content>
  </v-app>
</template>

<script>


export default {
  name: 'elo-tracker',
  components: {
    //
  },
  data () {
    return {
      links: [
        {
          title: 'Users',
          icon: 'account_circle',
          href: '/users'
        },
        {
          title: 'Match History',
          icon: 'assignment',
          href: '/matches'
        },
        {
          title: 'Elo Chart',
          icon: 'timeline',
          href: '/elo'
        }
      ]
    }
  }
}
</script>
