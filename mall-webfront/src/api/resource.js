import request from './request'

export const getResource = (data) => {
  return request({
    url: '/resource/getPage',
    method: 'POST',
    data
  })
}

export const addResource = (data) => {
    return request({
        url: '/resource/create',
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        data
    })
}

export const updateResource = (data) => {
    return request({
        url: '/resource/update',
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        data
    })
}

export const delResource = (id) => {
    return request({
        url: '/resource/delete?id=' + id,
        method: 'GET'
    })
}

export const getResourceById = (roleId) => {
    return request({
        url: 'resource/getList?roleId=' + roleId,
        method: 'GET'
    })
}
