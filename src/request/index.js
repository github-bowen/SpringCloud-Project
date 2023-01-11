import axios from 'axios'
//import {message} from 'ant-design-vue'

// 创建一个axios实例
const axiosService = axios.create({
    //网关对应地址
    baseURL: 'http://localhost:8888/',
    timeout: 1000,
})

// request拦截器
axiosService.interceptors.request.use(
    request => {
        const token = localStorage.getItem('token');//store.getters['user/getUser'];
        if (!token) {
            request.headers.Authorization = null
        } else {
            request.headers.Authorization = token
        }
        return request
    },
    error => {
        return Promise.reject(error)
    }
)

// respone拦截器
axiosService.interceptors.response.use(
    response => {
        return response
    },
    error => {
        // 判断error的status代码，并将对应的信息告知用户
        console.log(error);
        //message.error('error');
    }
)

export default axiosService
