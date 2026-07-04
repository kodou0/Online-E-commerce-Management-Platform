package com.lc.model.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class OrderVo
{
    private int id;
    private double sum;
    private Date CreatedTime;
}
