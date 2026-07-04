package com.lc.controller;

import com.lc.common.result.Result;
import com.lc.mapper.OrderMapper;
import com.lc.model.entity.Order;
import com.lc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;


    @GetMapping("/getAllOrders")
    public Result getAllOrders(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return Result.ok(orderService.getAllOrders(pageNum, pageSize));
    }


    @DeleteMapping("/deleteOrder")
    public Result deleteOrder(@RequestParam("id") int id) {
        Result result;
        try {
            orderService.deleteOrder(id);
            result = Result.ok();
        } catch (Exception e) {
            result = Result.error("订单删除失败");
        }
        return result;
    }

    @PostMapping("/addOrder")
    public Result addOrder(@RequestParam("userId") int userId, @RequestParam("addressId") int addressId, @RequestParam("sum") double sum,
                               @RequestParam("goodsIds") int[] goodsIds, @RequestParam("amounts")int[] amounts){
        Result result;
        try {
            orderService.addOrder(userId, addressId,sum,goodsIds,amounts);
            result = Result.ok();
        } catch (Exception e) {
            result = Result.error("订单信息插入失败");
        }
        return result;
    }

    @PostMapping("/updateOrder")
    @PreAuthorize("hasAuthority('/order/updateOrder')")
    public Result updateOrder(@RequestBody Order order) {
        if (orderMapper.updateById(order) == 1)
            return Result.ok();
        return Result.error("订单更新失败");
    }

    @GetMapping("/searchOrderById")
    public Result searchOrder(@RequestParam("id") int id) {
        Order order = orderMapper.selectById(id);
        if(order == null){
            return Result.error("订单不存在");
        }
        return Result.ok(order);
    }

    @GetMapping("/getOrderInfo")
    public Result getOrderInfo(@RequestParam("orderId") int orderId){
        return Result.ok(orderService.getOrderInfoByOrderId(orderId));
    }
}
