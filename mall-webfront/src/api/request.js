import axios from 'axios'
import { ElMessage } from 'element-plus'
import { diffTokenTime } from '@/utils/auth'
import store from '@/store'
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000
})

service.interceptors.request.use(
  (config) => {
    if (localStorage.getItem('token')) {
      if (diffTokenTime()) {
        store.dispatch('app/logout')
        return Promise.reject(new Error('token 失效了'))
      }
      config.headers.token = localStorage.getItem('token')
    }
    // if (window.location.hash.indexOf('#/login') !== 0) {
    //   config.headers.token = localStorage.getItem('token')
    // } else {
    //   config.headers.Authorization = localStorage.getItem('token')
    // }

    return config
  },
  (error) => {
    return Promise.reject(new Error(error))
  }
)

service.interceptors.response.use(
  (response) => {
    // const { data, code, message } = response.data
    const res = response.data
    if (res.code === 20000 || res.code === 20100) {
      return res
    } else if (res.code === 40100) {
      store.dispatch('app/logout')
     return Promise.reject(new Error(res.message))
    } else {
      ElMessage.error(res.message)
      return Promise.reject(new Error(res.message))
    }
  },
  (error) => {
    console.log(error.response)
    error.response && ElMessage.error(error.response.data)
    return Promise.reject(new Error(error.response.data))
  }
)
export default service
