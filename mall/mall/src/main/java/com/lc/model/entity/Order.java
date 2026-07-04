package com.lc.model.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`order`")
public class Order {
    @TableId
    private int id;
    @TableField("user_id")
    private int userId;
    @TableField("address_id")
    private int addressId;
    @TableField("sum")
    private double sum;
    @TableField("deliver")
    private int deliver;
    @TableField("refund")
    private int refund;
    @TableField("created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
}
