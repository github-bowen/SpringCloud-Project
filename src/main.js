import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueAxios from 'vue-axios'
import axios from "axios";
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'


const app = createApp(App)
app.use(store).use(router)
app.use(VueAxios, axios)
app.use(Antd)
app.mount('#app')
