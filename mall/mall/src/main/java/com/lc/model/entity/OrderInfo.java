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
@TableName("order_info")
public class OrderInfo {

    @TableId
    private int id;

    @TableField("goods_id")
    private int goodsId;

    @TableField("amount")
    private int amount;

    @TableField("order_id")
    private int orderId;
}
