package com.lc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.mapper.OrderInfoMapper;
import com.lc.mapper.OrderMapper;
import com.lc.model.entity.Order;
import com.lc.model.entity.OrderInfo;
import com.lc.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;


    @Test
    public void testGetOrderPage() {
        Page<Order> page = orderService.getAllOrders(1, 10);
        List<Order> lists = page.getRecords();
        lists.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());
    }

    @Test
    public void testAddOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAmount(4);//amount
        orderInfo.setGoodsId(4);//good_id
        orderInfo.setOrderId(8);//order_id
        int cnt = orderInfoMapper.insert(orderInfo);
        System.out.println("cnt = " + cnt);
    }


    @Test
    public void testDeleteOrder() {
        int id = 5;//order_info.order_id  order.id
        try {
            orderService.deleteOrder(id);
            System.out.println("订单删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("订单删除失败");
        }
    }

    @Test
    public void testUpdateOrder() {
        Order order = new Order();
        order.setId(8);
        order.setUserId(5);
        order.setAddressId(5);
        order.setSum(10.0);
        order.setDeliver(1);
        int cnt = orderMapper.updateById(order);
        if (cnt == 1) {
            System.out.println("订单更新成功");
        } else {
            System.out.println("订单更新失败");
        }
    }

    @Test
    public void testSearchOrderById() {
        int id = 8;
        Order order = orderMapper.selectById(id);
        if (order == null) {
            System.out.println("订单不存在");
        } else {
            System.out.println(order);
        }
    }

    @Test
    public void testAddOrder() {
        int userId = 2;
        int addressId = 3;
        double sum = 40;
        int[] goodsIds = {1, 2, 3};
        int[] amounts = {1, 2, 3};
        try {
            orderService.addOrder(userId, addressId, sum, goodsIds, amounts);
            System.out.println("插入订单成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
