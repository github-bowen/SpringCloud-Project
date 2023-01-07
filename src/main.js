import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueAxios from 'vue-axios'
import axios from "axios";
const app = createApp(App)
app.use(store).use(router).mount('#app')
app.use(VueAxios, axios)
