package com.lc;

import com.lc.mapper.ShoppingCartMapper;
import com.lc.model.entity.ShoppingCart;
import com.lc.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ShoppingCartTest {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Test
    public void testAddShoppingCart(){
//        int userId = 3;
//        int goodsId = 10;
//        int amount = 40;
//        try {
//            shoppingCartMapper.in(userId, goodsId, amount);
//            System.out.println("添加购物车成功");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

    @Test
    public void testDeleteShoppingCart(){
        int id = 5;
        int cnt = shoppingCartMapper.deleteById(id);
        if (cnt == 0) {
            System.out.println("购物车项不存在");
        }else{
            System.out.println("购物车项删除成功");
        }
    }

    @Test
    public void testClearShoppingCart(){
        try {
            int userId = 3;
            shoppingCartService.clearShoppingCart(userId);
            System.out.println("清空购物车成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testUpdateShoppingCart(){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(8);
        shoppingCart.setUserId(2);
        shoppingCart.setGoodsId(30);
        shoppingCart.setAmount(20);
        int cnt = shoppingCartMapper.updateById(shoppingCart);
        if (cnt == 0) {
            System.out.println("购物车商品更新失败");
        }else{
            System.out.println("购物车商品更新成功");
        }
    }

    @Test
    public void testGetAllShoppingCarts(){
        int userId = 12;
        List<ShoppingCart> lists = shoppingCartService.getAllShoppingCarts(userId);
        lists.forEach(System.out::println);
    }
}
