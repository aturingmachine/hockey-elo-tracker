import Vue from 'vue'
import Router from 'vue-router'
import Users from './views/Users.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/users',
      name: 'users',
      component: Users
    }
  ]
})
