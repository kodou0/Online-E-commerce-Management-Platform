import requst from './request'
// import Qs from 'qs'
// 获取全部数据
export const getAllOrders = (params) => {
    return requst({
      url: 'order/getAllOrders',
      method: 'GET',
      headers: {
        'content-type': 'x-www-form-urlencoded'
    },
      params
    })
  }
  // 搜索
  export const searchOrder = (params) => {
    return requst({
      url: 'order/searchOrderById',
      method: 'GET',
      headers: {
        'content-type': 'x-www-form-urlencoded'
    },
    params
    })
  }
  // 更新
  export const updateOrder = (data) => {
    return requst({
      url: 'order/updateOrder',
      method: 'post',
      data
    })
  }
  // 删除
  export const deleteOrder = (params) => {
    return requst({
      url: 'order/deleteOrder',
      method: 'delete',
      params
    })
  }
  // 新增
  export const addOrder = (data) => {
    return requst({
      url: 'order/addOrder?userId=' + data.userId + '&addressId=' + data.addressId + '&sum=' + data.sum + '&goodsIds=' + data.goodsIds + '&amounts=' + data.amounts,
      method: 'post',
      headers: {
        'content-Type': 'x-www-form-urlencoded;charset=UTF-8'
        },
      data
    })
  }
  // 获取订单详情
  export const getOrderInfo = (orderId) => {
    return requst({
      url: 'order/getOrderInfo?orderId=' + orderId,
      method: 'get',
      headers: {
        'content-Type': 'x-www-form-urlencoded;charset=UTF-8'
        }
    })
  }
