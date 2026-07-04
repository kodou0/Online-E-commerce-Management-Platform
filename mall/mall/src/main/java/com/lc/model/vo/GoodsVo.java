package com.lc.model.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoodsVo {
    private int id;
    private String goodsTypeId;
    private String typeName;
    private String goodsName;
    private double price;
    private String brand;
    private int shelves;
    private String img;
}
