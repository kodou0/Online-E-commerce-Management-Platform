import request from './request'

// 今日订单总数
export const todayAmount = () => {
  return request({
    url: 'OrderInfo/TodayAmount',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 昨日订单总数
export const yesAmount = () => {
  return request({
    url: 'OrderInfo/YesAmount',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 今日销售总额
export const todaySum = () => {
  return request({
    url: 'OrderInfo/TodaySum',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 昨日销售总额
export const yesSum = () => {
  return request({
    url: 'OrderInfo/YesSum',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本月订单数
export const monAmount = () => {
  return request({
    url: 'OrderInfo/MonAmount',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本月订单同比
export const monthOnMonthAmount = () => {
  return request({
    url: 'OrderInfo/MonthOnMonthAmount',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本周订单数
export const weekAmount = () => {
  return request({
    url: 'OrderInfo/WeekAmount',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本周订单同比
export const weekOnWeekAmount = () => {
  return request({
    url: 'OrderInfo/WeekOnWeekAmount',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本月销售额
export const monSum = () => {
  return request({
    url: 'OrderInfo/MonSum',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本月销售额同比
export const monthOnMonthSum = () => {
  return request({
    url: 'OrderInfo/MonthOnMonthSum',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本周销售额
export const weekSum = () => {
  return request({
    url: 'OrderInfo/WeekSum',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 本周销售额同比
export const weekOnWeekSum = () => {
  return request({
    url: 'OrderInfo/WeekOnWeekSum',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    }
  })
}
// 时间范围内订单数
export const periodOfTimeAmount = (params) => {
  return request({
    url: 'OrderInfo/PeriodOfTimeAmount',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    },
    params
  })
}
// 时间范围内销售额
export const periodOfTimeSum = (params) => {
  return request({
    url: 'OrderInfo/PeriodOfTimeSum',
    method: 'GET',
    headers: {
      'content-type': 'x-www-form-urlencoded'
    },
    params
  })
}
