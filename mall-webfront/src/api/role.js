import requst from './request'

export const getRole = (params) => {
  return requst({
    url: '/role/getPage',
    params
  })
}

export const addRole = (data) => {
  return requst({
    url: '/role/insert',
    method: 'post',
    data
  })
}

export const editRole = (data) => {
  return requst({
    url: '/role/update',
    method: 'POST',
    data
  })
}

export const statusUpdate = (params) => {
    return requst({
      url: '/role/statusUpdate',
      method: 'POST',
      params
    })
}

export const deleteRole = (params) => {
  return requst({
    url: '/role/delete',
    method: 'GET',
    params
  })
}

export const allocateResource = (data) => {
  return requst({
    url: '/role/allocate_resource?roleId=' + data.roleId + '&resourceIds=' + data.resourceIds,
    method: 'POST',
    data
  })
}

export const allocateMenu = (data) => {
  return requst({
    url: '/role/allocate_menu?roleId=' + data.roleId + '&menuIds=' + data.menuIds,
    method: 'POST',
    data
  })
}
