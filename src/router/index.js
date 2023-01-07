import { createRouter, createWebHashHistory } from 'vue-router'


const routes = [
  {
    path:'/',
    name:'login',
    component:()=>import('../views/LoginView.vue')
  },
  {
    path: '/main',
    name:'main',
    component:()=>import('../views/MainView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
