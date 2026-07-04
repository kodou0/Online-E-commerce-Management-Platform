package com.lc.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.mapper.OrderInfoMapper;
import com.lc.mapper.OrderMapper;
import com.lc.model.entity.Order;
import com.lc.model.entity.OrderInfo;
import com.lc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;


    @Override
    public Page<Order> getAllOrders(Integer pageNum, Integer pageSize) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        orderMapper.selectPage(page, null);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOrder(int id) throws Exception{
        int cnt = orderMapper.deleteById(id);
        if(cnt == 0){
            throw new RuntimeException("订单不存在");
        }
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getOrderId,id);
        int res = orderInfoMapper.delete(wrapper);
        if (res == 0){
            throw new RuntimeException("订单信息不存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrder(int userId, int addressId, double sum, int[] goodsIds, int[] amounts) throws Exception {
        Order order = new Order();
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setSum(sum);
        int cnt = orderMapper.insert(order);
        if(cnt == 0)
            throw new RuntimeException("订单信息插入失败");
        for(int i = 0; i < goodsIds.length; i++){
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(order.getId());
            orderInfo.setAmount(amounts[i]);
            orderInfo.setGoodsId(goodsIds[i]);
            orderInfoMapper.insert(orderInfo);
        }
    }

    @Override
    public List<OrderInfo> getOrderInfoByOrderId(int orderId) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getOrderId,orderId);
        return orderInfoMapper.selectList(wrapper);
    }
}
