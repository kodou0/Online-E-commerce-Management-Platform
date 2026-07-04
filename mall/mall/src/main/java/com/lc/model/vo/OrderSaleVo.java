package com.lc.model.vo;


import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString

public class OrderSaleVo
{
    private int id;
    private int GoodsId;
    private double sum;
    private Date CreatedTime;
    private int amount;
}

