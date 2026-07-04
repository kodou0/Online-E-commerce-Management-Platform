package com.lc.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.mapper.ShoppingCartMapper;
import com.lc.model.entity.ShoppingCart;
import com.lc.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Override
    public void clearShoppingCart(int userId) throws Exception{
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq( ShoppingCart::getUserId, userId);
        int cnt = shoppingCartMapper.delete(wrapper);
        if(cnt == 0){
            throw new RuntimeException("清空购物车失败");
        }
    }

    @Override
    public List<ShoppingCart> getAllShoppingCarts(int userId) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq( ShoppingCart::getUserId, userId);
        List<ShoppingCart> lists = shoppingCartMapper.selectList(wrapper);
        return lists;
    }

    @Override
    public int updateShoppingCart(int orderId, int amount) {
        LambdaUpdateWrapper<ShoppingCart> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ShoppingCart::getId,orderId);
        wrapper.set(ShoppingCart::getAmount,amount);
        return shoppingCartMapper.update(null,wrapper);
    }


}
