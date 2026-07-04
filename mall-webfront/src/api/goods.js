import request from './request'

export const getGoods = (params) => {
  return request({
    url: 'goods/getAllGoods',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    },
    params,
    timeout: 50000
  })
}

export const addGoods = (data) => {
  return request({
    url: 'goods/addGoods',
    method: 'post',
    data
  })
}

export const updateGoods = (data) => {
  return request({
    url: 'goods/updateGoods',
    method: 'post',
    headers: {
      'content-type': 'application/json'
    },
    data,
    timeout: 50000
  })
}

export const delGoods = (params) => {
  return request({
    url: 'goods/deleteGoods',
    method: 'DELETE',
    params
  })
}

export const queryGoods = (data) => {
  return request({
    url: 'goods/queryGoods',
    method: 'post',
    headers: {
      'content-type': 'application/json'
    },
    data
  })
}

export const getAllGoodsType = (params) => {
  return request({
    url: 'goods/getAllGoodsType',
    method: 'get',
    params
  })
}

export const addGoodsType = (data) => {
  return request({
    url: '/goods/addGoodsType',
    method: 'post',
    headers: {
      'content-type': 'application/json'
    },
    data
  })
}

export const updateGoodsType = (data) => {
  return request({
    url: '/goods/updateGoodsType',
    method: 'post',
    headers: {
      'content-type': 'application/json'
    },
    data
  })
}

export const getBase64 = (file) => {
  return new Promise(function (resolve, reject) {
    const reader = new FileReader()
    let imgResult = ''
    reader.readAsDataURL(file)
    reader.onload = function () {
      imgResult = reader.result
    }
    reader.onerror = function (error) {
      reject(error)
    }
    reader.onloadend = function () {
      resolve(imgResult)
    }
  })
}
