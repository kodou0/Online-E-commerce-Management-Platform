package com.lc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.model.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {

    void clearShoppingCart(int userId) throws Exception;

    List<ShoppingCart> getAllShoppingCarts(int userId);

    int updateShoppingCart(int orderId, int amount);
}
