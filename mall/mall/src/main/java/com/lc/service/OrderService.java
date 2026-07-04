package com.lc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.model.entity.Order;
import com.lc.model.entity.OrderInfo;

import java.util.List;


public interface OrderService extends IService<Order> {


    Page<Order> getAllOrders(Integer pageNum, Integer pageSize);

    void deleteOrder(int id) throws Exception;

    void addOrder(int userId, int addressId, double sum, int[] goodsIds, int[] amounts) throws Exception;

    List<OrderInfo> getOrderInfoByOrderId(int orderId);
}
