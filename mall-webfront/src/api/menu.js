import request from './request'

export const menuList = () => {
  return request({
    url: '/menu/treeList',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
  }
  })
}

export const menuListByParentIdRole = () => {
  return request({
    url: '/menu/getRoleMenu ',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
  }
  })
}

export const menuListByParentId = (params) => {
  return request({
    url: '/menu/list',
    method: 'GET',
    params
  })
}

export const menuUpdateHidden = (params) => {
  return request({
    url: '/menu/updateHidden',
    method: 'POST',
    params
  })
}

export const menuAdd = (data) => {
  return request({
    url: '/menu/create',
    method: 'POST',
    headers: {
      'content-type': 'application/json'
    },
    data
  })
}

export const menuUpdate = (id, data) => {
  return request({
    url: '/menu/update',
    method: 'POST',
    params: {
      id: id
    },
    data,
    headers: {
      'content-type': 'application/json'
    }
  })
}

export const menuDelete = (params) => {
  return request({
    url: 'menu/delete',
    method: 'DELETE',
    params
  })
}
