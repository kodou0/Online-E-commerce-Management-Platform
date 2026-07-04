package com.lc.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageParam implements Serializable {

    private static final Integer PAGE_Num = 1;
    private static final Integer PAGE_SIZE = 10;

    private Integer pageNum = PAGE_Num;
    private Integer pageSize = PAGE_SIZE;
}
