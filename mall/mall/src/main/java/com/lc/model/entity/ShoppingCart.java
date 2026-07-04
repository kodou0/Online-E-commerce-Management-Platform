package com.lc.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`shopping_cart`")
public class ShoppingCart {

    @TableId
    private int id;

    @TableField("user_id")
    private int userId;

    @TableField("goods_id")
    private int goodsId;

    @TableField("amount")
    private int amount;

    @TableField("img_url")
    private String imgUrl;

    @TableField("goods_name")
    private String goodsName;

    @TableField("price")
    private double price;
}
