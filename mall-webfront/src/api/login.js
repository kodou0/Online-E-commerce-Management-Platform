import request from './request'

export const login = (params) => {
  return request({
    url: '/user/login',
    method: 'POST',
    params
  })
}

export const register = (params) => {
  return request({
    url: '/user/register',
    method: 'POST',
    headers: {
      'content-type': 'application/json'
  },
    params
  })
}

export const logout = () => {
  return request({
    url: '/user/logout',
    method: 'GET'
  })
}
