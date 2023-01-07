import {createRouter, createWebHashHistory} from 'vue-router'


const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../views/LoginView.vue')
    },
    {
        path: '/main',
        name: 'main',
        component: () => import('../views/MainView.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('../views/RegisterView.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.path === '/main') {
        let user = localStorage.getItem('user')
        if (!user) {
            router.push('/')
        }
    }
    next()
})
// router.afterEach((to, from) => {
//
// })
export default router
