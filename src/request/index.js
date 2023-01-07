import axios from 'axios'
import store from '@/store'

// 创建一个axios实例
const axiosService = axios.create({
    //网关对应地址
    baseURL: 'http://192.168.2.163:10000/',
  timeout: 1000
})

// request拦截器
axiosService.interceptors.request.use(
  request => {
    const userInfo = store.getters['user/getUser'];
    if (!userInfo) {
      request.headers.Authorization = null
    } else {
      request.headers.Authorization = userInfo.token
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
    console.log(error)
  }
)

export default axiosService
