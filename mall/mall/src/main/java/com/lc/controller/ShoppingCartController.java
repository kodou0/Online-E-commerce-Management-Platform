package com.lc.controller;

import com.lc.common.result.Result;
import com.lc.mapper.ShoppingCartMapper;
import com.lc.model.entity.ShoppingCart;
import com.lc.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @return 响应
     */
    @PostMapping("/add")
    public Result addShoppingCart(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request) {
        shoppingCart.setUserId(Integer.valueOf(request.getHeader("userId")));
        int cnt = shoppingCartMapper.insert(shoppingCart);
        if(cnt == 0){
            return Result.error("购物车添加失败");
        }
        return Result.ok(shoppingCart.getId());
    }

    /**
     * 删除购物项
     * @param id 购物车商品id
     * @return 响应
     */
    @DeleteMapping("/delete")
    public Result deleteShoppingCart(@RequestParam("id") int id) {
        int cnt = shoppingCartMapper.deleteById(id);
        if (cnt == 0) {
            return Result.error("购物车项删除失败");
        }
        return Result.ok();
    }

    /**
     * 清空购物车
     * @return 响应
     */
    @DeleteMapping("/clear")
    public Result clearShoppingCart(HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Result result;
        try {
            shoppingCartService.clearShoppingCart(Integer.valueOf(userId));
            result = Result.ok();
        } catch (Exception e) {
            result = Result.error(e.getMessage());
        }
        return result;
    }


    @PostMapping("/update")
    private Result updateShoppingCart(@RequestParam("orderId") int orderId, @RequestParam("amount")int amount) {
        int cnt = shoppingCartService.updateShoppingCart(orderId, amount);
        if (cnt == 0) {
            return Result.error("购物车商品更新失败");
        }
        return Result.ok();
    }

    /**
     * 获取所有购物项数据
     * @return 响应
     */
    @GetMapping("/getAll")
    public Result getShoppingCartPage(HttpServletRequest request){
        String userId = request.getHeader("userId");
        return Result.ok(shoppingCartService.getAllShoppingCarts(Integer.valueOf(userId)));
    }
}
