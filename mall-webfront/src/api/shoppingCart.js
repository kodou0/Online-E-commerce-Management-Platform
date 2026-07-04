import request from './request'
export const getGoodsList = () => {
    return request({
        url: '/cart/getAll',
        method: 'GET',
        Timeout: 50000
    })
}
export const addShoppingCart = (data) => {
    return request({
        url: 'cart/add',
        method: 'Post',
        data
    })
}
export const delShoppingCart = (id) => {
    return request({
        url: '/cart/delete?id=' + id,
        method: 'DELETE'
    })
}
export const clearShoppingCart = () => {
    return request({
        url: '/cart/clear',
        method: 'DELETE'
    })
}
export const updateNum = (data) => {
    return request({
        url: '/cart/update',
        method: 'POST',
        data
    })
}
export const getGoods = (params) => {
    return request({
        url: 'goods/getAllGoods',
        method: 'GET',
        headers: {
            'content-type': 'x-www-form-urlencoded'
        },
        params
    })
}
