package com.lc.model.vo;

import com.lc.model.entity.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class ResourcePageReqVO extends PageParam {

    private String name;

    //资源路径/权限标识符
    private String path;

    //资源分类ID(用户管理1 权限模块2 商品模块3 订单模块4  数据统计5 其他模块6)
    private Integer categoryId;
}
