package com.lc.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("resource")
public class Resource {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createTime;

    //资源名称
    private String name;

    //资源权限标识符
    private String permission;

    private String description;

    //资源分类ID(用户管理1 权限模块2 商品模块3 订单模块4  数据统计5 其他模块6)',
    private Integer categoryId;
}
