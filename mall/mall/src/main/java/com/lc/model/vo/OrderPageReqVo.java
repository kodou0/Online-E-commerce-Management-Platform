package com.lc.model.vo;

import com.lc.model.entity.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderPageReqVo extends PageParam {
    private int id;

    private int userId;

    private int addressId;

    private double sum;

    private int deliver;

    private int refund;

    private Date createdTime;
}
