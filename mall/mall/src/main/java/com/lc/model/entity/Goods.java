package com.lc.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("goods")
public class Goods {
    @TableId
    private int id;
    @TableField("goods_type_id")
    private int goodsTypeId;
    @TableField("goods_name")
    private String goodsName;
    @TableField("price")
    private double price;
    @TableField("brand")
    private String brand;
    @TableField("shelves")
    private int shelves;
    @TableField("img")
    private String img;
}
