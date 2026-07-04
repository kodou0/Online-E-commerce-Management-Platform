import requst from './request'

export const getUser = (params) => {
  return requst({
    url: '/userInfo/getPage',
    params
  })
}

export const addUser = (data) => {
  return requst({
    url: '/userInfo/create',
    method: 'post',
    data
  })
}

export const editUser = (data) => {
  return requst({
    url: '/userInfo/update',
    method: 'POST',
    data
  })
}

export const deleteUser = (params) => {
  return requst({
    url: 'userInfo/delete',
    method: 'GET',
    params
  })
}

export const getUserRole = (params) => {
  return requst({
    url: 'userInfo/getRole',
    method: 'GET',
    params
  })
}

export const allocate = (params) => {
  return requst({
    url: 'role/allocate',
    method: 'POST',
    params
  })
}

export const getSimpleList = (params) => {
  return requst({
    url: 'role/getSimpleList',
    method: 'GET',
    params
  })
}
